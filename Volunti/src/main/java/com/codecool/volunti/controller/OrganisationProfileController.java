package com.codecool.volunti.controller;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.service.StorageService;
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

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;


@Slf4j
@Controller
public class OrganisationProfileController {

    private OrganisationService organisationService;
    private UserService userService;
    private StorageService storageService;

    @Autowired
    public OrganisationProfileController(OrganisationService organisationService, UserService userService, StorageService storageService) {
        this.organisationService = organisationService;
        this.userService = userService;
        this.storageService = storageService;
    }

    @GetMapping(value = "/profile/organisation")
    public String renderOrganisationProfile(Model model) {
        log.info("renderOrganisationProfile() method called ...");

        Organisation organisation = organisationService.get(1);
        log.info("organisation id: " + organisation.getOrganisationId());
        model.addAttribute("organisation", organisation);
        return "profiles/organisation";
    }

    @GetMapping("/profile/organisation/image")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(Principal principal) {
        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();

        Resource file = organisationService.loadProfilePicture(organisation);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @GetMapping("/profile/organisation/text")
    @ResponseBody
    public Organisation serveText(Principal principal) {
        User user = userService.getByEmail(principal.getName());
        return user.getOrganisation();
    }

    @PostMapping( value = "/profile/organisation/saveText")
    @ResponseBody
    public String saveText(Organisation organisation){
        log.info("saveText() method called ...");

        //TODO: Save the text...
        organisationService.save(organisation);
        return "profiles/organisation";  //TODO: What do we want to return here?!
    }

    @PostMapping( value = "/profile/organisation/saveImage")
    public String saveImage(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        log.info("saveImage() method called...");

        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();
        log.info("our organisation: " + organisation.toString());

        if(file.getContentType().endsWith("png") ||file.getContentType().endsWith("jpg") && !file.isEmpty()){
            log.info("this is png or jpg");

            File convFile = storageService.createTemp(file);
            organisation.setProfilePictureFileForSave(convFile);
            organisationService.save(organisation);
            convFile.delete();

        } else{
            log.info("it isn't png or jpg or it is empty");
        }
        return "profiles/organisation";
    }

    @GetMapping( value = "/profile/react")
    public String renderReactTemplate(){
        return "profiles/organisationReact";
    }
}
