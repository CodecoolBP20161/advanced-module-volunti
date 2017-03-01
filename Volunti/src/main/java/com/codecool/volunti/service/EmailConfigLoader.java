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

    // TODO: http url override to the final url
    private static final String HEADERIMAGE = "src=http://localhost:8080/assets/images/volunti_logo.png";
    private static final String URL = "http://localhost:8080";

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
        String bodyStart = "<body><table style='font-family: arial;'><tr><td align='center'><img " + HEADERIMAGE + "></td></tr>";
        String body = "<tr><td align='center' >";
        String url = "<p>"+ URL +"/registration/organisation/step3/" + user.getActivationID() + "</p>";
        String bodyEnd = "</td></tr><tr><td align='center'></td></tr></table></body>";
        String pStart = "<p>";
        String pEnd = "</p>";

        String text1 = emailProperties.get("text1").toString();
        String text2 = emailProperties.get("text2").toString();
        String text3 = emailProperties.get("text3").toString();
        String welcome1 = emailProperties.get("welcome1").toString();
        String welcome2 = emailProperties.get("welcome2").toString();
        String msg =htmlStart +
                bodyStart + body + pStart + text1 + pEnd +
                br + pStart + text2 + pEnd +
                br + pStart + text3 + pEnd +
                br + url + pStart + welcome1 + pEnd +
                br + pStart + welcome2 + pEnd + bodyEnd + htmlEnd;
        helper.setText(msg, true);

        return helper;
    }
}
