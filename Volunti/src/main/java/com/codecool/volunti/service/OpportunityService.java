package com.codecool.volunti.service;

import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.repository.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpportunityService {

    private OpportunityRepository opportunityRepository;

    @Autowired
    public OpportunityService(OpportunityRepository opportunityRepository){

        this.opportunityRepository = opportunityRepository;
    }

    public void update(Opportunity opportunityNew, Opportunity opportunityOld) {
        opportunityOld.setTitle(opportunityNew.getTitle());
        opportunityOld.setAccommodationType(opportunityNew.getAccommodationType());
        opportunityOld.setAvailabilityFrom(opportunityNew.getAvailabilityFrom());
        opportunityOld.setDateAvailabilityTo(opportunityNew.getDateAvailabilityTo());
        opportunityOld.setAccommodationType(opportunityNew.getAccommodationType());
        opportunityOld.setCosts(opportunityNew.getCosts());
        opportunityOld.setHoursExpected(opportunityNew.getHoursExpected());
        opportunityOld.setMinimumStayInDays(opportunityNew.getMinimumStayInDays());
        opportunityOld.setNumberOfVolunteers(opportunityNew.getNumberOfVolunteers());
        opportunityOld.setFoodType(opportunityNew.getFoodType());
        opportunityOld.setRequirements(opportunityNew.getRequirements());
        opportunityRepository.save(opportunityOld);

    }
}
