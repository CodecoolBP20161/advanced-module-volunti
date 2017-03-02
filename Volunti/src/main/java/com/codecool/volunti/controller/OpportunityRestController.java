package com.codecool.volunti.controller;


import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.Skill;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/categ")
    public @ResponseBody EnumSet<Category> findCategories() {
        /**
         *      This method now returns an enumSet, but if you need a string list just change the return type
         *      to list, and the value to enumList!
         */
        log.info("all categories");
        EnumSet<Category> enumSet = EnumSet.allOf(Category.class);
        List<String> enumList = new ArrayList<>();
        for (Category c: enumSet) {
            enumList.add(c.toString());
            System.out.println(c.toString());
        }
        System.out.println(enumList.get(2));
        return enumSet;
    }

    //ContentNegotiatingViewResolver
    //ContentNegotiationManager
    @RequestMapping(value="/find",
                    method= RequestMethod.GET
                    )
    public   @ResponseBody List<Opportunity> findOpportunities() {
        log.info("opportunityRepository.findAll()");
        List<Opportunity> allOpportunity = (List<Opportunity>) opportunityRepository.findAll();
        int size = allOpportunity.size();
        return allOpportunity;
    }

    @GetMapping("/skill")
    public @ResponseBody Map<String, List<String>> findSkills() {
        List<Skill> skills = (List<Skill>) skillRepository.findAll();
        Map<String, List<String>> skillNames = new HashMap<>();
        skillNames.put("skills", skills.stream().map(s -> s.getName()).collect(Collectors.toList()));
        log.info("SkillNames" + String.valueOf(skillNames));
        return skillNames;
    }

    @GetMapping("/category")
    public @ResponseBody Map<String, Set<String>> findCategory() {
        List<Organisation> skills = (List<Organisation>) organisationRepository.findAll();
        Map<String, Set<String>> skillNames = new HashMap<>();
        skillNames.put("categories", skills.stream().map(o -> o.getCategory().name()).collect(Collectors.toSet()));
        log.info("SkillNames" + String.valueOf(skillNames));
        return skillNames;
    }

    @GetMapping("/location")
    public @ResponseBody Map<String, Set<String>> findLocation() {
        List<Organisation> skills = (List<Organisation>) organisationRepository.findAll();
        Map<String, Set<String>> skillNames = new HashMap<>();
        skillNames.put("categories", skills.stream().map(Organisation::getCountry).collect(Collectors.toSet()));
        log.info("SkillNames" + String.valueOf(skillNames));
        return skillNames;
    }

    //time, location, skill, category
}
