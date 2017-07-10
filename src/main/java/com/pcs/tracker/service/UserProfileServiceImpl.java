package com.pcs.tracker.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pcs.tracker.persistence.dao.PasswordTokenDAO;
import com.pcs.tracker.persistence.dao.RoleDAO;
import com.pcs.tracker.persistence.dao.UserProfileDAO;
import com.pcs.tracker.persistence.dao.VerificationTokenDAO;
import com.pcs.tracker.persistence.model.PasswordToken;
import com.pcs.tracker.persistence.model.UserProfile;
import com.pcs.tracker.persistence.model.VerificationToken;
import com.pcs.tracker.security.UserPrincipalService;

@Service("userProfileService")
@Transactional(propagation =Propagation.REQUIRED)
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	UserProfileDAO userProfileDao;

	@Autowired
	RoleDAO roleDao;

	@Autowired
	private VerificationTokenDAO verificationTokenDao;

	@Autowired
	private PasswordTokenDAO passwordTokenDao;

	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserPrincipalService userPrincipalService;

	private static final String TOKEN_INVALID = "invalidToken";
	private static final String TOKEN_EXPIRED = "expiredToken";

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileService.class);

	@Override
	public UserProfile getUserProfile(long id) {
		return userProfileDao.findByUserProfileId(id);
	}

	@Override
	public UserProfile findByUserName(String userName) {
		return userProfileDao.findByUserName(userName);
	}

	@Override
	public List<UserProfile> findAllUsers(UserProfile user) {
		return userProfileDao.findAll();
	}

	@Override
	public List<UserProfile> findAllEnabledUsers(UserProfile user) {
		return userProfileDao.findAllEnabledUsers(user);
	}

	/*@Override
	public UserProfile addUser(UserDto userDto) {
		UserProfile userProfile = new UserProfile();
		userProfile.setUserName(userDto.getEmail());
		userProfile.setEnabled(false);
		userProfile.setAccountLocked(true);
		userProfile.setCreatedBy(userDto.getCreatedBy());
		userProfile.setLastUpdatedBy(userDto.getCreatedBy());
		userProfile.setRole(roleDao.getRole(userDto.getRoleId()));
		if (0 != userDto.getPharmacyId()) {
			userProfile.setPharmacy(pharmacyDao.getPharmacy(userDto.getPharmacyId()));
		}else{
			userProfile.setPharmacy(null);
		}
		userProfile.setCreatedDate(new Date());
		userProfile.setLastUpdatedDate(new Date());
		userProfiledao.save(userProfile);
		LOGGER.debug("User has been created : " + userProfile.getUserName());
		return userProfile;
	}
*/
	@Override
	public boolean isUniqueUserName(long userProfileId, String userName) {
		UserProfile userProfile = findByUserName(userName);
		return null == userProfile || userProfile.getUserProfileId() == userProfileId;
	}

	/*@Override
	public UserProfile updateUser(UserDto userDto) {
		UserProfile userProfile = getUserProfile(userDto.getId());
		userProfile.setUserName(userDto.getEmail());
		userProfile.setEnabled(userDto.isEnabled());
		userProfile.setRole(roleDao.getRole(userDto.getRoleId()));
		if (0 != userDto.getPharmacyId()) {
			userProfile.setPharmacy(pharmacyDao.getPharmacy(userDto.getPharmacyId()));
		}else{
			userProfile.setPharmacy(null);
		}
		userProfile.setLastUpdatedBy(userDto.getLastUpdatedBy());
		userProfile.setLastUpdatedDate(new Date());
		userProfiledao.save(userProfile);
		LOGGER.debug("User has been updated : " + userProfile.getUserName());
		return userProfile;
	}
*/
	@Override
	public void createVerificationTokenForUser(final UserProfile userProfile, final String token) {
		final VerificationToken myToken = new VerificationToken(token, userProfile);
		myToken.setCreatedBy(userProfile.getCreatedBy());
		myToken.setLastUpdatedBy(userProfile.getLastUpdatedBy());
		verificationTokenDao.save(myToken);
	}

	@Override
	public void createPasswordTokenForUser(final UserProfile userProfile, final String token) {
		final PasswordToken myToken = new PasswordToken(token, userProfile);
		myToken.setCreatedBy(userProfile.getUserProfileId());
		myToken.setLastUpdatedBy(userProfile.getUserProfileId());
		passwordTokenDao.save(myToken);
	}
	

	@Override
	public void createPasswordTokenForUser(UserProfile userProfile, String token, long loggedInUserId) {
		final PasswordToken myToken = new PasswordToken(token, userProfile);
		myToken.setCreatedBy(loggedInUserId);
		myToken.setLastUpdatedBy(loggedInUserId);
		passwordTokenDao.save(myToken);
		
	}

	@Override
	public String validateVerificationToken(long id, String token) {
		final VerificationToken verificationToken = verificationTokenDao.findByToken(token);
		if (null == verificationToken || (verificationToken.getUserProfile().getUserProfileId() != id)
				|| !verificationToken.isEnabled()) {
			return TOKEN_INVALID;
		}

		final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return TOKEN_EXPIRED;
		}
		return null;
	}
	
	@Override
	public String validateAndUpdateVerificationToken(long id, String token) {
		final VerificationToken verificationToken = verificationTokenDao.findByToken(token);
		if (null == verificationToken || (verificationToken.getUserProfile().getUserProfileId() != id)
				|| !verificationToken.isEnabled()) {
			return TOKEN_INVALID;
		}

		final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return TOKEN_EXPIRED;
		}

		verificationToken.setEnabled(false);
		verificationToken.setLastUpdatedBy(id);
		verificationToken.setLastUpdatedDate(new Date());
		verificationTokenDao.save(verificationToken);
		return null;
	}

	@Override
	public void changeUserPassword(final UserProfile userProfile, final String password) {
		userProfile.setPassword(passwordEncoder.encode(password));
		userProfile.setEnabled(true);
		userProfile.setAccountLocked(false);
		userProfile.setPasswordLastChanged(new Date());
		userProfile.setLastUpdatedBy(userProfile.getUserProfileId());
		userProfile.setLastUpdatedDate(new Date());
		userProfileDao.save(userProfile);
		userPrincipalService.createPrincipal(userProfile);
		LOGGER.debug("User password has been updated : " + userProfile.getUserName());
	}

	@Override
	public String validatePasswordToken(long id, String token) {
		final PasswordToken passwordToken = passwordTokenDao.findByToken(token);
		if ((null == passwordToken) || (passwordToken.getUserProfile().getUserProfileId() != id)
				|| !passwordToken.isEnabled()) {
			return TOKEN_INVALID;
		}

		final Calendar cal = Calendar.getInstance();
		if ((passwordToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return TOKEN_EXPIRED;
		}
		return null;
	}
	
	@Override
	public String validateAndUpdatePasswordToken(long id, String token) {
		final PasswordToken passwordToken = passwordTokenDao.findByToken(token);
		if ((null == passwordToken) || (passwordToken.getUserProfile().getUserProfileId() != id)
				|| !passwordToken.isEnabled()) {
			return TOKEN_INVALID;
		}

		final Calendar cal = Calendar.getInstance();
		if ((passwordToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return TOKEN_EXPIRED;
		}

		passwordToken.setEnabled(false);
		passwordToken.setLastUpdatedBy(id);
		passwordToken.setLastUpdatedDate(new Date());
		passwordTokenDao.save(passwordToken);
		passwordTokenDao.disableTokens(id);
		return null;
	}

	@Override
	public boolean enabledAdminUser(long id) {
		if(countOfEnabledAdminUser(id) > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public int countOfEnabledAdminUser(long id) {
		UserProfile user = getUserProfile(id);
		int count = 0;
		List<UserProfile> userList=userProfileDao.enabledAdminUser(user.getRole());
		if (userList != null && !userList.isEmpty()) {
			for(UserProfile userProfile : userList){
				if(id != userProfile.getUserProfileId()){
					count++;
				}
			}
		}
		return count;
	}
}
