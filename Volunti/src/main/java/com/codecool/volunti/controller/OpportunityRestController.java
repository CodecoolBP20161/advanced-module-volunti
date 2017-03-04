package com.codecool.volunti.controller;


import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.Skill;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.SkillRepository;
import com.codecool.volunti.service.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

//import com.codecool.volunti.service.FilterService;

@RestController
@Slf4j
@RequestMapping(value = "/api/opportunities")
public class OpportunityRestController {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @RequestMapping(value="/find",
                    method= RequestMethod.GET)
    public   @ResponseBody List<Opportunity> findOpp() {
        log.info("opportunityRepository.findAll()");
        List<Opportunity> allOpportunity = (List<Opportunity>) opportunityRepository.findAll();
        int opportunityListSize = allOpportunity.size();
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();

        List<Opportunity> filterOpportunity = opportunityRepository.find("", "Hungary", "", new java.sql.Timestamp(now.getTime()), new java.sql.Timestamp(now.getTime()));
        Pageable page = new Pageable(filterOpportunity, 1, 5);
//        page.getListForPage();

        log.info("filterO" + filterOpportunity);
        return page.getListForPage();
    }

    @RequestMapping(value="/filters",
            method= RequestMethod.GET)
    public @ResponseBody Map<String, Set<String>> filters() {
        Map<String, Set<String>> filters = new HashMap<>();
        filters.put("elementsPerPage",new HashSet<String>(Arrays.asList("20")));
        filters.put("categories", getCategories());
        filters.put("skills", getSkills());
        filters.put("locations", getLocations());
        return filters;
    }



    private Set<String> getSkills() {
        List<Skill> skills = (List<Skill>) skillRepository.findAll();
        return skills.stream().map(Skill::getName).collect(Collectors.toSet());
    }

    private Set<String> getCategories() {
        List<Organisation> categories = (List<Organisation>) organisationRepository.findAll();
        return categories.stream().map(o -> o.getCategory().name()).collect(Collectors.toSet());
    }

    private Set<String> getLocations(){
        List<Organisation> locations = (List<Organisation>) organisationRepository.findAll();
        return locations.stream().map(Organisation::getCountry).collect(Collectors.toSet());
    }
}
