package com.pcs.tracker.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pcs.tracker.persistence.dao.UserPrincipalDAO;
import com.pcs.tracker.persistence.model.UserPrincipal;
import com.pcs.tracker.persistence.model.UserProfile;

@Service("userPrincipalService")
public class UserPrincipalServiceImpl implements UserPrincipalService {
	
	@Autowired
	UserPrincipalDAO userPrincipalDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final byte REQUIRED_NUM_OF_CHARACTERISTIC_FOR_PASS = 3;
	private static final byte COUNT_OF_LAST_PASSES_FOR_CHECK = 23;

	private static final List<Pattern> passRequiredPatterns;

	static {
		passRequiredPatterns = new ArrayList<Pattern>();
		passRequiredPatterns.add(Pattern.compile("[a-z]"));
		passRequiredPatterns.add(Pattern.compile("[A-Z]"));
		passRequiredPatterns.add(Pattern.compile("[0-9]"));
		passRequiredPatterns.add(Pattern.compile("[\\W]"));
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(UserPrincipalService.class);

	@Override
	public void createPrincipal(UserProfile userProfile) {

		LOGGER.debug("Creating new principal");

		createPrincipal(userProfile, userProfile.getPassword());

		LOGGER.debug("A new principal was created and the old one was deleted.");
	}

	@Override
	public void createPrincipal(UserProfile userProfile, String password) {

		LOGGER.debug("Creating new principal with pass");

		UserPrincipal newUserPrincipal = new UserPrincipal();
		fillPrincipalBody(userProfile, password, newUserPrincipal);
		UserPrincipal currentUserPrincipal = userPrincipalDAO.getActivePrincipalForUser(userProfile.getUserProfileId());
		userPrincipalDAO.save(newUserPrincipal);
		if (currentUserPrincipal != null) {
			LOGGER.debug("Current principal was found");
			currentUserPrincipal.setCurrent(false);
			currentUserPrincipal.setLastUpdatedBy(userProfile.getUserProfileId());
			currentUserPrincipal.setLastUpdatedDate(new Date());
			userPrincipalDAO.save(currentUserPrincipal);
			userPrincipalDAO.deleteOldPrincipals(userProfile.getUserProfileId());
		}

		LOGGER.debug("A new principal with pass was created and the old one was deleted.");
	}

	private void fillPrincipalBody(UserProfile userProfile, String password, UserPrincipal newUserPrincipal) {
		LOGGER.debug("Filling the  principal");
		newUserPrincipal.setUserProfileId(userProfile.getUserProfileId());
		newUserPrincipal.setPassword(password);
		newUserPrincipal.setCreatedBy(userProfile.getLastUpdatedBy());
		newUserPrincipal.setLastUpdatedBy(userProfile.getLastUpdatedBy());
	}

	@Override
	public boolean isValidPassword(String password) {

		if (password == null || password.length() < 8) {
			return false;
		}

		byte countOfcharacteristics = 0;
		Matcher passMatcher = null;

		for (Pattern passPattern : passRequiredPatterns) {
			passMatcher = passPattern.matcher(password);
			if (passMatcher.find()) {
				countOfcharacteristics++;
			}
		}
		return countOfcharacteristics >= REQUIRED_NUM_OF_CHARACTERISTIC_FOR_PASS;
	}

	@Override
	public boolean isPasswordUsedLastNtimes(long id, String password) {
		LOGGER.debug("Checking if pass was used last " + COUNT_OF_LAST_PASSES_FOR_CHECK + "times");
		List<UserPrincipal> userPrincipals = userPrincipalDAO.getAllPrincipalsForUser(id);
		Iterator<UserPrincipal> it = userPrincipals.iterator();
		while (it.hasNext()) {
			if (passwordEncoder.matches(password, it.next().getPassword())) {
				return true;
			}
		}
		return false;
	}
}
