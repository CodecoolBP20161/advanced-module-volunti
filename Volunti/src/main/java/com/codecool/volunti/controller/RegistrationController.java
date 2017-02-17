package com.codecool.volunti.controller;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.service.NotificationService;
import com.codecool.volunti.service.OrganisationService;
import com.codecool.volunti.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("organisation")
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
    public String step1(Model model, HttpSession session) {
        LOGGER.info("step1() method called ...");
        Organisation organisation = new Organisation();
        if ( session.getAttribute("organisation") != null ) {
            organisation = (Organisation) session.getAttribute("organisation");
        }
        model.addAttribute("organisation", organisation);
        return "registration/organisation/step1";
    }

    //save organisation registration
    @RequestMapping( value = "/registration/organisation/step1", method = RequestMethod.POST )
    public String saveStep1(Organisation organisation, HttpSession session) {
        LOGGER.info("saveStep1() method called...");
        LOGGER.info("session in the step1: " + session.getAttribute("organisation").toString());

        return "redirect:/registration/organisation/step2/" + organisation.getOrganisationId();
    }

    //render user registration
    @RequestMapping( value = "/registration/organisation/step2/{organisation_id}", method = RequestMethod.GET )
    public String step2(@PathVariable Integer organisation_id, Model model, HttpSession session) {
        LOGGER.info("step2() method called...");
        LOGGER.info("session in the step2: " + session.getAttribute("organisation").toString());
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("organisation_id", organisation_id);
        return "registration/step2";
    }

    //save user registration and send the confirmation email
    @RequestMapping( value = "/registration/organisation/step2/{organisation_id}", method = RequestMethod.POST )
    public String saveStep2(@PathVariable Integer organisation_id, User user, HttpSession session, Organisation organisation) {
        LOGGER.info("saveStep2() method called...");
        LOGGER.info("session: " + session.getAttribute("organisation").toString());

        //save the organisation from the session into database
        organisation = (Organisation) session.getAttribute("organisation");
        Organisation savedOrganisation = organisationService.saveOrganisation(organisation);

        //save the user into database
        user.setOrganisation(organisationService.get(organisation_id));
        User savedUser = userService.saveUser(user);

        //email sending...
        signupSuccess(user);

        //clean the session
        session.setAttribute("organisation", new Organisation());
        LOGGER.info("session cleaned: " + session.getAttribute("organisation").toString());

        return "/registration/step3";
    }

    //@RequestMapping("/signup-success")
    public String signupSuccess(User user) {
        LOGGER.info("signupSuccess() method called...");

        // create user
        try {
            // send a notification
            User savedUser = userService.saveUser(user) ;
            user.signupSuccess(notificationService);
        } catch (Exception e) {
            LOGGER.warn("Email not sent");
        }
        return "Thank you for registering with us.";

    }

}
