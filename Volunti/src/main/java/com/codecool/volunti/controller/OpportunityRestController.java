package com.codecool.volunti.controller;


import com.codecool.volunti.dto.Filter2OpportunityDTO;
import com.codecool.volunti.exception.ErrorException;
import com.codecool.volunti.exception.OpportunityNotFoundException;
import com.codecool.volunti.model.*;
import com.codecool.volunti.repository.*;
import com.codecool.volunti.service.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequestMapping(value = "/api/opportunities")
public class OpportunityRestController {

    private static final int pageSize = 20;
    private SkillRepository skillRepository;
    private OpportunityRepository opportunityRepository;
    private OrganisationRepository organisationRepository;
    private ModelMapper modelMapper;
    @Autowired
    VolunteerRepository volunteerRepository;



    @Autowired
    public OpportunityRestController(SkillRepository skillRepository, OrganisationRepository organisationRepository, ModelMapper modelMapper, OpportunityRepository opportunityRepository) {
        this.skillRepository = skillRepository;
        this.organisationRepository = organisationRepository;
        this.modelMapper = modelMapper;
        this.opportunityRepository = opportunityRepository;
    }

    @RequestMapping(value = "/find/{currentPage}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> findOpp(@PathVariable int currentPage,
                                          @RequestParam(value = "from", required = false) String from,
                                          @RequestParam(value = "to", required = false) String to,
                                          @RequestParam(value = "skills", required = false) String skill,
                                          @RequestParam(value = "location", required = false) String country,
                                          @RequestParam(value = "category", required = false) String category,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        Pageable page;
        Map<String, Object> result = new HashMap<>();

        if (from == "") from = "1999-10-10";
        if (to == "") to = "2030-10-10";
        Date fromDate = stringToDate(from);
        Date toDate = stringToDate(to);


        if (Objects.equals(skill, "") && Objects.equals(country, "") && Objects.equals(category, "")) {
            page = new Pageable((List) convertToDto(opportunityRepository.findAll()), currentPage, pageSize);
        } else {
            page = new Pageable(convertToDto(opportunityRepository.find(country, category, new java.sql.Timestamp(fromDate.getTime()), new java.sql.Timestamp(toDate.getTime()), skill)), currentPage, pageSize);
        }


        if (page.getList().size() == 0) {
            throw new OpportunityNotFoundException();
        }

        result.put("result", page.getListForPage());
        result.put("maxpage", page.getMaxPages());
        result.put("totalItems", page.getList().size());

        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }


    @RequestMapping(value = "/filters", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> filters(@RequestParam(value = "volunteer", required = false) String isLoggedIn) {
        Map<String, Object> filters = new HashMap<>();


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
        return organisationRepository.findAll().stream().map(o -> o.getCategory().name()).collect(Collectors.toSet());
    }

    private Set<String> getLocations() {
        return organisationRepository.findAll().stream().map(Organisation::getCountry).collect(Collectors.toSet());
    }


    private List<Filter2OpportunityDTO> convertToDto(List<Opportunity> opportunities) {
        List<Filter2OpportunityDTO> result = new ArrayList<>();
        for (Opportunity opportunity : opportunities) {
            Filter2OpportunityDTO filterDto = modelMapper.map(opportunity, Filter2OpportunityDTO.class);
            filterDto.setName(opportunity.getOpportunitySkills().stream().map(Skill::getName).collect(Collectors.toList()));
            filterDto.setCategory(opportunity.getOrganisation().getCategory().name());
            filterDto.setCountry(opportunity.getOrganisation().getCountry());
            result.add(filterDto);
        }
        return result;
    }


    private Date stringToDate (String s) {

        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = simpleDateFormat.parse(s);
                System.out.println("date : "+simpleDateFormat.format(date));
        }
            catch (ParseException ex) {
                System.out.println("Exception "+ex);
        }
        return date;
    }
}
