package com.codecool.volunti.controller;


import com.codecool.volunti.model.OrganisationSocialLink;
import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.OrganisationSocialLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SocialLinkController {

    OrganisationService organisationService;

    @Autowired
    public SocialLinkController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @GetMapping(value = "/organisation/social-links")
    public List<OrganisationSocialLink> serveSocialLink(){
        return organisationService.findByOrganisationId(organisationService.get(1));
    }
}
