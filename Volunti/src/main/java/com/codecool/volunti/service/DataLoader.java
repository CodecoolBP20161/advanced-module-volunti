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

    private OrganisationService organisationService;
    private UserService userService;
    private VolunteerService volunteerService;
    private RoleService roleService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(OrganisationService organisationService, UserService userService, VolunteerService volunteerService, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.organisationService = organisationService;
        this.userService = userService;
        this.volunteerService = volunteerService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void loadData() {
        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        spokenLanguages.add(SpokenLanguage.ENGLISH);
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);

        Organisation organisation1 = new Organisation("UNICEF", Category.TEACHING, Country.HUNGARY, "1065", "Isaszeg", "Kossuth utca", spokenLanguages, "mission mission mission mission mission", "description1", "description2");
        Volunteer volunteer = new Volunteer();


        if (roleService.findByName(ROLE_USER.getRole()) == null) {
            organisationService.save(organisation1);
            volunteerService.save(volunteer);
            Role roleAdmin = new Role(ROLE_USER.getRole());
            roleService.save(roleAdmin);

            User user1 = new User("Anna", "Kiss", "asd@gmail.com", passwordEncoder.encode("codecool"), organisation1, volunteer);

            Set<Role> roleSet = new HashSet<>();
            roleSet.add(roleAdmin);
            user1.setRoles(roleSet);
            userService.saveUser(user1);
        }

        log.info("loadData method called ...");
    }
}

