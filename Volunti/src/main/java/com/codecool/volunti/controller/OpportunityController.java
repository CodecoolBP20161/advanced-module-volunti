package com.codecool.volunti.controller;

import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.SkillRepository;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.service.OpportunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequestMapping(value = "")
public class OpportunityController {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private OpportunityService opportunityService;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/profile/organisation/opportunities")
    public String displayOpportunityList(Model model, Principal principal){

        Organisation organisation = userRepository.findByEmail(principal.getName()).getOrganisation();

        log.info("Organisation found: " + organisation);

        List<Opportunity> opportunities = opportunityRepository.findByOrganisationOrderByIdAsc(organisation);
        log.info("Opportunities found: " + opportunities);

        model.addAttribute("opportunities", opportunities);
        model.addAttribute("organisation", organisation);
        return "opportunity/list";
    }

    @GetMapping("/profile/organisation/opportunity/delete/{opportunity_id}")
    public String deleteOpportunity(@PathVariable Integer opportunity_id){
        opportunityRepository.delete(opportunity_id);
        return "redirect:/profile/organisation/opportunities";
    }

    @GetMapping("/profile/organisation/opportunity/{opportunity_id}")
    public String editOpportunity(@PathVariable Integer opportunity_id, Model model, Principal principal ) {

        Organisation organisation = userRepository.findByEmail(principal.getName()).getOrganisation();
        String action;
        if (opportunity_id != 0) {
            Opportunity opportunity = opportunityRepository.findOne(opportunity_id);
            model.addAttribute("opportunity", opportunity);
            action = "/profile/organisation/opportunity" + opportunity_id;
            log.info("opp: " + opportunity);
        } else {
            model.addAttribute("opportunity", new Opportunity());
            action = "/profile/organisation/opportunity/0";
        }

        model.addAttribute("action", action);
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("organisation", organisation);

        return "opportunity/multi-form";
    }


    @PostMapping("/profile/organisation/opportunity/{opportunity_id}")
    public String editSaveOpportunity(@PathVariable Integer opportunity_id, Model model, Opportunity opportunity, final BindingResult bindingResult, Principal principal) {

        Organisation organisation = userRepository.findByEmail(principal.getName()).getOrganisation();

        if (opportunity_id != 0) {
        opportunityService.update(opportunity, opportunityRepository.findOne(opportunity_id));
        } else {
            opportunity.setOrganisation(organisation);
            opportunityRepository.save(opportunity);
        }
        log.info("opp: " + opportunity);

        model.addAttribute("opportunity", opportunity);
        model.addAttribute("organisation", organisation);

        if (bindingResult.hasErrors()) {
            return "opportunity/multi-form";
        }

        return "redirect:/profile/organisation/opportunities";
    }

}
