package com.codecool.volunti.controller;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@SessionAttributes({"organisation", "user"})
public class RegistrationController {

    private Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private static EmailType EMAILTYPE = EmailType.CONFIRMATION;

    private OrganisationRepository organisationRepository;
    private OrganisationService organisationService;
    private UserService userService;
    private EmailService emailService;
    private ValidationService validationService = new ValidationService(organisationService, userService);


    @Autowired
    public RegistrationController(OrganisationRepository organisationRepository,
                                  OrganisationService organisationService,
                                  UserService userService,
                                  EmailService emailService,
                                  ValidationService validationService) {
        this.organisationRepository = organisationRepository;
        this.organisationService = organisationService;
        this.userService = userService;
        this.emailService = emailService;
        this.validationService = validationService;
    }


    //render organisation registration
    @RequestMapping( value = "/registration/organisation/organisation", method = RequestMethod.GET )
    public String step1(Model model, HttpSession session) {
        LOGGER.info("step1() method called ...");
        Organisation organisation = new Organisation();
        if ( session.getAttribute("organisation") != null ) {
            organisation = (Organisation) session.getAttribute("organisation");
        }
        model.addAttribute("organisation", organisation);
        return "registration/organisation/organisation";
    }

    //save organisation registration
    @RequestMapping( value = "/registration/organisation/organisation", method = RequestMethod.POST )
    public String saveStep1(Organisation organisation, HttpSession session) {
        LOGGER.info("saveStep1() method called...");
        if(session.getAttribute("organisation") == null){
            return "redirect:/registration/organisation/organisation";
        }
        LOGGER.info("session in the step1: " + session.getAttribute("organisation").toString());
        return "redirect:/registration/organisation/user/" + organisation.getOrganisationId();
    }

    //render user registration
    @RequestMapping( value = "/registration/organisation/user/{organisation_id}", method = RequestMethod.GET )
    public String step2(@PathVariable Integer organisation_id, Model model, HttpSession session) {
        LOGGER.info("step2() method called...");
        if(session.getAttribute("organisation") == null){
            LOGGER.info("Step1 is not done, redirecting to step1.");
            return "redirect:/registration/organisation/organisation";
        }

        LOGGER.info("session in the step2: " + session.getAttribute("organisation").toString());
        User user = new User();
        if ( session.getAttribute("user") != null ) {
            user = (User) session.getAttribute("user");
        }
        model.addAttribute("user", user);
        model.addAttribute("organisation_id", organisation_id);
        return "registration/user";
    }

    //save user registration and send the confirmation email
    @RequestMapping( value = "/registration/organisation/user/", method = RequestMethod.POST )
    public String saveStep2(User user, HttpSession session, Organisation organisation) {
        LOGGER.info("saveStep2() method called...");
        if(session.getAttribute("organisation") == null){
            LOGGER.info("Step1 is not done, redirecting to step1.");
            return "redirect:/registration/organisation/organisation";
        }
        LOGGER.info("session: " + session.getAttribute("organisation").toString());

        //save the organisation from the session into database
        organisation = (Organisation) session.getAttribute("organisation");
        Organisation savedOrganisation = organisationService.saveOrganisation(organisation);
        LOGGER.info("organisation saved: {}", savedOrganisation);

        //save the user into database
        user.setOrganisation(organisation);
        user.hashPassword(user.getPassword());
        User savedUser = userService.saveUser(user);
        LOGGER.info("user saved: {}", savedUser);
        //email sending
        user.signupSuccess(emailService, EMAILTYPE);

        //clean the session
        session.removeAttribute("organisation");
        LOGGER.info("UUID: ", savedUser.getActivationID());
        session.removeAttribute("user");
        LOGGER.info("Organisation removed from session.");

        return "/registration/success";
    }

    //render user registration confirmation
    @RequestMapping( value = "/registration/organisation/success/{activation_id}", method = RequestMethod.GET )
    public String step3(@PathVariable String activation_id, Model model, HttpSession session) {
        LOGGER.info("step3() method called...");
        User newUser = userService.confirmRegistration(activation_id);
        if (newUser == null){
            LOGGER.warn("Activation failed.");
            return "registration/invalidActivationLink";
        } else{
            LOGGER.info("User profile has been activated.");
            //TODO: Log in newUser. Note:It can be also null for various reasons(see ConfirmRegistration())
        }

        model.addAttribute("user", newUser);
        return "registration/step4";
    }
    /* Expected Request body:
    {
        entityName: entityName,
        fieldName: fieldName,
        value: value
    }
    */
    @RequestMapping( value = "/registration/ValidateFieldIfExists", method = RequestMethod.POST)
    @ResponseBody
    public String validateFieldIfExists(@RequestBody HashMap<String, String> payload){
        LOGGER.info("payload: " + payload.toString());
       return String.valueOf(validationService.checkIfValueExists(payload));
    }

}
