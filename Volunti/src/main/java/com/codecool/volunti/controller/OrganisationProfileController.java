package com.codecool.volunti.controller;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.service.email.EmailService;
import com.codecool.volunti.service.email.EmailType;
import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;


@Slf4j
@Controller
public class OrganisationProfileController {

    private static EmailType EMAILTYPE = EmailType.CONFIRMATION;

    private OrganisationRepository organisationRepository;
    private OrganisationService organisationService;
    private UserService userService;
    private EmailService emailService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public OrganisationProfileController(OrganisationRepository organisationRepository,
                                  OrganisationService organisationService,
                                  UserService userService,
                                  EmailService emailService) {
        this.organisationRepository = organisationRepository;
        this.organisationService = organisationService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping(value = "/profile/organisation")
    public String renderOrganisationProfile(Model model) {
        log.info("renderOrganisationRProfile() method called ...");

        Organisation organisation = organisationService.get(1);
        log.info("organisation id: " + organisation.getOrganisationId());
        model.addAttribute("organisation", organisation);
        return "profiles/organisation";
    }


    @GetMapping("/profile/organisation/image")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(Model model) {

        Organisation organisation = organisationService.get(1);
        Resource file = organisationService.loadProfilePicture(organisation);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @GetMapping("/profile/organisation/text")
    @ResponseBody
    public Json serveText(Model model) {
        ObjectMapper mapper = new ObjectMapper();
        Organisation organisation = organisationService.get(1);
        Json json = new Json(organisation.toString());

        log.info("organisation converted to JSON: {}", json.value());
        return json;
    }


    @PostMapping( value = "/profile/organisation/saveText")
    public String saveText(Organisation organisation){
        log.info("saveText() method called ...");

        //TODO: Save the text...
        organisationService.save(organisation);
        return "profiles/organisation";  //TODO: What do we want to return here?!
    }

    @PostMapping( value = "/profile/organisation/saveImage")
    public String saveImage(@RequestParam("file") MultipartFile file){
        log.info("saveImage() method called...");

        Organisation organisation = organisationService.get(1);
        log.info("our organisation: " + organisation.toString());
        organisation.setProfilePictureFileForSave(file);
        organisationService.save(organisation);

        return "profiles/organisation";
    }
}
