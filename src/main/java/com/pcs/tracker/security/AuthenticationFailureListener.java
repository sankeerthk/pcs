package com.pcs.tracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    @Autowired
    @Lazy
    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(final AbstractAuthenticationFailureEvent e) {
        final WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
        if (auth != null) {
        	loginAttemptService.logAttempt(false,e.getAuthentication().getName());
        }
    }

}