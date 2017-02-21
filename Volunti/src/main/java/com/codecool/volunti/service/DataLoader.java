package com.codecool.volunti.service;


import com.codecool.volunti.model.*;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Service
public class DataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    private OrganisationRepository organisationRepository;
    private UserRepository userRepository;
    private VolunteerRepository volunteerRepository;
    private OpportunityRepository opportunityRepository;
    private SkillRepository skillRepository;

    @Autowired
    public DataLoader(OrganisationRepository organisationRepository, UserRepository userRepository, VolunteerRepository volunteerRepository,
                      OpportunityRepository opportunityRepository, SkillRepository skillRepository) {
        this.organisationRepository = organisationRepository;
        this.userRepository = userRepository;
        this.volunteerRepository = volunteerRepository;
        this.opportunityRepository = opportunityRepository;
        this.skillRepository = skillRepository;
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
        loadSkills();
        LOGGER.info("loadData method called ...");
    }

    private void loadSkills() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("General Maintenance"));
        skills.add(new Skill("Language practice"));
        skills.add(new Skill("Teaching"));
        skills.add(new Skill("Yoga"));
        skills.add(new Skill("Cooking"));
        skills.add(new Skill("Nursing"));
        skills.add(new Skill("Communication"));
        skills.add(new Skill("Programming"));
        skills.add(new Skill("Babysitting"));
        skills.add(new Skill("Farming"));
        skills.add(new Skill("Building"));
        skills.add(new Skill("Gardening"));

        skillRepository.save(skills);
    }
}

