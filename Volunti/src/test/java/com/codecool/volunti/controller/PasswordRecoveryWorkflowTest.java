package com.codecool.volunti.controller;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.model.enums.UserStatus;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.service.AbstractServiceTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;



import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;


import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNotSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class PasswordRecoveryWorkflowTest extends AbstractServiceTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganisationRepository organisationRepository;


    private Organisation organisation;
    private User user;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        organisation = new Organisation();
        organisation.setName("TestName");
        organisation.setCategory(Category.ADVERTISING_AGENCY);
        organisation.setCountry(Country.HUNGARY);
        organisation.setZipcode("ZIPCODE");
        organisation.setCity("TestCity");
        organisation.setAddress("Address");
        organisation.setMission("mission");
        organisation.setDescription1("Description1");
        organisation.setDescription2("Description2");
        organisation.setSpokenLanguage(new ArrayList<>(Arrays.asList(SpokenLanguage.AFAR, SpokenLanguage.ALBANIAN)));
        organisationRepository.save(organisation);

        user = new User("Test", "User", "volunti.trial.PasswordRecoveryTest@gmail.com", "testPassword", organisation, null);
        user.setUserStatus(UserStatus.ACTIVE);
        user = userRepository.save(user);

    }

    @Test
    public void test_step1_GET_HappyPath() throws Exception {
        this.mockMvc.perform(get("/forgotPassword/step1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("forgotPassword"));
    }

    @Test
    public void test_step1_POST_EmailExistsInDatabase() throws Exception {
        this.mockMvc.perform(post("/forgotPassword/step1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("emailAddress=volunti.trial.PasswordRecoveryTest@gmail.com"))
                .andExpect(status().isOk())
            .andExpect(view().name("information"))
            .andExpect(content().string(containsString("If your email address exists in our database,we have sent an e-mail to you with a reset link.")));
    }

    @Test
    public void test_step1_POST_EmailIsNotInDatabase() throws Exception {
        this.mockMvc.perform(post("/forgotPassword/step1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("emailAddress=not.used.email@gmail.com"))
//                .andExpect(status().isOk())
//            .andExpect(view().name("information"))
            .andExpect(content().string(containsString("If your email address exists in our database,we have sent an e-mail to you with a reset link.")));
    }

    @Test
    public void test_step2_GET_ValidActivationID() throws Exception {
        this.mockMvc.perform(post("/forgotPassword/step1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("emailAddress=not.used.email@gmail.com"));

        user = userRepository.findByEmail("volunti.trial.PasswordRecoveryTest@gmail.com");

        this.mockMvc.perform(get("/forgotPassword/step2/"  + user.getActivationID()))
                .andExpect(status().isOk())
                .andExpect(view().name("newPasswordForm"));
    }

    @Test
    public void test_step2_POST_HappyPath() throws Exception {
        String oldPassword = user.getPassword();
        String validPasswordFormURL = "id=" + user.getId() + "&" +
                "firstName=Test&" +
                "lastName=User&" +
                "email=volunti.trial.PasswordRecoveryTest@gmail.com&" +
                "activationID=" + user.getActivationID() + "&" +
                "userStatus=ACTIVE&" +
                "organisation=2&" +
                "volunteer=&" +
                "password=newPassword";

        this.mockMvc.perform(post("/forgotPassword/step2/")
                .with(csrf())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(validPasswordFormURL))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        user = userRepository.findByEmail("volunti.trial.PasswordRecoveryTest@gmail.com");
        assertNotSame(oldPassword, user.getPassword());
    }

}

