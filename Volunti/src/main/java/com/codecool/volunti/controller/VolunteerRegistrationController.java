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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
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
            return "redirect:/registration/volunteer/step1";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return "redirect:/registration/volunteer/step2";
    }

    @GetMapping("/step2")
    public String stepTwo(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/registration/volunteer/step1";
        }

        Volunteer volunteer = new Volunteer();
        if (session.getAttribute("volunteer") != null){
            volunteer = (Volunteer) session.getAttribute("volunteer");
        }
        User user = (User) session.getAttribute("user");

        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("volunteer", volunteer);
//        model.addAttribute("volunteer_id", volunteer.getId());

        return "registration/volunteer/volunteerForm";
    }

    @PostMapping("/step2")
    public String stepTwoPost(User user,Volunteer volunteer, HttpSession session, Model model) {
        if (session.getAttribute("volunteer") == null) {
            return "redirect:/registration/volunteer/step1";
        }

        log.info("USER " + user.getEmail() + " " + user.getFirstName());
        volunteer = (Volunteer) session.getAttribute("volunteer");
        //http://stackoverflow.com/questions/4024544/how-to-parse-dates-in-multiple-formats-using-simpledateformat

        // TODO: catch exceptions, email sender exception is not caught??
        volunteerService.save(volunteer);
        user.setVolunteer(volunteer);
        userService.saveUser(user);
        user.signupSuccess(emailService, EmailType.CONFIRMATION);

        model.addAttribute("theme", "Registration");
        model.addAttribute("message", "Registration successful! We have sent an e-mail to your email address to the given e-mail account."
                + "\n Please confirm your account using the given link.");
        return "information";
    }

    @GetMapping( value = "/step3/{activation_id}")
    public String volunteerConfirmation(@PathVariable String activation_id, Model model, HttpSession session) {
        User newUser = userService.confirmRegistration(activation_id);
        if (newUser == null) {
            model.addAttribute("theme", "Registration");
            model.addAttribute("message", "Account confirmation is unsuccessful.\nPlease try again or contact us for more help.");
            return "information";
        } else {
            //TODO: Log in newUser. Note:It can be also null for various reasons(see ConfirmRegistration())
        }

        model.addAttribute("user", newUser);
        model.addAttribute("theme", "Registration");
        model.addAttribute("message", "Account Confirmation is done.");
        session.removeAttribute("volunteer");
        session.removeAttribute("user");
        return "information";
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