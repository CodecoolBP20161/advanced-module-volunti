package com.codecool.volunti.service;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.Role;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.model.*;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.OpportunityHoursExpectedType;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.model.enums.UserStatus;
import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.RoleService;
import com.codecool.volunti.service.model.UserService;
import com.codecool.volunti.service.model.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import com.codecool.volunti.repository.*;
import org.fluttercode.datafactory.impl.DataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.util.List;

import static com.codecool.volunti.model.enums.RoleEnum.ROLE_USER;

@Slf4j
@Service
@Transactional
public class DataLoader {

    private OrganisationService organisationService;
    private UserService userService;
    private VolunteerService volunteerService;
    private RoleService roleService;
    private BCryptPasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    private OrganisationRepository organisationRepository;
    private UserRepository userRepository;
    private VolunteerRepository volunteerRepository;
    private OpportunityRepository opportunityRepository;
    private SkillRepository skillRepository;

    @Autowired
    public DataLoader(OrganisationService organisationService, UserService userService, VolunteerService volunteerService, RoleService roleService, BCryptPasswordEncoder passwordEncoder , OrganisationRepository organisationRepository, UserRepository userRepository, VolunteerRepository volunteerRepository,
                      OpportunityRepository opportunityRepository, SkillRepository skillRepository) {
        this.organisationService = organisationService;
        this.userService = userService;
        this.volunteerService = volunteerService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
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


        if (organisationRepository.count() == 0) {
            Organisation organisation1 = new Organisation("UNICEF", Category.TEACHING, "Hungary", "1065", "Isaszeg", "Kossuth utca", spokenLanguages, "mission mission mission mission mission", "description1", "description2");

            Organisation organisation2 = new Organisation("WHATEVER", Category.AGRICULTURE, "Iceland", "1065", "Reykjavik", "Whale str", spokenLanguages, "mission mission mission mission mission", "description1", "description2");

            Organisation organisation3 = new Organisation("Doctors Without Borders", Category.MANAGEMENT, "Kenya", "1065", "Random city in kenya", "whatever str", spokenLanguages, "mission mission mission mission mission", "description1", "description2");

            Organisation organisation4 = new Organisation("Feeding America", Category.OTHER, "USA", "1065", "New York", "Amsterdam Av. 106", spokenLanguages, "mission mission mission mission mission", "description1", "description2");

            Volunteer volunteer = new Volunteer();
            volunteer.setCountry("Hungary");
            volunteerRepository.save(volunteer);

            if (skillRepository.count() == 0) {
                loadSkills();
            }
            organisationRepository.save(organisation1);
            organisationRepository.save(organisation2);
            organisationRepository.save(organisation3);
            organisationRepository.save(organisation4);
            User user1 = new User("Lajos", "Lakatos", "b@g.com", "1234", organisation1, volunteer);
            userRepository.save(user1);

            for (int i = 0; i < 50; i++) {
                testOpportunityGenerator(organisation1);
                testOpportunityGenerator(organisation2);
                testOpportunityGenerator(organisation3);
                testOpportunityGenerator(organisation4);
            }
        }


        LOGGER.info("loadData method called ...");
        Organisation organisation1 = new Organisation("UNICEF", Category.TEACHING, "HUNGARY", "1065", "Isaszeg", "Kossuth utca", spokenLanguages, "mission mission mission mission mission", "description1", "description2");
        Volunteer volunteer = new Volunteer();


        if (roleService.findByName(ROLE_USER.getRole()) == null) {
            organisationService.save(organisation1);
            volunteerService.save(volunteer);
            Role roleUser = new Role(ROLE_USER.getRole());
            roleService.save(roleUser);

            User user1 = new User("Anna", "Kiss", "em@i.l", passwordEncoder.encode("password"), organisation1, volunteer);
            User user2 = new User("Volunti", "Volunti", "volunti.trial@gmail.com", passwordEncoder.encode("password"), organisation1, volunteer);
            user2.setUserStatus(UserStatus.ACTIVE);

            Set<Role> roleSet = new HashSet<>();
            roleSet.add(roleUser);
            user1.setRoles(roleSet);
            user2.setRoles(roleSet);
            userService.saveUser(user1);
            userService.saveUser(user2);
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

    public void testOpportunityGenerator(Organisation org){
        DataFactory df = new DataFactory();

        List<Skill> skills = new ArrayList<>();

        List<Skill> possibleSkills = (List) skillRepository.findAll();
        for (int i=0; i < 2; i++) {
            int randomSkill = (int) (Math.random() * possibleSkills.size());
            skills.add(possibleSkills.get(randomSkill));
        }
        if (skills.get(0) == skills.get(1)){
            skills.remove(1);
        }

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
