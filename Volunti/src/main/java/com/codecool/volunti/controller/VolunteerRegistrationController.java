package com.codecool.volunti.controller;

import com.codecool.volunti.repository.VolunteerRepository;
import com.codecool.volunti.service.email.EmailService;
import com.codecool.volunti.service.model.UserService;
import com.codecool.volunti.service.model.VolunteerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by levente on 2017.04.04..
 */
@Controller
@RequestMapping(value = "/registration/volunteer")
@SessionAttributes({"volunteer", "user"})
public class VolunteerRegistrationController {

    private UserService userService;
    private EmailService emailService;
    private VolunteerService volunteerService;
    private VolunteerRepository volunteerRepository;
//    private ValidationService validationService = new ValidationService(userService,volunteerService);
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/step1")
    public String stepOne() {
        return "registration/volunteer/volunteerForm";
    }
}
