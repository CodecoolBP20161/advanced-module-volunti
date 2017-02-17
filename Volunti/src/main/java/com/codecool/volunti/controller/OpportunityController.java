package com.codecool.volunti.controller;


import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.service.OpportunityService;
import com.codecool.volunti.service.OrganisationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class OpportunityController {

    @Autowired
    private OpportunityService opportunityService;

    @Autowired
    private OrganisationService organisationService;

    @GetMapping("/org/{org_id}/opp/form")
    public String form(@PathVariable Integer org_id, Model model){

        Organisation organisation;
        organisation = organisationService.get(org_id);

        Opportunity opportunity;
        opportunity = new Opportunity(organisation, "First opportunity", 10, "Tent",
                "Vega", 3, "none", 2,
                new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English");
        model.addAttribute("opportunity", opportunity);
        return "multi-form";
    }

    @PostMapping("/org/{org_id}/opp/form")
    public String saveOpportunity(@Valid Opportunity opportunity){
        log.info("opportunity = " + opportunity);
        return "redirect:/org_opportunities";
    }


    // for tests, but route and return value legit for the final product
//    @GetMapping("/org/{id}/opp")
//    public String display(HttpServletRequest req){
//        // equals find all
//        List<Opportunity> list = new ArrayList<>();
//
//        Opportunity x = new Opportunity();
//        Opportunity y = new Opportunity();
//        list.add(x);
//        list.add(y);
//
//        //y.title = "other title";
//        req.setAttribute("opps", list);
//        return "org_opportunities";
//    }
//
//
}
