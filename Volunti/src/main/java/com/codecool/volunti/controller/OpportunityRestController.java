package com.codecool.volunti.controller;


import com.codecool.volunti.dto.Filter2OpportunityDTO;
import com.codecool.volunti.exception.ErrorException;
import com.codecool.volunti.exception.OpportunityNotFoundException;
import com.codecool.volunti.model.*;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.SkillRepository;
import com.codecool.volunti.repository.VolunteerRepository;
import com.codecool.volunti.service.OpportunityService;
import com.codecool.volunti.service.Pageable;
import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.SkillService;
import com.codecool.volunti.service.model.UserService;
import com.codecool.volunti.service.model.VolunteerService;
import com.google.common.collect.Iterables;
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

    @Autowired
    VolunteerRepository volunteerRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private SkillService skillService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private OpportunityService opportunityService;



    @Autowired
    public OpportunityRestController(SkillRepository skillRepository, OrganisationRepository organisationRepository, OpportunityRepository opportunityRepository) {
        this.skillRepository = skillRepository;
        this.organisationRepository = organisationRepository;
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

        if (from.equals("") || to.equals("")) {
            if (from.equals("")) {
                c.setTime(fromDate);
                c.add(Calendar.YEAR, -20);
                fromDate = c.getTime();
            }
            if (to.equals("")) {
                c.setTime(toDate);
                c.add(Calendar.YEAR, 20);
                toDate = c.getTime();
            }
        }
        else if (!from.equals("")) fromDate = stringToDate(from);
        else if (!to.equals("")) toDate = stringToDate(to);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.equals(skill, "") && Objects.equals(country, "") && Objects.equals(category, "")) {
            page = new Pageable(opportunityService.convertToDto(opportunityRepository.findAll()), currentPage, pageSize);
        } else {
            page = new Pageable(opportunityService.convertToDto(opportunityRepository.find(country, category, new java.sql.Timestamp(fromDate.getTime()), new java.sql.Timestamp(toDate.getTime()), skill)), currentPage, pageSize);
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
        filters.put("categories", organisationService.getCategories());
        filters.put("skills", skillService.getSkills());
        filters.put("locations", organisationService.getLocations());

        if (auth.getName().equals("anonymousUser")) {
            filters.put("userSkills", "");
            filters.put("userLocation", "");
        } else {
            User user = userService.getByEmail(auth.getName());
            Volunteer volunteer = volunteerRepository.findOne(user.getVolunteer().getId());

            filters.put("userSkills", Iterables.get(volunteerService.getUserSkills(volunteer.getId()), 0));
            filters.put("userLocation", volunteer.getCountry());
        }
        return filters;
    }


    //throwable exception from rest controller
    @ExceptionHandler(OpportunityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorException opportunityNotFound() {
        return new ErrorException(4, "Spittle Opportunity not found");
    }





    private Date stringToDate(String s) {

        Date date = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException ex) {
        }
        return date;
    }
}
