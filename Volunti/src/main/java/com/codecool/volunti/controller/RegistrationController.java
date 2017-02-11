package com.codecool.volunti.controller;


import com.codecool.volunti.model.User;
import com.codecool.volunti.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/signup-success")
    public String signupSuccess(){

        // create user
        User user = new User();
        user.setFirstName("Moni");
        user.setLastName("Lombos");
        user.setEmail("lombos.monika@gmail.com");

        // send a notification
        try {
            notificationService.sendNotification(user);
        }catch( Exception e ){
            // catch error
            LOGGER.warn("Error Sending Email: " + e.getMessage());
        }

        return "Thank you for registering with us.";
    }

}
