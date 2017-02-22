package com.codecool.volunti.controller;


import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.Skill;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.SkillRepository;
import com.codecool.volunti.service.OpportunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequestMapping(value = "/organisation")
public class OpportunityController {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private OpportunityService opportunityService;

    @Autowired
    private SkillRepository skillRepository;


    @GetMapping("/{organisation_id}/opportunity/new")
    public String form(@PathVariable Integer organisation_id, Model model, Opportunity opportunity , final BindingResult bindingResult){
        Organisation organisation = organisationRepository.findByOrganisationId(organisation_id);
        String action = "/organisation/" + organisation_id + "/opportunity/new";
        model.addAttribute("action", action);
        model.addAttribute("organisation", organisation);
        model.addAttribute("opportunity", new Opportunity());
        log.info("Opportunity found: " + opportunity);
        if (bindingResult.hasErrors()) {
            return "multi-form";
        }

        return "multi-form";
    }

    @PostMapping("/{organisation_id}/opportunity/new")
    public String saveOpportunity(@PathVariable Integer organisation_id, Model model, Opportunity opportunity , final BindingResult bindingResult){
        log.info("opportunity = " + opportunity);
        Organisation organisation;
        organisation = organisationRepository.findByOrganisationId(organisation_id);

        List<Skill> skill = (List<Skill>) skillRepository.findAll();
        log.info("skill = " + skill);
        model.addAttribute("organisation", organisation);
        model.addAttribute("skills", skill);
        if (bindingResult.hasErrors()) {
            return "multi-form";
        }
        opportunity.setOrganisation(organisation);
        opportunityRepository.save(opportunity);
        return "redirect:/organisation/{organisation_id}/opportunities";
    }


    @GetMapping("/{organisation_id}/opportunities")
    public String displayOpportunityList(@PathVariable Integer organisation_id, Model model){

        Organisation organisation = organisationRepository.findOne(organisation_id);
        log.info("Organisation found: " + organisation);

        List<Opportunity> opportunities = opportunityRepository.findByOrganisation(organisation);
        log.info("Opportunities found: " + opportunities);

        model.addAttribute("opportunities", opportunities);
        model.addAttribute("organisation", organisation);
        return "organisation_opportunities";
    }

    @GetMapping("/{organisation_id}/opportunity/delete/{opportunity_id}")
    public String deleteOpportunity(@PathVariable Integer organisation_id, @PathVariable Integer opportunity_id){
        opportunityRepository.delete(opportunity_id);
        return "redirect:/organisation/{organisation_id}/opportunities";
    }

    @GetMapping("/{organisation_id}/opportunity/edit/{opportunity_id}")
    public String editOpportunity(@PathVariable Integer organisation_id,@PathVariable Integer opportunity_id, Model model ) {
        Opportunity opportunity = opportunityRepository.findOne(opportunity_id);
        Organisation organisation = organisationRepository.findByOrganisationId(organisation_id);
        List<Skill> skill = (List<Skill>) skillRepository.findAll();
        System.out.println("skill = " + skill);
        model.addAttribute("skills", skill);
        model.addAttribute("opportunity", opportunity);
        model.addAttribute("organisation", organisation);
        log.info("opp: " + opportunity);
        return "multi-form";
    }

    @PostMapping("/{organisation_id}/opportunity/edit/{opportunity_id}")
    public String editOpportunity(@PathVariable Integer organisation_id,@PathVariable Integer opportunity_id, Model model, Opportunity opportunity) {

        Opportunity opportunityOld = opportunityRepository.findOne(opportunity_id);
        Organisation organisation = organisationRepository.findByOrganisationId(organisation_id);

        opportunityService.update(opportunity, opportunityOld);
        String action = "/organisation/" + organisation_id + "/opportunity/edit/" + opportunity_id;
        model.addAttribute("action", action);
        System.out.println("opportunity = " + opportunity);
        model.addAttribute("opportunity", opportunity);
        model.addAttribute("organisation", organisation);
        log.info("opp: " + opportunity);
        return "redirect:/organisation/{organisation_id}/opportunities";
    }

}
