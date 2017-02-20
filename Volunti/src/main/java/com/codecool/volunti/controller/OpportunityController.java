package com.codecool.volunti.controller;


import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequestMapping(value = "/organisation")
public class OpportunityController {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @GetMapping("/{organisation_id}/opportunity/new")
    public String form(@PathVariable Integer organisation_id, Model model, Opportunity opportunity , final BindingResult bindingResult){
        Organisation organisation;
        organisation = organisationRepository.findByOrganisationId(organisation_id);
        model.addAttribute("organisation", organisation);

            if (bindingResult.hasErrors()) {
                return "new-multi-form";
            }
        System.out.println("opportunity = " + opportunity);
        return "new-multi-form";
    }

    @PostMapping("/{organisation_id}/opportunity/new")
    public String saveOpportunity(@PathVariable Integer organisation_id, Model model, Opportunity opportunity , final BindingResult bindingResult){
        log.info("opportunity = " + opportunity);
        Organisation organisation;
        organisation = organisationRepository.findByOrganisationId(organisation_id);
        model.addAttribute("organisation", organisation);
        if (bindingResult.hasErrors()) {
            return "new-multi-form";
        }
        opportunityRepository.save(opportunity);
        return "redirect:/organisation/{organisation_id}/opportunities";
    }


    @GetMapping("/{organisation_id}/opportunities")
    public String display(@PathVariable Integer organisation_id, Model model){

        Organisation organisation;
        organisation = organisationRepository.findOne(organisation_id);
        log.info("Organisation found: " + organisation);

        List<Opportunity> opportunities;
        opportunities = opportunityRepository.findByOrganisation(organisation);
        log.info("Opportunities found: " + opportunities);

        model.addAttribute("opportunities", opportunities);
        model.addAttribute("organisation", organisation);
        return "organisation_opportunities";
    }

    @GetMapping("/{organisation_id}/opportunity/delete/{opportunity_id}")
    public String deleteOpportunity(@PathVariable Integer organisation_id, @PathVariable Integer opportunity_id){
        opportunityRepository.delete(opportunity_id);
        return "redirect:/organisation/{organisation_id}/opportunities";
    }

    @GetMapping("/{organisation_id}/opportunity/edit/{opportunity_id}")
    public String editOpportunity(@PathVariable Integer organisation_id,@PathVariable Integer opportunity_id, Model model) {
        Opportunity opportunity = opportunityRepository.findOne(opportunity_id);
        Organisation organisation = organisationRepository.findByOrganisationId(organisation_id);
        model.addAttribute("opportunity", opportunity);
        model.addAttribute("organisation", organisation);
        log.info("opp: " + opportunity);
        return "multi-form";
    }

    @PostMapping("/{organisation_id}/opportunity/edit/{opportunity_id}")
    public String editOpportunity(@PathVariable Integer organisation_id,@PathVariable Integer opportunity_id, Model model, Opportunity opportunity) {
        Opportunity opportunityOld = opportunityRepository.findOne(opportunity_id);
        Organisation organisation = organisationRepository.findByOrganisationId(organisation_id);
        opportunityOld.setTitle(opportunity.getTitle());
        opportunityOld.setAccommodationType(opportunity.getAccommodationType());
        opportunityOld.setAvailabilityFrom(opportunity.getAvailabilityFrom());
        opportunityOld.setDateAvailabilityTo(opportunity.getDateAvailabilityTo());
        opportunityOld.setAccommodationType(opportunity.getAccommodationType());
        opportunityOld.setCosts(opportunity.getCosts());
        opportunityOld.setHoursExpected(opportunity.getHoursExpected());
        opportunityOld.setMinimumStayInDays(opportunity.getMinimumStayInDays());
        opportunityOld.setNumberOfVolunteers(opportunity.getNumberOfVolunteers());
        opportunityOld.setRequirements(opportunity.getRequirements());
        opportunityRepository.save(opportunityOld);
        System.out.println("opportunity = " + opportunity);
        model.addAttribute("opportunity", opportunity);
        model.addAttribute("organisation", organisation);
        log.info("opp: " + opportunity);
        return "redirect:/organisation/{organisation_id}/opportunities";
    }

}
