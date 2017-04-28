package com.codecool.volunti.service;

import com.codecool.volunti.dto.Filter2OpportunityDTO;
import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.Skill;
import com.codecool.volunti.repository.OpportunityRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OpportunityService {

    private OpportunityRepository opportunityRepository;
    private ModelMapper modelMapper;

    @Autowired
    public OpportunityService(OpportunityRepository opportunityRepository, ModelMapper modelMapper){

        this.opportunityRepository = opportunityRepository;
        this.modelMapper = modelMapper;
    }

    public void update(Opportunity opportunityNew, Opportunity opportunityOld) {
        log.debug("update opportunity");
        opportunityOld.setTitle(opportunityNew.getTitle());
        opportunityOld.setAccommodationType(opportunityNew.getAccommodationType());
        opportunityOld.setAvailabilityFrom(opportunityNew.getAvailabilityFrom());
        opportunityOld.setDateAvailabilityTo(opportunityNew.getDateAvailabilityTo());
        opportunityOld.setAccommodationType(opportunityNew.getAccommodationType());
        opportunityOld.setCosts(opportunityNew.getCosts());
        opportunityOld.setHoursExpected(opportunityNew.getHoursExpected());
        opportunityOld.setHoursExpectedType(opportunityNew.getHoursExpectedType());
        opportunityOld.setMinimumStayInDays(opportunityNew.getMinimumStayInDays());
        opportunityOld.setNumberOfVolunteers(opportunityNew.getNumberOfVolunteers());
        opportunityOld.setFoodType(opportunityNew.getFoodType());
        opportunityOld.setRequirements(opportunityNew.getRequirements());
        opportunityOld.setOpportunitySkills(opportunityNew.getOpportunitySkills());
        opportunityRepository.save(opportunityOld);
        log.info("opportunity update saved");

    }

    public List<Filter2OpportunityDTO> convertToDto(List<Opportunity> opportunities) {
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
}
