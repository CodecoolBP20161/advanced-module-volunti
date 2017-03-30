package com.codecool.volunti.controller;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import java.security.Principal;


@Slf4j
@Controller
public class OrganisationProfileController {

    private OrganisationService organisationService;
    private UserService userService;

    @Autowired
    public OrganisationProfileController(OrganisationService organisationService, UserService userService) {
        this.organisationService = organisationService;
        this.userService = userService;
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
    public Json serveText(Principal principal) {
        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();
        if (organisation == null){
            log.warn("No organisation found in the database with this name.");
            return new Json("this organisation has not been registered yet");
        }
        Json json = new Json(organisation.toString());
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
