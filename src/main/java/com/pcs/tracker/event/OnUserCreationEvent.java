package com.pcs.tracker.event;

import org.springframework.context.ApplicationEvent;

import com.pcs.tracker.persistence.model.UserProfile;


public class OnUserCreationEvent extends ApplicationEvent {

	private final String appUrl;
	private final UserProfile userProfile;
	
	private static final long serialVersionUID = 1L;

	/**
	 * The constructor for Application event.
	 * @param userProfile
	 * @param appUrl
	 */
	public OnUserCreationEvent(final UserProfile userProfile, final String appUrl) {
        super(userProfile);
        this.userProfile = userProfile;
        this.appUrl = appUrl;
    }
	
	public String getAppUrl() {
		return appUrl;
	}
	
	public UserProfile getUserProfile() {
		return userProfile;
	}

}
