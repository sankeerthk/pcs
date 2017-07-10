package com.pcs.tracker.security;

public interface LoginAttemptService {
	
	void logAttempt(boolean success, String username);
	
	void clearCache(String username);
	
	int remainingAttempts(String username);
}
