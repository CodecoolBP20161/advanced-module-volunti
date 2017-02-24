package com.codecool.volunti.service;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.SpokenLanguage;
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
    private  VolunteerRepository volunteerRepository;
    private Organisation organisation;
    private Volunteer volunteer;

    @Autowired
    public DataLoader(OrganisationRepository organisationRepository, UserRepository userRepository, VolunteerRepository volunteerRepository) {
        this.organisationRepository = organisationRepository;
        this.userRepository = userRepository;
        this.volunteerRepository = volunteerRepository;
    }

    @PostConstruct
    public void loadData() {
        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        spokenLanguages.add(SpokenLanguage.ENGLISH);
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);

        Organisation organisation1 = new Organisation("UNICEF", Category.TEACHING, Country.Hungary, "1065", "Isaszeg", "Kossuth utca", spokenLanguages, "mission mission mission mission mission", "description1", "description2");
        Volunteer volunteer = new Volunteer();



        User user1 = new User("Anna", "Kiss", "asd@gmail.com", "password", organisation1, volunteer);

        organisationRepository.save(organisation1);
        volunteerRepository.save(volunteer);
        userRepository.save(user1);

        LOGGER.info("loadData method called ...");
    }
}

