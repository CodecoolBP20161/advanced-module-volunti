package com.codecool.volunti.controller;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.service.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class OrganisationProfileController {

    private Logger LOGGER = LoggerFactory.getLogger(OrganisationProfileController.class);

    private static EmailType EMAILTYPE = EmailType.CONFIRMATION;

    private OrganisationRepository organisationRepository;
    private OrganisationService organisationService;
    private UserService userService;
    private EmailService emailService;
    private BCryptPasswordEncoder passwordEncoder;

    private final StorageService storageService;

    @Autowired
    public OrganisationProfileController(OrganisationRepository organisationRepository,
                                  OrganisationService organisationService,
                                  UserService userService,
                                  EmailService emailService, StorageService storageService) {
        this.organisationRepository = organisationRepository;
        this.organisationService = organisationService;
        this.userService = userService;
        this.emailService = emailService;
        this.storageService = storageService;
    }

    @GetMapping(value = "/profile/organisation")
    public String renderOrganisationProfile(Model model) {
        log.info("renderOrganisationRProfile() method called ...");

        Organisation organisation = new Organisation();
        model.addAttribute("organisation", organisation);
        return "profiles/organisation";
    }

    @PostMapping( value = "/profile/organisation/saveText")
    public String saveText(Organisation organisation){
        log.info("saveText() method called ...");

        organisationService.saveOrganisation(organisation);
        return "profiles/organisation";  //TODO: What do we want to return here?!
    }

    @PostMapping( value = "/profile/organisation/saveImage")
    public String  saveImage(@RequestParam("file") MultipartFile file, Organisation organisation){
        log.info("saveImage() method called...");

        String hashedOrganisation = BCrypt.hashpw(organisation.getName(), ((Integer) organisation.getOrganisationId()).toString());
        organisation.setProfilePicture(hashedOrganisation);
        storageService.store(file);


        return "profiles/organisation";  //TODO: What do we want to return here?!
    }
}
