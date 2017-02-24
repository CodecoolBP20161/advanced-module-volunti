package com.codecool.volunti.controller;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.service.EmailService;
import com.codecool.volunti.service.EmailType;
import com.codecool.volunti.service.OrganisationService;
import com.codecool.volunti.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


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


    @Autowired
    public RegistrationController(OrganisationRepository organisationRepository, OrganisationService organisationService, UserService userService, EmailService emailService) {
        this.organisationRepository = organisationRepository;
        this.organisationService = organisationService;
        this.userService = userService;
        this.emailService = emailService;
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
        if(session.getAttribute("organisation") == null){
            return "redirect:/registration/organisation/step1";
        }
        LOGGER.info("session in the step1: " + session.getAttribute("organisation").toString());
        return "redirect:/registration/organisation/step2/" + organisation.getOrganisationId();
    }

    //render user registration
    @RequestMapping( value = "/registration/organisation/step2/{organisation_id}", method = RequestMethod.GET )
    public String step2(@PathVariable Integer organisation_id, Model model, HttpSession session) {
        LOGGER.info("step2() method called...");
        if(session.getAttribute("organisation") == null){
            LOGGER.info("Step1 is not done, redirecting to step1.");
            return "redirect:/registration/organisation/step1";
        }

        LOGGER.info("session in the step2: " + session.getAttribute("organisation").toString());
        User user = new User();
        if ( session.getAttribute("user") != null ) {
            user = (User) session.getAttribute("user");
        }
        model.addAttribute("user", user);
        model.addAttribute("organisation_id", organisation_id);
        return "registration/step2";
    }

    //save user registration and send the confirmation email
    @RequestMapping( value = "/registration/organisation/step2/", method = RequestMethod.POST )
    public String saveStep2(User user, HttpSession session, Organisation organisation) {
        LOGGER.info("saveStep2() method called...");
        if(session.getAttribute("organisation") == null){
            LOGGER.info("Step1 is not done, redirecting to step1.");
            return "redirect:/registration/organisation/step1";
        }
        LOGGER.info("session: " + session.getAttribute("organisation").toString());

        //save the organisation from the session into database
        organisation = (Organisation) session.getAttribute("organisation");
        Organisation savedOrganisation = organisationService.saveOrganisation(organisation);
        LOGGER.info("organisation saved: {}", savedOrganisation);

        //save the user into database
        user.setOrganisation(organisation);
        User savedUser = userService.saveUser(user);
        LOGGER.info("user saved: {}", savedUser);
        //email sending
        //user.signupSuccess(emailService, EMAILTYPE);

        //clean the session
        session.removeAttribute("organisation");
        session.removeAttribute("user");
        LOGGER.info("Organisation removed from session.");

        return "/registration/step3";
    }

    //render user registration confirmation
    @RequestMapping( value = "/registration/organisation/step3/{activation_id}", method = RequestMethod.GET )
    public String step3(@PathVariable String activation_id, Model model, HttpSession session) {
        LOGGER.info("step3() method called...");
        model.addAttribute("confirmation", activation_id);
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
    public String ValidateFieldIfExists(@RequestBody HashMap<String, String> payload){
        LOGGER.info("Field Validation Started.");
        String entity = payload.get("entityName");
        String fieldName = payload.get("fieldName");
        String valueToCheck = payload.get("value").trim();
        switch (entity) {
            case "user":
                switch (fieldName) {
                    case "email":
                        LOGGER.info("Entity: User");
                        return String.valueOf(userService.getByEmail(valueToCheck) != null);
                    default:
                        LOGGER.error("The given field name in " + entity + " doesnt exists.");
                        throw new NotImplementedException();
                }
            case "organisation":
                switch (fieldName) {
                case "name":
                    LOGGER.info("Entity: Organization");
                    return String.valueOf(organisationService.getByName(valueToCheck) != null);
                default:
                    LOGGER.error("The given field name in " + entity + " doesnt exists.");
                    throw new NotImplementedException();
                }
            default:
                LOGGER.error("Not implemented validation type or wrong request body.");
                throw new NotImplementedException();
        }
    }

}
