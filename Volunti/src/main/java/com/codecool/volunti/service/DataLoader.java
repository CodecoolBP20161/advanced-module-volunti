package com.codecool.volunti.service;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.Role;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.model.enums.UserStatus;
import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.RoleService;
import com.codecool.volunti.service.model.UserService;
import com.codecool.volunti.service.model.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
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


        Organisation organisation1 = new Organisation("UNICEF", Category.TEACHING, Country.HUNGARY, "1065", "Isaszeg", "Kossuth utca", spokenLanguages, "mission mission mission mission mission", "description1", "description2","profileHash", "backgroundhash");
        try {
            File testImageFile = new File( "Volunti/src/main/resources/static/images/profile_image/test_profile_image.png" );
            log.info(testImageFile.getAbsolutePath());
            organisation1.setProfilePictureFileForSave(testImageFile);
        } catch (Exception e) {
            log.error("Cannot save test image: ", e);
        }

        Volunteer volunteer = new Volunteer();
        organisationService.save(organisation1);
        volunteerService.save(volunteer);

        if (roleService.findByName(ROLE_USER.getRole()) == null) {

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

        log.info("loadData method called ...");
    }
}

