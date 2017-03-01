package com.codecool.volunti.controller;


import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.service.FilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/api/opportunities")
public class OpportunityRestController {

    private OpportunityRepository opportunityRepository;

    @Autowired
    public OpportunityRestController(OpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }

    @RequestMapping(value="/find", method= RequestMethod.GET)
    public   @ResponseBody List<Opportunity> findOpportunities() {
        log.info("opportunityRepository.findAll()");
        System.out.println("opportunityRepository = " + opportunityRepository.findAll());
        return (List<Opportunity>) opportunityRepository.findAll();
    }

    //time, location, skill, category
}
