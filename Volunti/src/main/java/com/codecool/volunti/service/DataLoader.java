package com.codecool.volunti.service;


import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.repository.VolunteerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;


@Service
public class DataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    private OrganisationRepository organisationRepository;
    private UserRepository userRepository;
    private VolunteerRepository volunteerRepository;
    private OpportunityRepository opportunityRepository;

    @Autowired
    public DataLoader(OrganisationRepository organisationRepository, UserRepository userRepository, VolunteerRepository volunteerRepository, OpportunityRepository opportunityRepository) {
        this.organisationRepository = organisationRepository;
        this.userRepository = userRepository;
        this.volunteerRepository = volunteerRepository;
        this.opportunityRepository = opportunityRepository;
    }

    @PostConstruct
    public void loadData() {
        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        spokenLanguages.add(SpokenLanguage.ENGLISH);
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);

        User user1 = new User("Anna", "Kiss", "asd@gmail.com", "asdasd", "asd");
        Organisation organisation1 = new Organisation("UNICEF", Category.TEACHING, "Hungary", "1065", "Isaszeg", "Kossuth utca", spokenLanguages, "mission mission mission mission mission", "description1", "description2");
        Volunteer volunteer = new Volunteer();
        Opportunity opportunity = new Opportunity(organisation1, "First opportunity", 10, "Tent",
                "Vega", 3, "none", 2,
                new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English");

        Opportunity opportunity1 = new Opportunity(organisation1, "Second opportunity", 10, "Tent",
                "Vega", 3, "none", 2,
                new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English");

        userRepository.save(user1);
        organisationRepository.save(organisation1);
        volunteerRepository.save(volunteer);
        opportunityRepository.save(opportunity);
        opportunityRepository.save(opportunity1);
        LOGGER.info("loadData method called ...");
    }
}

