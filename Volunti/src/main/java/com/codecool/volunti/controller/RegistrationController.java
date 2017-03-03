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
    public String renderOrganisationRegistration(Model model, HttpSession session) {
        LOGGER.info("renderOrganisationRegistration() method called ...");
        Organisation organisation = new Organisation();
        if ( session.getAttribute("organisation") != null ) {
            organisation = (Organisation) session.getAttribute("organisation");
        }
        model.addAttribute("organisation", organisation);
        return "registration/organisation/organisation";
    }

    //save organisation registration
    @RequestMapping( value = "/registration/organisation/organisation", method = RequestMethod.POST )
    public String saveOrganisation(Organisation organisation, HttpSession session) {
        LOGGER.info("saveOrganisation() method called...");
        if(session.getAttribute("organisation") == null){
            return "redirect:/registration/organisation/organisation";
        }
        return "redirect:/registration/organisation/user/" + organisation.getOrganisationId();
    }

    //render user registration
    @RequestMapping( value = "/registration/organisation/user/{organisation_id}", method = RequestMethod.GET )
    public String renderUserRegistration(@PathVariable Integer organisation_id, Model model, HttpSession session) {
        LOGGER.info("renderUserRegistration() method called...");
        if(session.getAttribute("organisation") == null){
            LOGGER.info("Step1 is not done, redirecting to renderOrganisationRegistration.");
            return "redirect:/registration/organisation/organisation";
        }

        LOGGER.info("session in the renderUserRegistration: " + session.getAttribute("organisation").toString());
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
    public String saveUser(User user, HttpSession session, Organisation organisation, Model model) {
        LOGGER.info("saveUser() method called...");
        if(session.getAttribute("organisation") == null){
            LOGGER.info("Step1 is not done, redirecting to renderOrganisationRegistration.");
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
        model.addAttribute("theme", "Registration");
        model.addAttribute("message", "Registration successful! We have sent an e-mail to your email address to the given e-mail account."
                                        + "<br />Please confirm your account using the given link.");
        return "information";
    }

    //render user registration confirmation
    @RequestMapping( value = "/registration/organisation/success/{activation_id}", method = RequestMethod.GET )
    public String confirmation(@PathVariable String activation_id, Model model, HttpSession session) {
        LOGGER.info("confirmation() method called...");
        User newUser = userService.confirmRegistration(activation_id);
        if (newUser == null){
            LOGGER.warn("Activation failed.");
            model.addAttribute("theme", "Registration");
            model.addAttribute("message", "Account confirmation is unsuccessful.<br />Please try again or contact us for more help.");
            return "information";
        } else{
            LOGGER.info("User profile has been activated.");
            //TODO: Log in newUser. Note:It can be also null for various reasons(see ConfirmRegistration())
        }

        model.addAttribute("user", newUser);
        model.addAttribute("theme", "Registration");
        model.addAttribute("message", "Account Confirmation is done.");
        return "information";

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
