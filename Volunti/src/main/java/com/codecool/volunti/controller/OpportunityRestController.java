package com.codecool.volunti.controller;


import com.codecool.volunti.exception.ErrorException;
import com.codecool.volunti.exception.OpportunityNotFoundException;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@Slf4j
@RequestMapping(value = "/api/opportunities")
public class OpportunityRestController {

    private static final int pageSize = 20;
    private OpportunityRepository opportunityRepository;
    private VolunteerRepository volunteerRepository;
    private UserService userService;
    private SkillService skillService;
    private VolunteerService volunteerService;
    private OrganisationService organisationService;
    private OpportunityService opportunityService;

    @Autowired
    public OpportunityRestController(OpportunityRepository opportunityRepository, VolunteerRepository volunteerRepository, UserService userService, SkillService skillService, VolunteerService volunteerService, OrganisationService organisationService, OpportunityService opportunityService) {
        this.opportunityRepository = opportunityRepository;
        this.volunteerRepository = volunteerRepository;
        this.userService = userService;
        this.skillService = skillService;
        this.volunteerService = volunteerService;
        this.organisationService = organisationService;
        this.opportunityService = opportunityService;
    }

    @RequestMapping(value = "/find/{currentPage}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String, Object>> findOpp(@PathVariable int currentPage,
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

        if ("".equals(from) || "".equals(to)) {
            if ("".equals(from)) {
                c.setTime(fromDate);
                c.add(Calendar.YEAR, -20);
                fromDate = c.getTime();
            }
            if ("".equals(to)) {
                c.setTime(toDate);
                c.add(Calendar.YEAR, 20);
                toDate = c.getTime();
            }
        }
        else if (!"".equals(from)) fromDate = stringToDate(from);
        else if (!"".equals(to)) toDate = stringToDate(to);

        log.info("api for opportunities response");

        if (Objects.equals(skill, "") && Objects.equals(country, "") && Objects.equals(category, "")) {
            page = new Pageable<>(opportunityService.convertToDto(opportunityRepository.findAll()), currentPage, pageSize);
        } else {
            page = new Pageable<>(opportunityService.convertToDto(opportunityRepository.find(country, category, new java.sql.Timestamp(fromDate.getTime()), new java.sql.Timestamp(toDate.getTime()), skill)), currentPage, pageSize);
        }


        if (page.getList().isEmpty()) {
            throw new OpportunityNotFoundException();
        }

        result.put("result", page.getListForPage());
        result.put("maxpage", page.getMaxPages());
        result.put("totalItems", page.getList().size());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @RequestMapping(value = "/filters", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> filters() {
        Map<String, Object> filters = new HashMap<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        log.info("filter api in action");

        filters.put("pageSize", pageSize);
        filters.put("categories", organisationService.getCategories());
        filters.put("skills", skillService.getSkills());
        filters.put("locations", organisationService.getLocations());

        if ("anonymousUser".equals(auth.getName())) {
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
            log.error("Parse exception");
        }
        return date;
    }
}
