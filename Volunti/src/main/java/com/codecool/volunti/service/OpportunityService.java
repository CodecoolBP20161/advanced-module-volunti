package com.codecool.volunti.service;


import com.codecool.volunti.model.Opportunity;
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

    public void save(Opportunity opportunity){
        opportunityRepository.save(opportunity);
    }
}
