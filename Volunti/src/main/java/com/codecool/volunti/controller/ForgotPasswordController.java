package com.codecool.volunti.controller;


import com.codecool.volunti.model.User;
import com.codecool.volunti.service.EmailService;
import com.codecool.volunti.service.EmailType;
import com.codecool.volunti.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Controller
public class ForgotPasswordController {

    private Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private UserService userService;
    private EmailService emailService;

    //render forgot password
    @RequestMapping( value = "/forgotPassword/step1", method = RequestMethod.GET )
    public String forgotPassword( Model model) {
        LOGGER.info("forgotPassword() method called ...");
        User user = new User();
        model.addAttribute("user", user);
        return "forgotPassword";
    }


    @RequestMapping( value = "/forgotPassword/step1", method = RequestMethod.POST )
    public String checkNewPasswordEmail(User user) {
        user = userService.getByEmail(user.getEmail());
        LOGGER.info("checkNewPasswordEmail() method called ...");
        if(user != null){
            user.setActivationID(UUID.randomUUID());
            userService.saveUser(user);
            user.signupSuccess(emailService, EmailType.FORGOT_PASSWORD);
        }
        return "registration/step4";
    }

    @RequestMapping( value = "/forgotPassword/step2/{activation_id}", method = RequestMethod.GET )
    public String renderforgotPassword(@PathVariable String activation_id, Model model) {
        LOGGER.info("renderforgotPassword() method called ...");
        User newUser = userService.confirmRegistration(activation_id);

        if (newUser == null){
            LOGGER.warn("Activation failed.");
            return "registration/invalidActivationLink";
        } else {
            LOGGER.info("User profile has been activated.");
        }
        model.addAttribute("user", newUser);
        return "update password form";  //???
    }

    @RequestMapping( value = "/forgotPassword/step2/", method = RequestMethod.POST )
    public String saveNewPassword(User user) {
        LOGGER.info("saveNewPasswor() method called ...");
        user.hashPassword(user.getPassword());
        userService.saveUser(user);
        return "index";
    }




}
