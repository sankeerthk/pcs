package com.pcs.tracker.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pcs.tracker.persistence.model.UserProfile;
import com.pcs.tracker.service.UserProfileService;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserProfileService userProfileService;

	@Override
	public UserDetails loadUserByUsername(String userName) {
		UserProfile userProfile = userProfileService.findByUserName(userName);
		if(userProfile == null){
			System.out.println("user not found");
			throw new UsernameNotFoundException("User not found");
		}else if(!userProfile.isEnabled()){
			throw new DisabledException("User is Disabled");
		}else if(userProfile.isAccountLocked()){
			throw new LockedException("user Account is locked");
		}
		return new org.springframework.security.core.userdetails.User(userProfile.getUserName(), userProfile.getPassword(), userProfile.isEnabled(), true, true, !userProfile.isAccountLocked(), getGrantedAuthorities(userProfile));
	}

	private List<GrantedAuthority> getGrantedAuthorities(UserProfile userProfile) {
		List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(userProfile.getRole().getRoleName()));
		return authorities;
	}
}
