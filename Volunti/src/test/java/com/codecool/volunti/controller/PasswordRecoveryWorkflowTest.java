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
import com.codecool.volunti.service.RoleService;
import com.codecool.volunti.service.UserService;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


public class  PasswordRecoveryWorkflowTest extends AbstractServiceTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    private Volunteer volunteer;
    private Organisation organisation;
    private User user;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        volunteer = new Volunteer();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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

        user = new User("Test", "User", "test.user@gmail.com", "testPassword", organisation, null);
        user = userRepository.save(user);

    }

    @Test
    public void test_step1_GET_HappyPath() throws Exception {
        this.mockMvc.perform(get("/forgotPassword/step1"))
                .andExpect(status().isOk())
                .andExpect(view().name("forgotPassword"));
    }

    @Test
    public void test_step1_POST_EmailExistsInDatabase() throws Exception {
        this.mockMvc.perform(post("/forgotPassword/step1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("emailAddress=test.user@gmail.com"))
                .andExpect(status().isOk())
            .andExpect(view().name("information"))
            .andExpect(content().string(containsString("If your email address exists in our database,we have sent an e-mail to you with a reset link.")));
    }

    @Test
    public void test_step1_POST_EmailIsNotInDatabase() throws Exception {
        this.mockMvc.perform(post("/forgotPassword/step1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("emailAddress=not.used.email@gmail.com"))
                .andExpect(status().isOk())
            .andExpect(view().name("information"))
            .andExpect(content().string(containsString("If your email address exists in our database,we have sent an e-mail to you with a reset link.")));
    }

    @Test
    public void test_step2_GET_ValidActivationID() throws Exception {
        this.mockMvc.perform(post("/forgotPassword/step1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("emailAddress=not.used.email@gmail.com"));

        user = userRepository.findByEmail("test.user@gmail.com");

        this.mockMvc.perform(get("/forgotPassword/step2/"  + user.getActivationID()))
                .andExpect(view().name("newPasswordForm"));
    }

    @Test
    public void test_step2_POST_HappyPath() throws Exception {
        String oldPassword = user.getPassword();
        String validPasswordFormURL =   "id=2&" +
                "firstName=Test&" +
                "lastName=User&" +
                "email=test.user@gmail.com&" +
                "activationID=" + user.getActivationID() +
                "userStatus=ACTIVE&" +
                "organisation=2&" +
                "volunteer=&" +
                "roles=&" +
                "password=newPassword";

        this.mockMvc.perform(post("/forgotPassword/step2/")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(validPasswordFormURL))
                .andExpect(view().name("index"));
        user = userRepository.findByEmail("test.user@gmail.com");
        assertNotSame(oldPassword, user.getPassword());
    }

}

