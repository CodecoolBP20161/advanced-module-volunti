package com.codecool.volunti.controller;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;


@Slf4j
@Controller
public class OrganisationProfileController {

    private OrganisationService organisationService;
    private UserService userService;
    private ObjectMapper objectMapper = new ObjectMapper();

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
    public ResponseEntity<Resource> serveFile(Principal principal) {
        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();

        Resource file = organisationService.loadProfilePicture(organisation);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping( value = "/profile/organisation/saveImage")
    public String saveImage(@RequestParam("file") MultipartFile file, Principal principal){
        log.info("saveImage() method called...");

        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();

        log.info("our organisation: " + organisation.toString());
        organisation.setProfilePictureFileForSave(file);
        organisationService.save(organisation);

        return "profiles/organisation";
    }

    @GetMapping("/profile/organisation/text")
    @ResponseBody
    public HashMap serveText(Principal principal) throws JsonProcessingException {
        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();
        HashMap hashMap = new HashMap<>();

        if (organisation == null) {
            log.warn("No organisation found in the database with this user ID.");
            hashMap.put("error", "You haven't registered an organisation yet.");
        } else {
            hashMap = objectMapper.convertValue(organisation, HashMap.class);
        }
        return hashMap;
    }

    @PostMapping( value = "/profile/organisation/saveText")
    public String saveText(Organisation organisation){
        log.info("saveText() method called ...");
        organisationService.save(organisation);
        return "profiles/organisation";
    }

    @GetMapping( value = "/profile/react")
    public String renderReactTemplate(){
        return "profiles/organisationReact";
    }
}
