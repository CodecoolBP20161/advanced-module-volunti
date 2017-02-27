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

        Opportunity opportunity = new Opportunity();
        opportunity.setOrganisation(organisation1);
        opportunity.setTitle("First opportunity");
        opportunity.setNumberOfVolunteers(10);
        opportunity.setAccommodationType("Tent");
        opportunity.setFoodType("Vega");
        opportunity.setHoursExpected(3);
        opportunity.setHoursExpectedType(null);
        opportunity.setMinimumStayInDays(2);
        opportunity.setAvailabilityFrom(new java.sql.Date(2017 - 02 - 16));
        opportunity.setDateAvailabilityTo(new java.sql.Date(2017 - 02 - 21));
        opportunity.setCosts("free");
        opportunity.setRequirements("English");


        Opportunity opportunity1 = new Opportunity();
        opportunity1.setOrganisation(organisation1);
        opportunity1.setTitle("Second opportunity1");
        opportunity1.setNumberOfVolunteers(10);
        opportunity1.setAccommodationType("Tent");
        opportunity1.setFoodType("Vega");
        opportunity1.setHoursExpected(3);
        opportunity1.setHoursExpectedType(null);
        opportunity1.setMinimumStayInDays(2);
        opportunity1.setAvailabilityFrom(new java.sql.Date(2017 - 02 - 16));
        opportunity1.setDateAvailabilityTo(new java.sql.Date(2017 - 02 - 21));
        opportunity1.setCosts("free");
        opportunity1.setRequirements("English");

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

