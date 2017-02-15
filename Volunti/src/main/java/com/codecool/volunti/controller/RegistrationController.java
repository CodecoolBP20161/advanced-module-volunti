package com.codecool.volunti.controller;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.service.NotificationService;
import com.codecool.volunti.service.OrganisationService;
import com.codecool.volunti.service.UserService;
import org.aspectj.weaver.IntMap;
import org.aspectj.weaver.ast.Not;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationController {

    private Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private OrganisationRepository organisationRepository;
    private OrganisationService organisationService;
    private UserService userService;
    private NotificationService notificationService;

    @Autowired
    public RegistrationController(OrganisationRepository organisationRepository, OrganisationService organisationService, UserService userService, NotificationService notificationService) {
        this.organisationRepository = organisationRepository;
        this.organisationService = organisationService;
        this.userService = userService;
        this.notificationService = notificationService;
    }


    //render organisation registration
    @RequestMapping( value = "/registration/organisation/step1", method = RequestMethod.GET )
    public String step1(Model model) {
        LOGGER.info("step1() method called ...");
        Organisation organisation = new Organisation();
        model.addAttribute("organisation", organisation);
        return "registration/organisation/step1";
    }

    //save organisation registration
    @RequestMapping( value = "/registration/organisation/step1", method = RequestMethod.POST )
    public String saveStep1(Organisation organisation) {
        LOGGER.info("saveStep1() method called...");
        Organisation savedOrganisation = organisationService.saveOrganisation(organisation);
        LOGGER.info("savedOrganisation: " + savedOrganisation);
        return "redirect:/registration/organisation/step2/" + organisation.getOrganisationId();
    }

    //render user registration
    @RequestMapping( value = "/registration/organisation/step2/{organisation_id}", method = RequestMethod.GET )
    public String step2(@PathVariable Integer organisation_id ,Model model) {
        LOGGER.info("step2() method called...");
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("organisation_id", organisation_id);
        return "registration/user";
    }

    //save user registration
    @RequestMapping( value = "/registration/organisation/step2/{organisation_id}", method = RequestMethod.POST )
    public String saveStep2(@PathVariable Integer organisation_id, User user) {
        LOGGER.info("saveStep2() method called...");
        user.setOrganisation(organisationService.get(organisation_id));
        User savedUser = userService.saveUser(user);

        signupSuccess(user);

        return "/registration/organisation/confirmation";   //Should I redirect somewhere else?
    }

    //@RequestMapping("/signup-success")
    public String signupSuccess(User user) {
        LOGGER.info("signupSuccess() method called...");

        // create user
        try {
            // send a notification
            notificationService.sendNotification(user);
        } catch (Exception e) {
            LOGGER.warn("Email not sent");
        }
        return "Thank you for registering with us.";
    }




}
