package com.codecool.volunti.controller;


import com.codecool.volunti.DTO.Filter2OpportunityDTO;
import com.codecool.volunti.model.Filter2Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.Skill;
import com.codecool.volunti.repository.Filter2OpportunityRepository;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.SkillRepository;
import com.codecool.volunti.service.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    private static final int pageSize = 20;
    private SkillRepository skillRepository;
    private OrganisationRepository organisationRepository;
    private Filter2OpportunityRepository filter2OpportunityRepository;

    @Autowired
    public OpportunityRestController(SkillRepository skillRepository, OrganisationRepository organisationRepository, Filter2OpportunityRepository filter2OpportunityRepository) {
        this.skillRepository = skillRepository;
        this.organisationRepository = organisationRepository;
        this.filter2OpportunityRepository = filter2OpportunityRepository;
    }

    @RequestMapping(value = "/find/{currentPage}", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> findOpp(@PathVariable int currentPage,
                                  @RequestParam(value = "from", required = false) Date from,
                                  @RequestParam(value = "to", required = false) Date to,
                                  @RequestParam(value = "skills", required = false) String skill,
                                  @RequestParam(value = "location", required = false) String country,
                                  @RequestParam(value = "category", required = false) String category,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        Pageable page;
        Map<String, Object> result = new HashMap<>();

        if (Objects.equals(skill, "") && Objects.equals(country, "") && Objects.equals(category, "")){
            page = new Pageable((List) convertToDto(filter2OpportunityRepository.findAll()), currentPage, pageSize);
        } else {
            page = new Pageable(convertToDto(filter2OpportunityRepository.find(country, category, new java.sql.Timestamp(from.getTime()), new java.sql.Timestamp(to.getTime()), skill)), currentPage, pageSize);
        }

        result.put("maxpage", page.getMaxPages());
        result.put("result", page.getListForPage());
        return result;
    }

    @RequestMapping(value = "/filters", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> filters() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("pageSize", pageSize);
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

    private List<Filter2OpportunityDTO> convertToDto(List<Filter2Opportunity> filter2Opportunities) {
        ModelMapper modelMapper = new ModelMapper();
        List<Filter2OpportunityDTO> result = new ArrayList<>();
        for (int i = 0; i < filter2Opportunities.size(); i++) {
            Filter2Opportunity filter2Opportunity =  filter2Opportunities.get(i);
            Filter2OpportunityDTO filterDto = modelMapper.map(filter2Opportunity, Filter2OpportunityDTO.class);

            List<String> skillNames = filter2OpportunityRepository.findName(filter2Opportunity.getId());
            filterDto.setName(skillNames);
            result.add(filterDto);
        }
        return result;
    }
}
