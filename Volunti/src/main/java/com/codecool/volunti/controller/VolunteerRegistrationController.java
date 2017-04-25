package com.codecool.volunti.controller;

import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.repository.SkillRepository;
import com.codecool.volunti.service.email.EmailService;
import com.codecool.volunti.service.email.EmailType;
import com.codecool.volunti.service.model.UserService;
import com.codecool.volunti.service.model.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping(value = "/registration/volunteer")
@SessionAttributes({"volunteer", "user"})
public class VolunteerRegistrationController {

    private static final String VOLUNTEER = "volunteer";
    private static final String THEME = "theme";
    private static final String REGISTRATION = "Registration";
    private static final String INFORMATION = "information";
    private static final String MESSAGE = "message";
    private static final String STEP1 = "redirect:/registration/volunteer/step1";


    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private SkillRepository skillRepository;


    @GetMapping("/step1")
    public String stepOne(Model model, HttpSession session) {
        User user = new User();
        if ( session.getAttribute("user") != null ) {
            user = (User) session.getAttribute("user");
        }

        model.addAttribute("user", user);
        model.addAttribute("action","/registration/volunteer/step1");
        model.addAttribute("button", "next");
        return "registration/user";
    }

    @PostMapping("/step1")
    public String stepOnePost(User user, HttpSession session) {
        if(session.getAttribute("user") == null){
            return STEP1;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return "redirect:/registration/volunteer/step2";
    }

    @GetMapping("/step2")
    public String stepTwo(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return STEP1;
        }
        log.info("Volunteer Registration Step 2");

        Volunteer volunteer = new Volunteer();
        if (session.getAttribute(VOLUNTEER) != null){
            volunteer = (Volunteer) session.getAttribute(VOLUNTEER);
            log.info("session11");
        }

        log.info("session22");
        model.addAttribute("skills", skillRepository.findAll());
        log.info("session32");
        model.addAttribute(VOLUNTEER, volunteer);

        return "registration/volunteer/volunteerForm";
    }

    @PostMapping("/step2")
    public String stepTwoPost(@Valid Volunteer volunteer, BindingResult bindingResult, HttpSession session, Model model) {
        if (session.getAttribute(VOLUNTEER) == null) {
            return STEP1;
        }

        log.info("Volunteer Registration Step 2 / SAVE");

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getErrorCount() + " errors occured!" );
            return "registration/volunteer/volunteerForm";
        } else {
            volunteerService.save(volunteer);
//            System.out.println("volunteerSkill2: " + volunteer.getVolunteerSkills().get(0));
            User user = (User) session.getAttribute("user");
            user.setVolunteer(volunteer);
            userService.saveUser(user);
            user.signupSuccess(emailService, EmailType.CONFIRMATION);
            model.addAttribute(THEME, REGISTRATION);
            model.addAttribute(MESSAGE, "Registration successful! We have sent an e-mail to your email address to the given e-mail account."
                    + "\n Please confirm your account using the given link.");
            return INFORMATION;
        }

    }

    @GetMapping( value = "/step3/{activation_id}")
    public String volunteerConfirmation(@PathVariable String activation_id, Model model, HttpSession session) {
        User newUser = userService.confirmRegistration(activation_id);
        if (newUser == null) {
            model.addAttribute(THEME, REGISTRATION);
            model.addAttribute(MESSAGE, "Account confirmation is unsuccessful.\nPlease try again or contact us for more help.");
            return INFORMATION;
        } else {
            //TODO: Log in newUser. Note:It can be also null for various reasons(see ConfirmRegistration())
        }

        model.addAttribute("user", newUser);
        model.addAttribute(THEME, REGISTRATION);
        model.addAttribute(MESSAGE, "Account Confirmation is done.");
        session.removeAttribute(VOLUNTEER);
        session.removeAttribute("user");
        return INFORMATION;
    }

    /* Expected Request body:
    {
        entityName: entityName,
        fieldName: fieldName,
        value: value
    }
    */
//    @PostMapping( value = "/ValidateFieldIfExists")
//    @ResponseBody
//    public String validateFieldIfExists(@RequestBody HashMap<String, String> payload){
//        return String.valueOf(validationService.checkIfValueExists(payload));
//    }
}