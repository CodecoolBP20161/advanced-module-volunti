package com.codecool.volunti.controller;


import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class ForgotPasswordController {

    private Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private OrganisationRepository organisationRepository;
    private OrganisationService organisationService;
    private UserService userService;
    private EmailService emailService;
    private ValidationService validationService = new ValidationService(organisationService, userService);


    @Autowired
    public ForgotPasswordController(OrganisationRepository organisationRepository,
                                  OrganisationService organisationService,
                                  UserService userService,
                                  EmailService emailService,
                                  ValidationService validationService) {
        this.organisationRepository = organisationRepository;
        this.organisationService = organisationService;
        this.userService = userService;
        this.emailService = emailService;
        this.validationService = validationService;
    }

    @RequestMapping( value = "/forgotPassword/step1", method = RequestMethod.GET )
    public String forgotPassword( Model model) {
        LOGGER.info("forgotPassword() method called ...");
        User user = new User();
        model.addAttribute("user", user);
        return "forgotPassword";
    }


    @RequestMapping( value = "/forgotPassword/step1", method = RequestMethod.POST )
    public String checkNewPasswordEmail(@RequestParam String emailAddress, Model model) {
        LOGGER.info("checkNewPasswordEmail() method called ...");

        User user = userService.getByEmail(emailAddress);
        if(user != null){
            user.setActivationID(UUID.randomUUID());
            userService.saveUser(user);
            user.signupSuccess(emailService, EmailType.FORGOT_PASSWORD);
        }
        model.addAttribute("theme", "Forgot Password");
        model.addAttribute("message", "We have sent an e-mail to your email address to the given e-mail account.");
        return "information";
    }

    @RequestMapping( value = "/forgotPassword/step2/{activation_id}", method = RequestMethod.GET )
    public String renderforgotPassword(@PathVariable String activation_id, Model model) {
        LOGGER.info("renderforgotPassword() method called ...");


        User newUser = userService.confirmRegistration(activation_id);
        LOGGER.info("ourUser " + newUser.toString());
        if (newUser == null){
            LOGGER.warn("Activation failed.");
            return "registration/invalidActivationLink";
        } else {
            LOGGER.info("User profile has been activated.");
        }
        model.addAttribute("user", newUser);
        return "newPasswordForm";
    }

    @RequestMapping( value = "/forgotPassword/step2/", method = RequestMethod.POST )
    public String saveNewPassword(User user) {
        LOGGER.info("saveNewPasswor() method called ...");
        user.hashPassword(user.getPassword());
        userService.saveUser(user);
        return "index";
    }




}
