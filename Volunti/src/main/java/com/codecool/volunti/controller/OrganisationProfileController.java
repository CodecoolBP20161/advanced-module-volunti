package com.codecool.volunti.controller;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.OrganisationSocialLink;
import com.codecool.volunti.model.OrganisationVideo;
import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.OrganisationSocialLinkRepository;
import com.codecool.volunti.repository.OrganisationVideoRepository;
import com.codecool.volunti.service.ImageValidationService;
import com.codecool.volunti.service.StorageService;
import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@Transactional
public class OrganisationProfileController {

    private OrganisationService organisationService;
    private OrganisationVideoRepository organisationVideoRepository;
    private UserService userService;
    private StorageService storageService;
    private ImageValidationService imageValidationService;
    private OrganisationSocialLinkRepository organisationSocialLinkRepository;

    @Autowired
    public OrganisationProfileController(OrganisationService organisationService, OrganisationVideoRepository organisationVideoRepository, UserService userService, StorageService storageService, ImageValidationService imageValidationService, OrganisationSocialLinkRepository organisationSocialLinkRepository) {
        this.organisationService = organisationService;
        this.organisationVideoRepository = organisationVideoRepository;
        this.userService = userService;
        this.storageService = storageService;
        this.imageValidationService = imageValidationService;
        this.organisationSocialLinkRepository = organisationSocialLinkRepository;
    }

    @GetMapping(value = "/profile/organisation")
    public String renderReactTemplate() {
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
    public boolean saveText(@RequestBody Organisation editedOrganisation, Principal principal){
        log.info("saveText() method called ...");

        log.info(editedOrganisation.toString());
        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();

        organisationService.updateOrganisationProfile(organisation, editedOrganisation);
        organisationService.save(organisation);
        return true;
    }

    @PostMapping( value = "/profile/organisation/saveVideo")
    @ResponseBody
    public boolean saveVideo(@RequestBody OrganisationVideo editedOrganisationVideo, Principal principal){
        log.info("saveVideo() method called ...");

        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();
        List<OrganisationVideo> organisationVideos = organisationService.findVideoByOrganisationId(organisation);
//        if (!organisationVideos.isEmpty()){
//            for (OrganisationVideo video : organisationVideos) {
//                organisationVideoRepository.delete(video);
//            }
//        }
        editedOrganisationVideo.setOrganisationId(organisation);
        organisationService.save(editedOrganisationVideo);
        return true;

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
    public boolean saveProfileImage(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        log.info("saveProfileImage() method called...");
        log.info("File type: " + file.getContentType());

        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();
        log.info("our organisation: " + organisation.toString());

        if(imageValidationService.imageTypeValidator(file)){
            File convFile = storageService.createTemp(file);
            BufferedImage resizedImage = imageValidationService.resize(convFile,309,233);

            File convFile2 = new File(String.valueOf(convFile));
            ImageIO.write(resizedImage, "jpg", convFile2);

            organisation.setProfilePictureFileForSave(convFile2);
            organisationService.save(organisation);
            return convFile2.delete();
        }else{
            return false;
        }
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
    @ResponseBody
    public Boolean saveBackgroundImage(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {

        log.info("file size: " + file.getSize());
        log.info("saveBackgroundImage() method called...");
        log.info("File type: " + file.getContentType());

        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();
        log.info("our organisation: " + organisation.toString());

        if(imageValidationService.imageTypeValidator(file)){
            File convFile = storageService.createTemp(file);
            BufferedImage resizedImage = imageValidationService.resize(convFile,1349,496);

            File convFile2 = new File(String.valueOf(convFile));
            ImageIO.write(resizedImage, "jpg", convFile2);
            organisation.setBackgroundPictureFileForSave(convFile2);
            organisationService.save(organisation);
            return convFile2.delete();
        }else{
            return false;
        }
    }

    @PostMapping(value = "/profile/organisation/process")
    @ResponseBody
    public Boolean saveSocialLinks(@RequestBody List<OrganisationSocialLink> organisationSocialLinks, Principal principal){
        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();

        OrganisationSocialLink organisationSocialLink;
        List<OrganisationSocialLink> newOrgList = new ArrayList<>();
        organisationSocialLinkRepository.deleteByOrganisationId(organisation);

        for (OrganisationSocialLink item : organisationSocialLinks) {
            organisationSocialLink = new OrganisationSocialLink(item.getSocialLinkType(), item.getSocialLinkUrl(), organisation);
            newOrgList.add(organisationSocialLink);
        }

        organisationSocialLinkRepository.save(newOrgList);
        organisation.setOrganisationSocialLinks(newOrgList);

        log.info(organisation.getOrganisationSocialLinks().toString());
        return true;
    }

}
