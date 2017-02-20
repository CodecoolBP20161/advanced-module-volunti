package com.codecool.volunti.controller;


import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/{organisation_id}/opp/form")
    public String form(@PathVariable Integer organisation_id, Model model){

        Organisation organisation;
        organisation = organisationRepository.findByOrganisationId(organisation_id);

        Opportunity opportunity;
       opportunity = new Opportunity(organisation, "First opportunity", 10, "Tent",
              "Vega", 3, "none", 2,
              new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English");
        model.addAttribute("opportunity", opportunity);
        return "multi-form";
    }

    @PostMapping("/{organisation_id}/opportunity/new")
    public String saveOpportunity(@ModelAttribute @Valid Opportunity opportunity){
        //log.info("opportunity = " + opportunity);
        opportunityRepository.save(opportunity);
        return "redirect:organisation_opportunities.html";
    }


    @GetMapping("/{organisation_id}/opportunities")
    public String display(@PathVariable Integer organisation_id, Model model){

        Organisation organisation;
        organisation = organisationRepository.findByOrganisationId(organisation_id);

        List<Opportunity> opportunities;
        opportunities = opportunityRepository.findByOrganisation(organisation);

        Opportunity opportunity;
        opportunity = new Opportunity(organisation, "First opportunity", 10, "Tent",
                "Vega", 3, "none", 2,
                new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English");
        opportunityRepository.save(opportunity);
        model.addAttribute("opportunities", opportunities);
        model.addAttribute("organisation", organisation);
        return "organisation_opportunities";
    }

    @GetMapping("/{organisation_id}/opportunity/delete/{opportunity_id}")
    public String deleteOpportunity(@PathVariable Integer organisation_id, Integer opportunity_id){
        opportunityRepository.delete(opportunity_id);
        return "redirect:organisation_opportunities.html";
    }

    @PostMapping("/{organisation_id}/opportunity/edit/{opportunity_id}")
    public String editOpportunity(@PathVariable Integer organisation_id, Integer opportunity_id) {
        opportunityRepository.findById(opportunity_id);
        return "redirect:organisation_opportunities.html";
    }

}
