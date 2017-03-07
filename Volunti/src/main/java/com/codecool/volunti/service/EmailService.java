package com.codecool.volunti.service;

import com.codecool.volunti.model.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@Transactional
public class EmailService {

	private JavaMailSender javaMailSender;
	private EmailConfigLoader emailConfigLoader = new EmailConfigLoader();
	
	@Autowired
	public EmailService(JavaMailSender javaMailSender){
		this.javaMailSender = javaMailSender;
	}

	@Async
	public void sendEmail(User user, EmailType emailType) throws MessagingException {
        log.debug("Sending email...");
        MimeMessage message = javaMailSender.createMimeMessage();
        // EmailProperties
        emailConfigLoader.setEmailProperties(user, emailType, message);
        javaMailSender.send(message);
        log.debug("Email sent...");
	}
	
}