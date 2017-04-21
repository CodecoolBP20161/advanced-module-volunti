package com.codecool.volunti.service;


import com.codecool.volunti.model.*;
import com.codecool.volunti.model.enums.*;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.SkillRepository;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.service.model.RoleService;
import com.codecool.volunti.service.model.UserService;
import com.codecool.volunti.service.model.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

import static com.codecool.volunti.model.enums.RoleEnum.ROLE_USER;

@Slf4j
@Service
@Transactional
public class DataLoader {

    @Autowired
    private UserService userService;
    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OpportunityRepository opportunityRepository;
    @Autowired
    private SkillRepository skillRepository;


    @PostConstruct
    public void loadData() {

        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        List<Skill> volunteerSkills = new ArrayList<>();
        Set<Role> roleSet = new HashSet<>();

        spokenLanguages.add(SpokenLanguage.ENGLISH);
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);

        Organisation organisation1 = new Organisation("UNICEF", Category.TEACHING, Country.HUNGARY, "1065", "Isaszeg", "Kossuth utca", spokenLanguages, "mission mission mission mission mission", "description1", "description2");
        Organisation organisation2 = new Organisation("WHATEVER", Category.AGRICULTURE, Country.ICELAND, "1065", "Reykjavik", "Whale str", spokenLanguages, "mission mission mission mission mission", "description1", "description2");
        Organisation organisation3 = new Organisation("Doctors Without Borders", Category.MANAGEMENT, Country.KENYA, "1065", "Random city in kenya", "whatever str", spokenLanguages, "mission mission mission mission mission", "description1", "description2");
        Organisation organisation4 = new Organisation("Feeding America", Category.OTHER, Country.UNITED_STATES, "1065", "New York", "Amsterdam Av. 106", spokenLanguages, "mission mission mission mission mission", "description1", "description2");

        loadSkills();

        organisationRepository.save(organisation1);
        organisationRepository.save(organisation2);
        organisationRepository.save(organisation3);
        organisationRepository.save(organisation4);

        for (int i = 0; i < 50; i++) {
            testOpportunityGenerator(organisation1);
            testOpportunityGenerator(organisation2);
            testOpportunityGenerator(organisation3);
            testOpportunityGenerator(organisation4);
        }

        Volunteer volunteer = new Volunteer();
        volunteer.setCountry("HUNGARY");
        volunteer.setMotto("my mottos");
        volunteer.setInterest("my interest");
        volunteer.setSpokenLanguages(spokenLanguages);
        volunteerSkills.add(skillRepository.findOne(3));
        volunteer.setVolunteerSkills(volunteerSkills);
        volunteerService.save(volunteer);

        Volunteer icelandicVolunteer = new Volunteer();
        icelandicVolunteer.setCountry("ICELAND");
        icelandicVolunteer.setMotto("my motto");
        icelandicVolunteer.setInterest("my interest");
        icelandicVolunteer.setSpokenLanguages(spokenLanguages);
        volunteerSkills.add(skillRepository.findOne(2));
        icelandicVolunteer.setVolunteerSkills(volunteerSkills);

        volunteerService.save(icelandicVolunteer);

        User userLajos = new User("Lajos", "Lakatos", "b@g.com", "1234", organisation1, volunteer);
        User userAnna = new User("Anna", "Kiss", "em@i.l", passwordEncoder.encode("password"), organisation1, volunteer);
        User userVolunti = new User("Volunti", "Volunti", "volunti.trial@gmail.com", passwordEncoder.encode("password"), organisation1, volunteer);
        userVolunti.setUserStatus(UserStatus.ACTIVE);

        Role roleUser = new Role(ROLE_USER.getRole());
        roleService.save(roleUser);

        roleSet.add(roleUser);
        userAnna.setRoles(roleSet);
        userVolunti.setRoles(roleSet);
        userService.saveUser(userAnna);
        userService.saveUser(userVolunti);
        userRepository.save(userLajos);

        log.info("loadData method called ...");
    }

    private void loadSkills() {
        List<Skill> skills = new ArrayList<>();
        List<String> possibleSkills = new ArrayList<>(Arrays.asList("General Maintenance","Language practice",
                "Teaching","Yoga", "Cooking","Nursing","Communication","Programming",
                "Babysitting","Farming","Building","Gardening","Dancing"));

        for (int i = 0; i <possibleSkills.size(); i++){
            skills.add(new Skill(possibleSkills.get(i)));
        }
        skillRepository.save(skills);
    }

    public void testOpportunityGenerator(Organisation org) {
        DataFactory df = new DataFactory();

        List<Skill> skills = new ArrayList<>();
        List<Skill> possibleSkills = (List) skillRepository.findAll();
        for (int i = 0; i < 2; i++) {
            int randomSkill = (int) (Math.random() * possibleSkills.size());
            skills.add(possibleSkills.get(randomSkill));
        }
        if (skills.get(0) == skills.get(1)) {
            skills.remove(1);
        }

        String title = df.getRandomWord() + " " + df.getRandomWord() + " " + df.getRandomWord() + " " + df.getRandomWord();
        Opportunity opportunity = new Opportunity();
        opportunity.setOrganisation(org);
        opportunity.setTitle(title);
        opportunity.setNumberOfVolunteers(df.getNumberBetween(1, 100));
        opportunity.setAccommodationType(df.getRandomWord(5));
        opportunity.setFoodType(df.getRandomWord());
        opportunity.setHoursExpected(df.getNumberBetween(3, 12));
        opportunity.setHoursExpectedType(OpportunityHoursExpectedType.Day);
        opportunity.setMinimumStayInDays(df.getNumberBetween(1, 99));
        opportunity.setAvailabilityFrom(df.getDateBetween(new Date(113, 3, 2), new Date(118, 3, 9)));
        opportunity.setDateAvailabilityTo(df.getDateBetween(new Date(121, 3, 9), new Date(123, 10, 4)));
        opportunity.setCosts("free");
        opportunity.setRequirements(df.getRandomWord());
        opportunity.setOpportunitySkills(skills);

        opportunityRepository.save(opportunity);
    }
}
