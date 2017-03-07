package com.codecool.volunti.service;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.Role;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.repository.VolunteerRepository;
import lombok.extern.slf4j.Slf4j;
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

import static com.codecool.volunti.model.enums.RoleEnum.ROLE_USER;

@Slf4j
@Service
@Transactional
public class DataLoader {

    private OrganisationRepository organisationRepository;
    private UserRepository userRepository;
    private  VolunteerRepository volunteerRepository;
    private Organisation organisation;
    private Volunteer volunteer;

    @Autowired
    RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

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


        if (roleService.findByName(ROLE_USER.getRole()) == null) {
            organisationRepository.save(organisation1);
            volunteerRepository.save(volunteer);
            Role roleAdmin = new Role(ROLE_USER.getRole());
            roleService.save(roleAdmin);

            User user1 = new User("Anna", "Kiss", "asd@gmail.com", passwordEncoder.encode("codecool"), organisation1, volunteer);

            Set<Role> roleSet = new HashSet<>();
            roleSet.add(roleAdmin);
            user1.setRoles(roleSet);
            userService.save(user1);
        }



        log.info("loadData method called ...");
    }
}

