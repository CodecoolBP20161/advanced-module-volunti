package com.codecool.volunti.controller;


import com.codecool.volunti.dto.Filter2OpportunityDTO;
import com.codecool.volunti.exception.ErrorException;
import com.codecool.volunti.exception.OpportunityNotFoundException;
import com.codecool.volunti.model.*;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.SkillRepository;
import com.codecool.volunti.repository.VolunteerRepository;
import com.codecool.volunti.service.Pageable;
import com.codecool.volunti.service.model.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserService userService;

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
        Date fromDate = new Date();
        Date toDate = new Date();
        Calendar c = Calendar.getInstance();

        if (from == "") {
            c.setTime(fromDate);
            c.add(Calendar.YEAR, -20);
            fromDate = c.getTime();
        } else {
            fromDate = stringToDate(from);
        }
        if (to == "") {
            c.setTime(toDate);
            c.add(Calendar.YEAR, 20);
            toDate = c.getTime();
        } else {
            toDate = stringToDate(to);
        }

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
    public Map<String, Object> filters() {
        Map<String, Object> filters = new HashMap<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        filters.put("pageSize", pageSize);
        filters.put("categories", getCategories());
        filters.put("skills", getSkills());
        filters.put("locations", getLocations());

        if (auth.getName().equals("anonymousUser")){
            filters.put("userSkills", "");
            filters.put("userLocation", "");
        }else {
            User user1 = userService.getByEmail(auth.getName());
            Volunteer volunteer = volunteerRepository.findOne(user1.getVolunteer().getId());

            filters.put("userSkills", getUserSkills(volunteer.getId()));
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
            filterDto.setSkills(opportunity.getOpportunitySkills().stream().map(Skill::getName).collect(Collectors.toList()));
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
        }
            catch (ParseException ex) {
        }
        return date;
    }
}
