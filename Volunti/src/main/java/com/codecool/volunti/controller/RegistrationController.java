package com.codecool.volunti.controller;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.service.email.EmailService;
import com.codecool.volunti.service.email.EmailType;
import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.RoleService;
import com.codecool.volunti.service.model.UserService;
import com.codecool.volunti.service.model.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@SessionAttributes({"organisation", "user"})
public class RegistrationController {

    private static final EmailType EMAILTYPE = EmailType.CONFIRMATION;
    private static final String ORGANISATION = "organisation";
    private static final String THEME = "theme";
    private static final String REGISTRATION = "Registration";
    private static final String INFORMATION = "information";
    private static final String MESSAGE = "message";
    private static final String STEP1 = "redirect:/registration/organisation/step1";

    private OrganisationService organisationService;
    private UserService userService;
    private EmailService emailService;
    private ValidationService validationService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(
                                  OrganisationService organisationService,
                                  UserService userService,
                                  EmailService emailService,
                                  ValidationService validationService, BCryptPasswordEncoder passwordEncoder) {
        this.organisationService = organisationService;
        this.userService = userService;
        this.emailService = emailService;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
    }



    @GetMapping( value = "/registration/organisation/step1")
    public String renderOrganisationRegistration(Model model, HttpSession session) {
        log.info("renderOrganisationRegistration() method called ...");
        Organisation organisation = new Organisation();
        if ( session.getAttribute(ORGANISATION) != null ) {
            organisation = (Organisation) session.getAttribute(ORGANISATION);
        }
        model.addAttribute(ORGANISATION, organisation);
        return "registration/organisation/organisation";
    }

    @PostMapping( value = "/registration/organisation/step1")
    public String saveOrganisation(Organisation organisation, HttpSession session) {
        log.info("saveOrganisation() method called...");
        if(session.getAttribute(ORGANISATION) == null){
            return STEP1;
        }
        return "redirect:/registration/organisation/step2/" + organisation.getOrganisationId();
    }

    @GetMapping( value = "/registration/organisation/step2/{organisation_id}")
    public String renderUserRegistration(@PathVariable Integer organisation_id, Model model, HttpSession session) {
        log.info("renderUserRegistration() method called...");
        if(session.getAttribute(ORGANISATION) == null){
            log.info("Step1 is not done, redirecting to renderOrganisationRegistration.");
            return STEP1;
        }

        log.info("session in the renderUserRegistration: " + session.getAttribute(ORGANISATION).toString());
        User user = new User();
        if ( session.getAttribute("user") != null ) {
            user = (User) session.getAttribute("user");
        }
        model.addAttribute("action","/registration/organisation/step2/");
        model.addAttribute("button", "submit");
        model.addAttribute("user", user);
        model.addAttribute("organisation_id", organisation_id);
        return "registration/user";
    }

    //save user registration and send the confirmation email
    @PostMapping(value = "/registration/organisation/step2/")
    public String saveUser(User user, HttpSession session, Organisation organisation, Model model) {
        log.info("saveUser() method called...");
        if(session.getAttribute(ORGANISATION) == null){
            log.info("Step1 is not done, redirecting to renderOrganisationRegistration.");
            return "redirect:/registration/organisation/step1";
        }
        log.info("session: " + session.getAttribute(ORGANISATION).toString());

        //save the organisation from the session into database
        organisation = (Organisation) session.getAttribute(ORGANISATION);
        Organisation savedOrganisation = organisationService.saveOrganisation(organisation);
        log.info("organisation saved: {}", savedOrganisation);

        //save the user into database
        user.setOrganisation(organisation);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userService.saveUser(user);
        log.info("user saved: {}", savedUser);
        //email sending
        user.signupSuccess(emailService, EMAILTYPE);

        //clean the session
        session.removeAttribute(ORGANISATION);
        session.removeAttribute("user");
        log.info("Organisation removed from session.");
        model.addAttribute(THEME, REGISTRATION);
        model.addAttribute(MESSAGE, "Registration successful! We have sent an e-mail to your email address to the given e-mail account."
                                        + "\n Please confirm your account using the given link.");
        return INFORMATION;
    }

    @GetMapping( value = "/registration/organisation/step3/{activation_id}")
    public String confirmation(@PathVariable String activation_id, Model model, HttpSession session) {
        log.info("confirmation() method called...");
        User newUser = userService.confirmRegistration(activation_id);
        if (newUser == null){
            log.warn("Activation failed.");
            model.addAttribute(THEME, REGISTRATION);
            model.addAttribute(MESSAGE, "Account confirmation is unsuccessful.\nPlease try again or contact us for more help.");
            return INFORMATION;
        } else{
            log.info("User profile has been activated.");
            //TODO: Log in newUser. Note:It can be also null for various reasons(see ConfirmRegistration())
        }

        model.addAttribute("user", newUser);
        model.addAttribute(THEME, REGISTRATION);
        model.addAttribute(MESSAGE, "Account Confirmation is done.");
        return INFORMATION;
    }
    /* Expected Request body:
    {
        entityName: entityName,
        fieldName: fieldName,
        value: value
    }
    */
    @PostMapping( value = "/registration/ValidateFieldIfExists")
    @ResponseBody
    public String validateFieldIfExists(@RequestBody HashMap<String, String> payload){
        log.info("payload: " + payload.toString());
       return String.valueOf(validationService.checkIfValueExists(payload));
    }
}
