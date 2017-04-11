package com.codecool.volunti.controller;

import com.codecool.volunti.service.ImageValidationService;
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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Principal;


@Slf4j
@Controller
public class OrganisationProfileController {

    private OrganisationService organisationService;
    private UserService userService;
    private StorageService storageService;
    private ImageValidationService imageValidationService;

    @Autowired
    public OrganisationProfileController(OrganisationService organisationService, UserService userService, StorageService storageService, ImageValidationService imageValidationService) {
        this.organisationService = organisationService;
        this.userService = userService;
        this.storageService = storageService;
        this.imageValidationService = imageValidationService;
    }

    @GetMapping(value = "/profile/organisation")
    public String renderOrganisationProfile(Model model) {
        log.info("renderOrganisationProfile() method called ...");

        Organisation organisation = organisationService.get(1);
        log.info("organisation id: " + organisation.getOrganisationId());
        model.addAttribute("organisation", organisation);
        return "profiles/organisation";
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

    @GetMapping("/profile/organisation/image/profile")
    @ResponseBody
    public ResponseEntity<Resource> serveProfileImage(Principal principal) {
        log.info("serveProfileImage() method called...");
        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();

        Resource file = organisationService.loadProfilePicture(organisation);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping( value = "/profile/organisation/saveProfileImage")
    public String saveProfileImage(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        log.info("saveProfileImage() method called...");
        log.info("File type: " + file.getContentType());

        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();
        log.info("our organisation: " + organisation.toString());

        MultipartFile checkedFile = imageValidationService.imageTypeValidator(file);

        File convFile = storageService.createTemp(file);
        organisation.setProfilePictureFileForSave(convFile);
        organisationService.save(organisation);
        convFile.delete();

        return "profiles/organisation";
    }

    @GetMapping("/profile/organisation/image/background")
    @ResponseBody
    public ResponseEntity<Resource> serveBackgroundImage(Principal principal) {
        log.info("serveBackgroundImage() mehod called...");
        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();

        Resource file = organisationService.loadBackgroundPicture(organisation);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping( value = "/profile/organisation/saveBackgroundImage")
    public String saveBackgroundImage(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        log.info("saveBackgroundImage() method called...");
        log.info("File type: " + file.getContentType());

        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();
        log.info("our organisation: " + organisation.toString());

        MultipartFile checkedFile = imageValidationService.imageTypeValidator(file);

        File convFile = storageService.createTemp(checkedFile);

        BufferedImage resizedImage = imageValidationService.resize(convFile,1349,496);

        File convFile2 = new File(String.valueOf(convFile));
        ImageIO.write(resizedImage, "jpg", convFile2);

        organisation.setBackgroundPictureFileForSave(convFile2);
        organisationService.save(organisation);
        convFile2.delete();

        return "profiles/organisation";
    }



    @GetMapping( value = "/profile/react")
    public String renderReactTemplate(){
        return "profiles/organisationReact";
    }
}
