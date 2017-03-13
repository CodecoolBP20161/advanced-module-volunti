package com.codecool.volunti.controller;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.service.*;
import com.codecool.volunti.service.email.EmailService;
import com.codecool.volunti.service.email.EmailType;
import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;


@Slf4j
@Controller
public class OrganisationProfileController {

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
        this.storageService.deleteAll();
        this.storageService.init();
    }

    @GetMapping(value = "/profile/organisation")
    public String renderOrganisationProfile(Model model) {
        log.info("renderOrganisationRProfile() method called ...");

        Organisation organisation = organisationRepository.findOne(1);
        log.info("organisation id: " + organisation.getOrganisationId());
        Path imagePath = storageService.load(((Integer) organisation.getOrganisationId()).toString());
        log.info("imagePath: " + imagePath);

        model.addAttribute("organisation", organisation);
        return "profiles/organisation";
    }

    


    @PostMapping( value = "/profile/organisation/saveText")
    public String saveText(Organisation organisation){
        log.info("saveText() method called ...");

        //TODO: Save the text...
        organisationService.saveOrganisation(organisation);
        return "profiles/organisation";  //TODO: What do we want to return here?!
    }

    @PostMapping( value = "/profile/organisation/saveImage")
    public String  saveImage(@RequestParam("file") MultipartFile file){
        log.info("saveImage() method called...");

        Organisation organisation = organisationRepository.findOne(1);
        log.info("our organisation: " + organisation.toString());
        storageService.store(file, organisation);

        String hashedOrganisation = ((Integer) organisation.getOrganisationId()).toString();
        organisation.setProfilePicture(hashedOrganisation);
        organisationService.saveOrganisation(organisation);

        return "profiles/organisation";
    }
}
