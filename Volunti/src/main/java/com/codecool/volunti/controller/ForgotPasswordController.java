package com.codecool.volunti.controller;


import com.codecool.volunti.model.User;
import com.codecool.volunti.service.email.EmailService;
import com.codecool.volunti.service.email.EmailType;
import com.codecool.volunti.service.model.UserService;
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


    private UserService userService;
    private EmailService emailService;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ForgotPasswordController(UserService userService, EmailService emailService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/forgotPassword/step1")
    public String forgotPassword() {
        log.info("forgotPassword() method called ...");
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
        model.addAttribute("message", "If your email address exists in our database,we have sent an e-mail to you with a reset link.");
        return "information";
    }

    @GetMapping( value = "/forgotPassword/step2/{activation_id}")
    public String renderforgotPassword(@PathVariable String activation_id, Model model) {
        log.info("renderforgotPassword() method called ...");
        User newUser = userService.handlePasswordActivationID(activation_id);
        if (newUser == null) {
            log.warn("No user found in the database with this email address.");
            model.addAttribute("theme", "Forgot Password");
            model.addAttribute("message", "Activation link is invalid. Please contact us for more help.");
            return "information";
        } else {
            log.info("User found in the database.");
        }
        model.addAttribute("user", newUser);
        return "newPasswordForm";
    }

    @PostMapping( value = "/forgotPassword/step2/")
    public String saveNewPassword(User user) {
        log.info("saveNewPassword() method called ...");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "index";
    }

}
