package com.codecool.volunti.controller;

import com.codecool.volunti.model.OrganisationVideo;
import com.codecool.volunti.service.ImageValidationService;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.service.StorageService;
import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;


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
    public String saveText(@RequestBody Organisation editedOrganisation, Principal principal){
        log.info("saveText() method called ...");

        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();

        organisation.setMission(editedOrganisation.getMission());
        organisation.setDescription1(editedOrganisation.getDescription1());
        organisation.setDescription2(editedOrganisation.getDescription2());

        organisationService.save(organisation);
        return "profiles/organisation";
    }

    @PostMapping( value = "/profile/organisation/saveVideo")
    @ResponseBody
    public String saveVideo(@RequestBody OrganisationVideo editedOrganisationVideo, Principal principal){
        log.info("saveVideo() method called ...");

        User user = userService.getByEmail(principal.getName());
        Organisation organisation = user.getOrganisation();
        OrganisationVideo organisationVideo = new OrganisationVideo();
        organisationVideo.setOrganisationId(organisation);
        organisationVideo.setEmbedCode(editedOrganisationVideo.getEmbedCode());

//        organisationService.save(organisationVideo);
        return "profiles/organisation";
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
            convFile2.delete();
            return true;
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
    public Boolean saveBackgroundImage(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        log.info("here it is");
        log.info("file size: " + file.getSize());

        /*if(bindingResult.hasErrors()){
            log.error(file.getSize() + "error");
        }*/

        /*if(file.getSize() >= 1048576){
            BindingResult bindingResult = new BeanPropertyBindingResult(file, "image to upload");
            bindingResult.addError(new ObjectError("multipartFile","image size is bigger than 1048576"));
            log.error(bindingResult.getErrorCount() + bindingResult.toString());
            throw new FileUploadBase.SizeLimitExceededException("Too big picture", 1048576, file.getSize());
        }*/


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
            convFile2.delete();
            return true;
        }else{
            return false;
        }

    }


}
