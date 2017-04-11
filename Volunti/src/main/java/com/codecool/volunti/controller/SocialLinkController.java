package com.codecool.volunti.controller;


import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SocialLinkController {

    private OrganisationService organisationService;
    private UserService userService;

    @Autowired
    public SocialLinkController(OrganisationService organisationService, UserService userService) {
        this.organisationService = organisationService;
        this.userService = userService;
    }
}
