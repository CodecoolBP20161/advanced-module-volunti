package com.codecool.volunti.service;

import com.codecool.volunti.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

	private Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	private JavaMailSender javaMailSender;
	private EmailConfigLoader emailConfigLoader = new EmailConfigLoader();
	
	@Autowired
	public EmailService(JavaMailSender javaMailSender){
		this.javaMailSender = javaMailSender;
	}

	@Async
	public void sendEmail(User user, EmailType emailType) throws MessagingException {
        LOGGER.debug("Sending email...");
        MimeMessage message = javaMailSender.createMimeMessage();
        // EmailProperties
        emailConfigLoader.setEmailProperties(user, emailType, message);
        javaMailSender.send(message);
        LOGGER.debug("Email sent...");
	}
	
}