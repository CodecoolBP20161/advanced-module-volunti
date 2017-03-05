package com.codecool.volunti.controller;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.Skill;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.SkillRepository;
import com.codecool.volunti.service.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequestMapping(value = "/api/opportunities")
public class OpportunityRestController {

    private static Integer pageSize = 20;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @RequestMapping(value = "/find/{currentPage}", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, List<Object>> findOpp(@PathVariable int currentPage,
                                      @RequestParam(value = "from", required = false) Date from,
                                      @RequestParam(value = "to", required = false) Date to,
                                      @RequestParam(value = "skills", required = false) String skill,
                                      @RequestParam(value = "location", required = false) String country,
                                      @RequestParam(value = "category", required = false) String category,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        Map<String, List<Object>> result = new HashMap<>();

        Pageable page = new Pageable(opportunityRepository.find(country, category, new java.sql.Timestamp(from.getTime()), new java.sql.Timestamp(to.getTime()), skill), currentPage, pageSize);
        Integer maxPage = page.getMaxPages();

        result.put("maxpage", Collections.singletonList(maxPage));
        result.put("result", page.getListForPage());
        return result;
    }

    @RequestMapping(value = "/find/all/{currentPage}", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, List<Object>> findAll(@PathVariable int currentPage) {
        Map<String, List<Object>> result = new HashMap<>();
        Pageable page = new Pageable((List) opportunityRepository.findAll(), currentPage, pageSize);
        Integer maxPage = page.getMaxPages();

        result.put("maxpage", Collections.singletonList(maxPage));
        result.put("result", page.getListForPage());
        return result;
    }

    @RequestMapping(value = "/filters", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Set<String>> filters() {
        Map<String, Set<String>> filters = new HashMap<>();
        filters.put("pageSize", new HashSet<>(Arrays.asList(pageSize.toString())));
        filters.put("categories", getCategories());
        filters.put("skills", getSkills());
        filters.put("locations", getLocations());
        return filters;
    }

    //Convert String to Date from RequestParam
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    private Set<String> getSkills() {
        List<Skill> skills = (List<Skill>) skillRepository.findAll();
        return skills.stream().map(Skill::getName).collect(Collectors.toSet());
    }

    private Set<String> getCategories() {
        List<Organisation> categories = (List<Organisation>) organisationRepository.findAll();
        return categories.stream().map(o -> o.getCategory().name()).collect(Collectors.toSet());
    }

    private Set<String> getLocations() {
        List<Organisation> locations = (List<Organisation>) organisationRepository.findAll();
        return locations.stream().map(Organisation::getCountry).collect(Collectors.toSet());
    }
}
