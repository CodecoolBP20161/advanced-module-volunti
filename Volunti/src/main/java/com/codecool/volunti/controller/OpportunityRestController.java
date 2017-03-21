package com.codecool.volunti.controller;


import com.codecool.volunti.dto.Filter2OpportunityDTO;
import com.codecool.volunti.exception.ErrorException;
import com.codecool.volunti.exception.OpportunityNotFoundException;
import com.codecool.volunti.model.Filter2Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.Skill;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.repository.Filter2OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.SkillRepository;
import com.codecool.volunti.repository.VolunteerRepository;
import com.codecool.volunti.service.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private ModelMapper modelMapper;
    @Autowired
    VolunteerRepository volunteerRepository;

    @Autowired
    public OpportunityRestController(SkillRepository skillRepository, OrganisationRepository organisationRepository, Filter2OpportunityRepository filter2OpportunityRepository, ModelMapper modelMapper) {
        this.skillRepository = skillRepository;
        this.organisationRepository = organisationRepository;
        this.filter2OpportunityRepository = filter2OpportunityRepository;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/find/{currentPage}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> findOpp(@PathVariable int currentPage,
                                          @RequestParam(value = "from", required = false) Date from,
                                          @RequestParam(value = "to", required = false) Date to,
                                          @RequestParam(value = "skills", required = false) String skill,
                                          @RequestParam(value = "location", required = false) String country,
                                          @RequestParam(value = "category", required = false) String category,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        Pageable page;
        Map<String, Object> result = new HashMap<>();

        if (Objects.equals(skill, "") && Objects.equals(country, "") && Objects.equals(category, "")) {
            page = new Pageable((List) convertToDto(filter2OpportunityRepository.findAll()), currentPage, pageSize);
        } else {
            page = new Pageable(convertToDto(filter2OpportunityRepository.find(country, category, new java.sql.Timestamp(from.getTime()), new java.sql.Timestamp(to.getTime()), skill)), currentPage, pageSize);
        }

        if (page.getList().size() == 0) {
            throw new OpportunityNotFoundException();
        }

        result.put("result", page.getListForPage());
        result.put("maxpage", page.getMaxPages());

        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }


    @RequestMapping(value = "/filters", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> filters(@RequestParam(value = "volunteer", required = false) String isLoggedIn) {
        Map<String, Object> filters = new HashMap<>();

        // If we'll have login and session we can replace this line e.g. get user info from session, but for now
        // it will be okay I guess!
        Volunteer user = volunteerRepository.findOne(1);

        filters.put("pageSize", pageSize);
        filters.put("categories", getCategories());
        filters.put("skills", getSkills());
        filters.put("locations", getLocations());

        if (isLoggedIn == null){
            filters.put("userSkills", "");
            filters.put("userLocation", "");
        }else {
            filters.put("userSkills", getUserSkills(user.getId()));
            filters.put("userLocation", "Method needs to get users location");
        }
        return filters;
    }

    //Convert String to Date from RequestParam
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    //throwable exception from rest controller
    @ExceptionHandler(OpportunityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorException opportunityNotFound() {
        return new ErrorException(4, "Spittle Opportunity not found");
    }

    // Finds the user's skills
    private Set<String> getUserSkills(int id) {
        List<Skill> skills = volunteerRepository.findOne(id).getVolunteerSkills();
        return skills.stream().map(Skill::getName).collect(Collectors.toSet());
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
        List<Filter2OpportunityDTO> result = new ArrayList<>();
        for (Filter2Opportunity filterOpp : filter2Opportunities) {
            Filter2OpportunityDTO filterDto = modelMapper.map(filterOpp, Filter2OpportunityDTO.class);
            filterDto.setName(filter2OpportunityRepository.findName(filterOpp.getId()));
            result.add(filterDto);
        }
        return result;
    }
}
