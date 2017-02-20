package com.codecool.volunti.service;


import com.codecool.volunti.model.User;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@Data
public class EmailConfigLoader {

    private Logger LOGGER = LoggerFactory.getLogger(EmailConfigLoader.class);

    private static final String HEADERIMAGE = "src=http://www.twitrcovers.com/wp-content/uploads/2014/11/Snail-l.jpg";

    EmailProperties emailPropertiesReader = new EmailProperties();
    HashMap emailProperties;

    public MimeMessageHelper setEmailProperties(User user, EmailType emailType, MimeMessage mimeMessage) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(user.getEmail());

        emailProperties = emailPropertiesReader.getPropValues(emailType);
        helper.setSubject(emailProperties.get("subject").toString());

        String br = "<br/>";
        String htmlStart = "<html>";
        String htmlEnd = "</html>";
        String bodyStart = "<body style='text-align: justify; color: red'>";
        String bodyEnd = "</body>";
        String img = "<img "  + HEADERIMAGE +  " style='width: 65%'>";
        String h1Start = "<h1>";
        String h1End = "</h1>";
        String url = "<a>localhost:8080/registration/organisation/step3/" + user.getActivationID() + "</a>";

        String text1 = emailProperties.get("text1").toString();
        String text2 = emailProperties.get("text2").toString();
        String welcome1 = emailProperties.get("welcome1").toString();
        String welcome2 = emailProperties.get("welcome2").toString();
        String msg =htmlStart + bodyStart + img + h1Start + text1 + br + text2  + br + url + br + welcome1 + br + welcome2 + h1End + bodyEnd + htmlEnd;
        helper.setText(msg, true);

        return helper;
    }
}
