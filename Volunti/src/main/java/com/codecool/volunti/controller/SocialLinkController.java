package com.codecool.volunti.controller;


import com.codecool.volunti.model.OrganisationSocialLink;
import com.codecool.volunti.service.model.OrganisationSocialLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SocialLinkController {

    OrganisationSocialLinkService organisationSocialLinkService;

    @Autowired
    public SocialLinkController(OrganisationSocialLinkService organisationSocialLinkService) {
        this.organisationSocialLinkService = organisationSocialLinkService;
    }

    @GetMapping(value = "/organisation/social-links")
    public List<OrganisationSocialLink> serveSocialLink(){
        return organisationSocialLinkService.findAll();
    }
}
