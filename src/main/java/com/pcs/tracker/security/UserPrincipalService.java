package com.pcs.tracker.security;

import com.pcs.tracker.persistence.model.UserProfile;

public interface UserPrincipalService {
	void createPrincipal(UserProfile userProfile);

	void createPrincipal(UserProfile userProfile, String password);

	boolean isValidPassword(String password);

	boolean isPasswordUsedLastNtimes(long id, String password);
}