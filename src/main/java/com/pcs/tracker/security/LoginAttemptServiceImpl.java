package com.pcs.tracker.security;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.pcs.tracker.persistence.dao.LoginAttemptDAO;
import com.pcs.tracker.persistence.dao.UserProfileDAO;
import com.pcs.tracker.persistence.model.LoginAttempt;
import com.pcs.tracker.persistence.model.UserProfile;


@Service("loginAttemptService")
public class LoginAttemptServiceImpl implements LoginAttemptService {

	@Autowired
	LoginAttemptDAO loginAttemptDAO;

	@Autowired
	UserProfileDAO userProfileDAO;
	
	@Autowired
	private Environment env;

	private LoadingCache<String, Integer> attemptsCache;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginAttemptService.class);
	
	private static int maxAttempts;
	
	@Autowired(required = true)
	private void init() {		
		maxAttempts = Integer.parseInt(env.getProperty("user.login.maxAttempt"));
	}


	public LoginAttemptServiceImpl() {
		super();
		attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS)
				.build(new CacheLoader<String, Integer>() {
					@Override
					public Integer load(final String key) {
						return 0;
					}
				});
	}

	@Override
	public void logAttempt(boolean status, String userName) {
		LoginAttempt loginAttempt = new LoginAttempt();
		if (status) {
			loginAttempt.setStatus("Successful authentication");
			clearCache(userName);
		} else {
			loginAttempt.setStatus("Failed authentication");
			int attempts = 0;
			try {
				attempts = attemptsCache.get(userName);
			} catch (final ExecutionException e) {
				LOGGER.error("Exception while getting value from cache ", e);
				attempts = 0;
			}
			attempts++;
			attemptsCache.put(userName, attempts);
			if (attempts >= maxAttempts) {
				UserProfile userProfile = userProfileDAO.findByUserName(userName);
				if (null != userProfile) {
					userProfile.setAccountLocked(true);
					userProfileDAO.save(userProfile);
				}
				loginAttempt.setStatus("Failed authentication - User account is locked");
			}
		}
		loginAttempt.setUserName(userName);
		loginAttemptDAO.save(loginAttempt);
	}

	@Override
	public void clearCache(String userName) {
		if (null!= userName && userName.isEmpty()) {
			attemptsCache.invalidateAll();
		} else {
			attemptsCache.invalidate(userName);
		}

	}
	
	@Override
	public int remainingAttempts(String userName) {
		if (null != userName && userName.isEmpty()) {
			return maxAttempts;
		} else {
			try {
				return maxAttempts - attemptsCache.get(userName);
			} catch (ExecutionException e) {
				LOGGER.error("Exception while calculating attempts ", e);
				return maxAttempts;
			}
		}
	}
}
