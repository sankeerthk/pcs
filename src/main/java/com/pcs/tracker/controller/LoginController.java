package com.pcs.tracker.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("loggedInUser")
public class LoginController {

//	@Autowired
//	AuthenticationTrustResolver authenticationTrustResolver;

//	@Autowired
//	private MessageSource messages;
	
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;


	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	/**
	 * This method handles login GET requests. If users is already logged-in and
	 * tries to goto login page again, will be redirected to authorization code
	 * page.
	 * 
	 * @param model
	 *            the model attribute for the login page
	 * 
	 * @return String the view name where user will be redirected
	 */
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(ModelMap model) {
		System.out.println("Password:--" +passwordEncoder.encode("Password@4411"));
		if (isCurrentAuthenticationAnonymous()) {
			LOGGER.debug("User is not logged-in");
			return "login";
		} else {
			LOGGER.debug("User is already logged-in");
			return "redirect:/user/home";
		}
	}

	/**
	 * This method handles login redirect after logout requests.
	 * 
	 * @param redirectAttributes
	 *            the redirectAttributes attribute for the login page
	 * 
	 * @return String the view name where user will be redirected
	 */
	@RequestMapping(value = { "/login" }, params = { "logout=true" }, method = RequestMethod.GET)
	public String logoutRedirect(RedirectAttributes redirectAttributes) {
		LOGGER.debug("User is logged-out");
		redirectAttributes.addFlashAttribute("logout", "true");
		return "redirect:/login";
	}

	/**
	 * This method handles login redirect after login error.
	 * 
	 * @param redirectAttributes
	 *            the redirectAttributes attribute for the login page
	 * 
	 * @return String the view name where user will be redirected
	 */
	@RequestMapping(value = { "/login" }, params = { "error=true" }, method = RequestMethod.GET)
	public String loginError(RedirectAttributes redirectAttributes) {
		LOGGER.debug("User authentication error");
		redirectAttributes.addFlashAttribute("error", "true");
		return "redirect:/login";
	}

	/**
	 * This method handles login redirect after expired session.
	 * 
	 * @param redirectAttributes
	 *            the redirectAttributes attribute for the login page
	 * 
	 * @return String the view name where user will be redirected
	 */
	@RequestMapping(value = { "/login" }, params = { "session=expired" }, method = RequestMethod.GET)
	public String sessionExpired(RedirectAttributes redirectAttributes) {
		LOGGER.debug("User session has expired.");
		redirectAttributes.addFlashAttribute("session", "true");
		return "redirect:/login";
	}

	/**
	 * This method will handle the logout requests from logged-in user.
	 * 
	 * @param request
	 *            the http request attribute
	 * @param response
	 *            the http response attribute
	 * 
	 * @return String the view name where user will be redirected
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
			model.remove("loggedInUser");
			request.getSession().removeAttribute("loggedInUser");
			SecurityContextHolder.getContext().setAuthentication(null);
			SecurityContextHolder.clearContext();
		}
		return "redirect:/login?logout=true";
	}

	/**
	 * This method will redirect the user to login page if user does not have
	 * permission to access requested page.
	 * 
	 * @param model
	 *            the model attribute for the request
	 * @param principal
	 *            the user principal object
	 * 
	 * @return String the redirect link for login page
	 */
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDenied(final RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
//		redirectAttributes.addFlashAttribute("accessDenied",
//				messages.getMessage("error.user.accessDenied", null, null));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
			model.remove("loggedInUser");
			request.getSession().removeAttribute("loggedInUser");
			SecurityContextHolder.getContext().setAuthentication(null);
			SecurityContextHolder.clearContext();
		}
		return "redirect:/login";
	}

	/**
	 * This method returns true if users is already authenticated [logged-in],
	 * else false.
	 * 
	 * @return boolean true if users is already authenticated
	 */
	private boolean isCurrentAuthenticationAnonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AuthenticationTrustResolver authenticationTrustResolver =new AuthenticationTrustResolverImpl();
		return authenticationTrustResolver.isAnonymous(authentication);
	}

}