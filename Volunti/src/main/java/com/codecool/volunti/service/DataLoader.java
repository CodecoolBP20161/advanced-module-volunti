package com.codecool.volunti.service;


import com.codecool.volunti.VoluntiApplication;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.repository.VolunteerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;




@Service
public class DataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    private OrganisationRepository organisationRepository;
    private UserRepository userRepository;
    private  VolunteerRepository volunteerRepository;

    @Autowired
    public DataLoader(OrganisationRepository organisationRepository, UserRepository userRepository, VolunteerRepository volunteerRepository) {
        this.organisationRepository = organisationRepository;
        this.userRepository = userRepository;
        this.volunteerRepository = volunteerRepository;
    }

    @PostConstruct
    public void loadData() {

        User user1 = new User("Anna", "Kiss", "asd@gmail.com", "asdasd", "asd");
        Organisation organisation1 = new Organisation("UNICEF", Category.TEACHING, "Hungary", "Isaszeg", "Kossuth utca", SpokenLanguage.ENGLISH, "asdasdasdasdasdasdasd", "asdasd", "asdasd");
        Volunteer volunteer = new Volunteer();

        userRepository.save(user1);
        organisationRepository.save(organisation1);
        volunteerRepository.save(volunteer);
        LOGGER.info("loadData method called ...");
    }
}

