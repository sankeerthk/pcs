package com.pcs.tracker.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.pcs.tracker.persistence.model.UserProfile;
import com.pcs.tracker.service.UserProfileService;

@Component("authenticationSuccessHandler")
@SessionAttributes("loggedInUser")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private UserProfileService userProfileService;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request,final HttpServletResponse response, final Authentication authentication)
			throws IOException {
		handle(request,response,authentication);
		final HttpSession session= request.getSession(false);
		if(session != null){
			UserProfile user = userProfileService.findByUserName(getPrincipal().getUsername());
			session.setAttribute("loggedInUser", user);
			session.setMaxInactiveInterval(1200);
		}
		clearAuthenticationAttributes(request);
	}

	protected void handle(final HttpServletRequest request,final HttpServletResponse response,final Authentication authentication) throws IOException {
		final String targetUrl =  determineTargetUrl(authentication);
		if(response.isCommitted()){
			System.out.println("response is commited: "+targetUrl);
			return;
		}
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(final Authentication authentication) {	
		return "user/home";
	}
	
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if(session == null){
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
	private UserDetails getPrincipal() {
		UserDetails userDetails=null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof UserProfile){
			userDetails = (UserDetails) principal;
		}
		return userDetails;
	}

	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

}
