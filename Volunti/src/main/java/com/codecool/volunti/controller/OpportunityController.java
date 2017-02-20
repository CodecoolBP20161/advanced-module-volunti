package com.codecool.volunti.controller;


import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.service.OpportunityService;
import com.codecool.volunti.service.OrganisationService;
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
@RequestMapping(value = "/org")
public class OpportunityController {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @GetMapping("/{org_id}/opp/form")
    public String form(@PathVariable Integer org_id, Model model){

        Organisation organisation;
        organisation = organisationRepository.findByOrganisationId(org_id);

        Opportunity opportunity;
       opportunity = new Opportunity(organisation, "First opportunity", 10, "Tent",
              "Vega", 3, "none", 2,
              new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English");
        model.addAttribute("opportunity", opportunity);
        return "multi-form";
    }

    @PostMapping("/{org_id}/opp/new")
    public String saveOpportunity(@ModelAttribute @Valid Opportunity opportunity){
        log.info("opportunity = " + opportunity);
        opportunityRepository.save(opportunity);
        return "redirect:org_opportunities.html";
    }


    @GetMapping("/{org_id}/opportunities")
    public String display(@PathVariable Integer org_id, Model model){

        Organisation organisation;
        organisation = organisationRepository.findByOrganisationId(org_id);

        List<Opportunity> opportunities;
        opportunities = opportunityRepository.findByOrganisation(organisation);

//        Opportunity opportunity;
//        opportunity = new Opportunity(organisation, "First opportunity", 10, "Tent",
//                "Vega", 3, "none", 2,
//                new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English");
//        opportunityRepository.save(opportunity);
        model.addAttribute("opportunities", opportunities);
        model.addAttribute("organisation", organisation);
        return "org_opportunities";
    }

    @GetMapping("/{org_id}/opp/delete/{opp_id}")
    public String deleteOpportunity(@PathVariable Integer org_id, Integer opp_id){
        opportunityRepository.delete(opp_id);
        return "redirect:org_opportunities.html";
    }

}
