package com.codecool.volunti.service;


import com.codecool.volunti.model.Filter2Opportunity;
import com.codecool.volunti.repository.Filter2OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public class Filter2OpportunityService {

    @Autowired
    private Filter2OpportunityRepository filter2OpportunityRepository;

    public List<Filter2Opportunity> filter (String country, String category, Timestamp dateFrom, Timestamp dateTo, String skill){
        return null;
    }

}
