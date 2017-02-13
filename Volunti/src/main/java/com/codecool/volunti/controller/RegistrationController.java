package com.codecool.volunti.controller;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.service.NotificationService;
import com.codecool.volunti.service.OrganisationService;
import com.codecool.volunti.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private OrganisationRepository organisationRepository;
    private OrganisationService organisationService;
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    //render organisation registration
    @RequestMapping( value = "/registration/organisation/step1", method = RequestMethod.GET )
    public String step1() {
        return "registration/organisation/step1";
    }

    //save organisation registration
    @RequestMapping( value = "/registration/organisation/step1", method = RequestMethod.POST )
    public String saveStep1(Organisation organisation) {
        Organisation savedOrganisation = organisationService.saveOrganisation(organisation);
        LOGGER.info("savedOrganisation: " + savedOrganisation);
        return "redirect:/registration/organisation/step2";
    }

    //render user registration
    @RequestMapping( value = "/registration/organisation/step2", method = RequestMethod.GET )
    public String step2() {
        return "registration/user";
    }

    //save user registration
    @RequestMapping( value = "/registration/organisation/step2", method = RequestMethod.POST )
    public String save(User user) {
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
