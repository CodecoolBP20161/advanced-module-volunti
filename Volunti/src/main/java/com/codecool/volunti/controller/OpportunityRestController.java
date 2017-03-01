package com.codecool.volunti.controller;


import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Skill;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

//import com.codecool.volunti.service.FilterService;

@RestController
@Slf4j
@RequestMapping(value = "/api/opportunities")
public class OpportunityRestController {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private SkillRepository skillRepository;

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

    @GetMapping("/find")
    public   @ResponseBody List<Opportunity> findOpportunities() {
        log.info("opportunityRepository.findAll()");
        return (List<Opportunity>) opportunityRepository.findAll();
    }

    @GetMapping("/skill")
    public @ResponseBody List<Skill> findSkills() {
        log.info("opportunityRepository.findAll()");
        return (List<Skill>) skillRepository.findAll();
    }

    //time, location, skill, category
}
