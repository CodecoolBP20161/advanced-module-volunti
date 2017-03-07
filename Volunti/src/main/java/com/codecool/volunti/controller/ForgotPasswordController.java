package com.codecool.volunti.controller;


import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Controller
public class ForgotPasswordController {

    private static EmailType EMAILTYPE = EmailType.FORGOT_PASSWORD;

    private OrganisationRepository organisationRepository;
    private OrganisationService organisationService;
    private UserService userService;
    private EmailService emailService;
    private ValidationService validationService = new ValidationService(organisationService, userService);
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ForgotPasswordController(OrganisationRepository organisationRepository,
                                  OrganisationService organisationService,
                                  UserService userService,
                                  EmailService emailService,
                                  ValidationService validationService, BCryptPasswordEncoder passwordEncoder) {
        this.organisationRepository = organisationRepository;
        this.organisationService = organisationService;
        this.userService = userService;
        this.emailService = emailService;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/forgotPassword/step1")
    public String forgotPassword( Model model) {
        log.info("forgotPassword() method called ...");
        User user = new User();
        model.addAttribute("user", user);
        return "forgotPassword";
    }


    @PostMapping( value = "/forgotPassword/step1")
    public String checkNewPasswordEmail(@RequestParam String emailAddress, Model model) {
        log.info("checkNewPasswordEmail() method called ...");

        User user = userService.getByEmail(emailAddress);
        if(user != null){
            user.setActivationID(UUID.randomUUID().toString());
            userService.saveUser(user);
            user.signupSuccess(emailService, EMAILTYPE);
        }
        model.addAttribute("theme", "Forgot Password");
        model.addAttribute("message", "We have sent an e-mail to your email address to the given e-mail account.");
        return "information";
    }

    @GetMapping( value = "/forgotPassword/step2/{activation_id}")
    public String renderforgotPassword(@PathVariable String activation_id, Model model) {
        log.info("renderforgotPassword() method called ...");

        log.info("activation id: " + activation_id);

        if(activation_id.equals("undefined")){
            return "newPasswordForm";
        }
        User newUser = userService.handlePasswordActivationID(activation_id);
        log.info("ourUser " + newUser.toString());
        if (newUser == null){
            log.warn("Activation failed.");
            return "registration/invalidActivationLink";
        } else {
            log.info("User profile has been activated.");
        }
        model.addAttribute("user", newUser);
        return "newPasswordForm";
    }

    @PostMapping( value = "/forgotPassword/step2/")
    public String saveNewPassword(User user) {
        log.info("saveNewPasswor() method called ...");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "index";
    }

}
