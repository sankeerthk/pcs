package com.pcs.tracker.listener;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.pcs.tracker.event.OnUserCreationEvent;
import com.pcs.tracker.persistence.model.UserProfile;
import com.pcs.tracker.service.UserProfileService;


@Component
public class UserCreationListener implements ApplicationListener<com.pcs.tracker.event.OnUserCreationEvent> {

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private MessageSource messages;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserCreationListener.class);

	@Override
	public void onApplicationEvent(final OnUserCreationEvent event) {
		try {
			this.confirmRegistration(event);
		} catch (MailException | NoSuchMessageException | MessagingException e) {
			LOGGER.error("email could not be sent", e);
		}
	}

	/**
	 * This method will handle the user creation event.
	 * 
	 * @param event
	 * @throws MessagingException 
	 */
	private void confirmRegistration(final OnUserCreationEvent event) throws MessagingException {
		final UserProfile userProfile = event.getUserProfile();
		final String token = UUID.randomUUID().toString();
		userProfileService.createVerificationTokenForUser(userProfile, token);
		final MimeMessage email = constructEmailMessage(event, userProfile, token);
		mailSender.send(email);
	}

	/**
	 * This method will create the email for user verification.
	 * 
	 * @param event
	 * @param userProfile
	 * @param token
	 * 
	 * @return MimeMessage the email to be sent to user
	 * @throws MessagingException 
	 */
	private final MimeMessage constructEmailMessage(final OnUserCreationEvent event, final UserProfile userProfile,
			final String token) throws MessagingException {
		final String recipientAddress = userProfile.getUserName();
		final String subject = messages.getMessage("email.newUser.subject", null, null);
		final String confirmationUrl = event.getAppUrl() + "/userConfirmation?id=" + userProfile.getUserProfileId()
		+ "&token=" + token;
		final String message1 = messages.getMessage("email.newUser.successMsg1", null, null);
		final String message2 = messages.getMessage("email.newUser.successMsg2", null, null);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
		helper.setTo(recipientAddress);
		helper.setSubject(subject);
		mimeMessage.setContent(message1 + confirmationUrl + message2, "text/html");
		helper.setFrom(env.getProperty("support.email"));
		return mimeMessage;
	}
}
