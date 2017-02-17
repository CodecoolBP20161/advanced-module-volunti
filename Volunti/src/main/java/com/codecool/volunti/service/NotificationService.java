package com.codecool.volunti.service;

import com.codecool.volunti.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class NotificationService {

	private Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);
	private JavaMailSender javaMailSender;
	
	@Autowired
	public NotificationService(JavaMailSender javaMailSender){
		this.javaMailSender = javaMailSender;
	}
	
	@Async
	public void sendNotification(User user) throws MessagingException {

        LOGGER.debug("Sending email...");

        String msg = "<html><body style='text-align: justify; color: red' ><img src=http://www.twitrcovers.com/wp-content/uploads/2014/11/Snail-l.jpg style='width: 100%'><h1>Hello from volunti, please put the activation link</h1><img src= http://www.twitrcovers.com/wp-content/uploads/2014/11/Snail-l.jpg></body></html>";
		MimeMessage message = javaMailSender.createMimeMessage();

		message.setSubject("hello from volunti");
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message, true);
		helper.setFrom("edwinemma92@gmail.com");
		helper.setTo("lombos.monika@gmail.com");
		helper.setText(msg, true);
		javaMailSender.send(message);
		LOGGER.debug("Email sent...");
	}
	
}