package com.codecool.volunti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrganisationProfileController {

    @GetMapping("/organisation")
    public String renderOrganisationProfile() {
        return "profiles/organisation";
    }
}
