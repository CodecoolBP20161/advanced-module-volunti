package com.codecool.volunti.service;

import com.codecool.volunti.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	private Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);
	private JavaMailSender javaMailSender;
	
	@Autowired
	public NotificationService(JavaMailSender javaMailSender){
		this.javaMailSender = javaMailSender;
	}
	
	@Async
	public void sendNotification(User user) throws MailException, InterruptedException {

        LOGGER.debug("Sending email...");
        SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom("edwinemma92@gmail.com");
		mail.setSubject("Spring Boot is awesome!");
		mail.setText("Why aren't you using Spring Boot?");
		javaMailSender.send(mail);

		LOGGER.debug("Email sent...");
	}
	
}