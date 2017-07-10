package com.pcs.tracker.service;

import java.util.List;

import com.pcs.tracker.persistence.model.UserProfile;

public interface UserProfileService {

	/**
	 * @param id
	 * @return
	 */
	UserProfile getUserProfile(long id);

	/**
	 * @param userName
	 * @return
	 */
	UserProfile findByUserName(String userName);

	/**
	 * @return
	 */
	List<UserProfile> findAllUsers(UserProfile user);
	
	/**
	 * @return
	 */
	List<UserProfile> findAllEnabledUsers(UserProfile user);

	/**
	 * @param userDto
	 */
	//UserProfile addUser(final UserDto userDto);
	
	/**
	 * @param userDto
	 * @param id
	 */
	//UserProfile updateUser(final UserDto userDto);
	
	/**
	 * @param userProfileId
	 * @param userName
	 * @return
	 */
	boolean isUniqueUserName(long userProfileId, String userName);	
	
    /**
     * @param userProfile
     * @param token
     */
    void createVerificationTokenForUser(UserProfile userProfile, String token);

    /**
     * @param id
     * @param token
     * @return
     */
    String validateVerificationToken(long id, String token);
    
    /**
     * @param id
     * @param token
     * @return
     */
    String validateAndUpdateVerificationToken(long id, String token);

    /**
     * @param userProfile
     * @param token
     */
    void createPasswordTokenForUser(UserProfile userProfile, String token);
    
    /**
     * @param userProfile
     * @param token
     * @param loggedInUserId
     */
    void createPasswordTokenForUser(UserProfile userProfile, String token, long loggedInUserId);

    /**
     * @param id
     * @param token
     * @return
     */
    String validatePasswordToken(long id, String token);
    
    /**
     * @param id
     * @param token
     * @return
     */
    String validateAndUpdatePasswordToken(long id, String token);
 
    /**
     * @param userProfile
     * @param password
     */
    void changeUserPassword(final UserProfile userProfile, final String password); 
    
    /**
     * @param id
     * @return
     */
    boolean enabledAdminUser(long id);
}
