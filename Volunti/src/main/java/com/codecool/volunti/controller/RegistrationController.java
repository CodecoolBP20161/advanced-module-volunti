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
        Organisation organisation = new Organisation();
        model.addAttribute("organisation", organisation);
        LOGGER.info("render registration page 1");
        return "registration/organisation/step1";
    }

    //save organisation registration
    @RequestMapping( value = "/registration/organisation/step1", method = RequestMethod.POST )
    public String saveStep1(Organisation organisation) {
        LOGGER.info("benne van" + organisation);
        Organisation savedOrganisation = organisationService.saveOrganisation(organisation);
        LOGGER.info("savedOrganisation: " + savedOrganisation);
        return "redirect:/registration/organisation/step2/" + organisation.getId();
    }

    //render user registration
    @RequestMapping( value = "/registration/organisation/step2/{organisation_id}", method = RequestMethod.GET )
    public String step2(@PathVariable Integer organisation_id ,Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("organisation_id", organisation_id);
        LOGGER.info("render registration page 2");
        return "registration/user";
    }

    //save user registration
    @RequestMapping( value = "/registration/organisation/step2/{organisation_id}", method = RequestMethod.POST )
    public String save(@PathVariable Integer organisation_id, User user) {
        user.setOrganisation(organisationService.get(organisation_id));
        User savedUser = userService.saveUser(user);
        LOGGER.info("savedUser: " + savedUser);
        return "/registration/organisation/confirmation";   //Should I redirect somewhere else?
    }

    @RequestMapping("/signup-success")
    public String signupSuccess() throws Exception {

        // create user
        User user = new User();
        user.setFirstName("Moni");
        user.setLastName("Lombos");
        user.setEmail("lombos.monika@gmail.com");

        // send a notification
        notificationService.sendNotification(user);


        return "Thank you for registering with us.";
    }




}
