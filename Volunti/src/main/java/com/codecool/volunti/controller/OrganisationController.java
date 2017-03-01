package com.codecool.volunti.controller;

import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.repository.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
//@RequestMapping(value = "/opportunities")
public class OrganisationController {

    @Autowired
    OpportunityRepository opportunityRepository;

    @GetMapping("/opportunities")
    public String getAllOpps() {
        return "opportunity/all-opportunities";
    }

    @GetMapping("/opportunities/{oppId}")
    public String singleOppView(@PathVariable Integer oppId, Model model) {
        Opportunity selectedOpportunity = opportunityRepository.findOne(oppId);

        model.addAttribute("foundOppObject",selectedOpportunity);

        return "opportunity/single_opportunity_view";
    }
}
