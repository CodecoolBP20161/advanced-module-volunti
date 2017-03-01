package com.codecool.volunti.service;


import com.codecool.volunti.model.*;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.OpportunityHoursExpectedType;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.repository.*;
import org.fluttercode.datafactory.impl.DataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
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
        Volunteer volunteer = new Volunteer();
        volunteerRepository.save(volunteer);
        userRepository.save(user1);

        Organisation organisation1 = new Organisation("UNICEF", Category.TEACHING, "Hungary", "1065", "Isaszeg", "Kossuth utca", spokenLanguages, "mission mission mission mission mission", "description1", "description2");
        loadSkills();
        organisationRepository.save(organisation1);

        Opportunity firstOpportunity = new Opportunity();
        firstOpportunity.setOrganisation(organisation1);
        firstOpportunity.setTitle("First opportunity");
        firstOpportunity.setNumberOfVolunteers(10);
        firstOpportunity.setAccommodationType("Tent");
        firstOpportunity.setFoodType("Vega");
        firstOpportunity.setHoursExpected(3);
        firstOpportunity.setHoursExpectedType(null);
        firstOpportunity.setMinimumStayInDays(2);
        firstOpportunity.setAvailabilityFrom(new java.sql.Date(2017 - 02 - 16));
        firstOpportunity.setDateAvailabilityTo(new java.sql.Date(2017 - 02 - 21));
        firstOpportunity.setCosts("free");
        firstOpportunity.setRequirements("English");
        opportunityRepository.save(firstOpportunity);

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
        opportunityRepository.save(opportunity1);

        for (int i = 0; i < 100; i++) {
            saveTestTask(organisation1);
        }

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

    public void saveTestTask(Organisation org){
        DataFactory df = new DataFactory();

        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("Programming"));
        skills.add(new Skill("Cooking"));

        String title = df.getRandomWord() + " " + df.getRandomWord();
        Opportunity opportunity = new Opportunity();
        opportunity.setOrganisation(org);
        opportunity.setTitle(title);
        opportunity.setNumberOfVolunteers(df.getNumberBetween(1,100));
        opportunity.setAccommodationType(df.getRandomWord(5));
        opportunity.setFoodType(df.getRandomWord());
        opportunity.setHoursExpected(df.getNumberBetween(3,12));
        opportunity.setHoursExpectedType(OpportunityHoursExpectedType.Day);
        opportunity.setMinimumStayInDays(df.getNumberBetween(1,99));
        opportunity.setAvailabilityFrom(df.getDateBetween(new Date(113,3,2),new Date(118,3,9)));
        opportunity.setDateAvailabilityTo(df.getDateBetween(new Date(121,3,9),new Date(123,10,4)));
        opportunity.setCosts("free");
        opportunity.setRequirements(df.getRandomWord());
        opportunity.setOpportunitySkills(skills);

        opportunityRepository.save(opportunity);
    }

}
