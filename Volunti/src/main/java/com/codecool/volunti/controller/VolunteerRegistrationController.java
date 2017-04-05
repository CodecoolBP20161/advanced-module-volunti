package com.codecool.volunti.controller;

import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.repository.VolunteerRepository;
import com.codecool.volunti.service.email.EmailService;
import com.codecool.volunti.service.email.EmailType;
import com.codecool.volunti.service.model.UserService;
import com.codecool.volunti.service.model.ValidationService;
import com.codecool.volunti.service.model.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping(value = "/registration/volunteer")
@SessionAttributes({"volunteer", "user"})
public class VolunteerRegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private VolunteerRepository volunteerRepository;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/step1")
    public String stepOne(Model model, HttpSession session) {
        Volunteer volunteer = new Volunteer();
        if ( session.getAttribute("volunteer") != null ) {
            volunteer = (Volunteer) session.getAttribute("volunteer");
        }
        model.addAttribute("volunteer", volunteer);
        return "registration/volunteer/volunteerForm";
    }

    @PostMapping("/step1")
    public String stepOnePost(Volunteer volunteer, HttpSession session) {
        if(session.getAttribute("volunteer") == null){
            return "redirect:/registration/volunteer/step1";
        }
        return "redirect:/registration/volunteer/step2/" + volunteer.getId();
    }

    @GetMapping("/step2/{volunteer_id}")
    public String stepTwo(@PathVariable Integer volunteer_id, Model model, HttpSession session) {
        if (session.getAttribute("volunteer") == null) {
            return "redirect:/registration/volunteer/step1";
        }

        User user = new User();
        if (session.getAttribute("user") != null){
            user = (User) session.getAttribute("user");
        }
        model.addAttribute("user", user);
        model.addAttribute("organisation_id", volunteer_id);
        return "registration/user";
    }

    @PostMapping("/step2")
    public String stepTwoPost(User user,Volunteer volunteer, HttpSession session, Model model) {
        if (session.getAttribute("volunteer") == null) {
            return "redirect:/registration/volunteer/step1";
        }
        volunteer = (Volunteer) session.getAttribute("volunteer");
        volunteerService.save(volunteer);
        user.setVolunteer(volunteer);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);

        user.signupSuccess(emailService, EmailType.CONFIRMATION);

        session.removeAttribute("volunteer");
        session.removeAttribute("user");
        model.addAttribute("theme", "Registration");
        model.addAttribute("message", "Registration successful! We have sent an e-mail to your email address to the given e-mail account."
                + "\n Please confirm your account using the given link.");
        return "information";
    }

    @GetMapping( value = "/step3/{activation_id}")
    public String volunteerConfirmation(@PathVariable String activation_id, Model model, HttpSession session) {
        User newUser = userService.confirmRegistration(activation_id);
        if (newUser == null){
            model.addAttribute("theme", "Registration");
            model.addAttribute("message", "Account confirmation is unsuccessful.\nPlease try again or contact us for more help.");
            return "information";
        } else{
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
    @PostMapping( value = "/ValidateFieldIfExists")
    @ResponseBody
    public String validateFieldIfExists(@RequestBody HashMap<String, String> payload){
        return String.valueOf(validationService.checkIfValueExists(payload));
    }
}
