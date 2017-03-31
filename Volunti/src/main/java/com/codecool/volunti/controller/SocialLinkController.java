package com.codecool.volunti.controller;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.OrganisationSocialLink;
import com.codecool.volunti.model.User;
import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


@RestController
public class SocialLinkController {

    private OrganisationService organisationService;
    private UserService userService;

    @Autowired
    public SocialLinkController(OrganisationService organisationService, UserService userService) {
        this.organisationService = organisationService;
        this.userService = userService;
    }

    @GetMapping(value = "/profile/organisation/social-links")
    public List<OrganisationSocialLink> serveSocialLink(Principal principal){
        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();

        return organisationService.findByOrganisationId(organisation);
    }
}
