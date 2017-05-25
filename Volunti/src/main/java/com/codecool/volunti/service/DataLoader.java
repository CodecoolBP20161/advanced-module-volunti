package com.codecool.volunti.service;


import com.codecool.volunti.configuration.DataloaderProperties;
import com.codecool.volunti.model.*;
import com.codecool.volunti.model.enums.*;
import com.codecool.volunti.service.model.OrganisationService;
import com.codecool.volunti.service.model.RoleService;
import com.codecool.volunti.service.model.UserService;
import com.codecool.volunti.service.model.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
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
    private final DataloaderProperties properties;

    @Autowired
    public DataLoader(OrganisationService organisationService, UserService userService, VolunteerService volunteerService, RoleService roleService, BCryptPasswordEncoder passwordEncoder, DataloaderProperties properties) {
        this.organisationService = organisationService;
        this.userService = userService;
        this.volunteerService = volunteerService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.properties = properties;
    }

    @PostConstruct
    public void loadData() {

        if ( ! this.properties.isEnabled() ) {
            log.info("SKIP DataLoader - loadData()");
            return;
        }

        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        spokenLanguages.add(SpokenLanguage.ENGLISH);
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);


        Organisation organisation1 = new Organisation("UNICEF",
                Category.Educational,
                Country.HUNGARY,
                "1065",
                "Isaszeg",
                "Kossuth utca",
                spokenLanguages,
                "Our mission is to help people in need, and make the world a better place.",
                "Description 1: UNICEF is a non profit organisation helping mainly children." +
                        "UNICEF is a non profit organisation helping mainly children.",
                "description 2: We provide the basic needs for every children: we prevent starvation, child-abuse, we grant education,  etc." +
                        "We provide the basic needs for every children: we prevent starvation, child-abuse, we grant education,  etc.",
                "",
                null);

        try {
            File testImageFile = new File( "Volunti/src/main/resources/static/images/profile_image/test_profile_image.png" );
            organisationService.setDefaultBackgroundImage(organisation1);
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

        OrganisationSocialLink organisationSocialLink1 = new OrganisationSocialLink();
        organisationSocialLink1.setSocialLinkType(SocialLink.FACEBOOK);
        organisationSocialLink1.setSocialLinkUrl("https://www.facebook.com/EVCPF/?fref=ts");
        organisationSocialLink1.setOrganisationId(organisation1);
        organisationService.save(organisationSocialLink1);

        OrganisationSocialLink organisationSocialLink2 = new OrganisationSocialLink();
        organisationSocialLink2.setSocialLinkType(SocialLink.TWITTER);
        organisationSocialLink2.setSocialLinkUrl("https://twitter.com/?lang=hu");
        organisationSocialLink2.setOrganisationId(organisation1);
        organisationService.save(organisationSocialLink2);

        OrganisationSocialLink organisationSocialLink3 = new OrganisationSocialLink();
        organisationSocialLink3.setSocialLinkType(SocialLink.LINKEDIN);
        organisationSocialLink3.setSocialLinkUrl("https://www.linkedin.com/");
        organisationSocialLink3.setOrganisationId(organisation1);
        organisationService.save(organisationSocialLink3);

        log.info("loadData method called ...");
    }
}

