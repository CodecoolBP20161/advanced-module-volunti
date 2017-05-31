package com.codecool.volunti.controller;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.model.enums.UserStatus;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.service.AbstractServiceTest;

import com.codecool.volunti.service.model.OrganisationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.assertEquals;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class RegistrationWorkflowTest extends AbstractServiceTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    private Organisation organisation;
    private User user;
    private MockMvc mockMvc;

    private String validOrganisationFormData = "organisationId=0" +
            "&name=TestName" +
            "&category=Animal_Charity" +
            "&country=BARBADOS" +
            "&zipcode=1234zipTest" +
            "&city=testCity" +
            "&address=TestAddress" +
            "&spokenLanguage=ACEHNESE&_spokenLanguage=1" +
            "&mission=TestMission" +
            "&description1=TestDesc1" +
            "&description2=TestDesc2";

    private String validUserFormData = "firstName=firstName" +
            "&lastName=lastName" +
            "&email=email%40email.hu" +
            "&password=password";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        spokenLanguages.add(SpokenLanguage.ENGLISH);

        organisation = new Organisation();
        organisation.setName("TestName");
        organisation.setCategory(Category.Educational);
        organisation.setCountry(Country.HUNGARY);
        organisation.setZipcode("1011");
        organisation.setCity("TestCity");
        organisation.setAddress("Address");
        organisation.setSpokenLanguage(spokenLanguages);
        organisation.setMission("mission");
        organisation.setDescription1("Desc1");
        organisation.setDescription2("Desc2");


        user = new User("Test", "User", "test.user@gmail.com", "testPassword", organisation, null);
    }

    @Test
    public void test_step1_GET_EmptySession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/organisation/organisation"));
    }

    @Test
    public void test_step1_GET_OrganisationIsInSession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step1").sessionAttr("organisation", organisation)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/organisation/organisation"))
                .andExpect(content().string(containsString("TestCity")));
    }

    @Test
    public void test_step1_POST_EmptySession() throws Exception {
        this.mockMvc.perform(post("/registration/organisation/step1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(validOrganisationFormData))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step1"));
    }

    @Test
    public void test_step1_POST_OrganisationIsInSession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step1").with(csrf()));
        this.mockMvc.perform(post("/registration/organisation/step1").sessionAttr("organisation", organisation)
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(validOrganisationFormData))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step2/0"));

    }

    @Test
    public void test_step2_GET_EmptySession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step2/0")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step1"));
    }

    @Test
    public void test_step2_GET_OrganisationIsInSession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step2/0").sessionAttr("organisation", organisation)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/user"));
    }

    @Test
    public void test_step2_POST_EmptySession() throws Exception {
        this.mockMvc.perform(post("/registration/organisation/step2/")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(validUserFormData))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step1"));

    }

    @Test
    public void test_step2_POST_ValidSession() throws Exception {
        this.mockMvc.perform(post("/registration/organisation/step2/").sessionAttr("organisation", organisation)
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(validUserFormData))
                .andExpect(status().isOk())
                .andExpect(view().name("information"));
        User userAfterRequest = userRepository.findByEmail("email@email.hu");
        assertEquals(UserStatus.INACTIVE, userAfterRequest.getUserStatus());
    }

    @Test
    public void test_step3_GET_InValidActivationID() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step3/ThisIsDefinitelyNotAnUUID")
                .with(csrf()))
                .andExpect(view().name("information"));


    }

    @Test
    public void test_step3_GET_ValidActivationID() throws Exception {
        Organisation organisation = organisationRepository.save(this.organisation);

        String userUUID = UUID.randomUUID().toString();
        user.setActivationID(userUUID);
        user.setOrganisation(organisation);
        userRepository.save(user);

        assertEquals(UserStatus.INACTIVE, user.getUserStatus());
        this.mockMvc.perform(get("/registration/organisation/step3/" + userUUID)
                .with(csrf()))
                .andExpect(content().string(containsString("Account Confirmation is done.")));
        User userAfterRequest = userRepository.findByEmail("test.user@gmail.com");
        assertEquals(UserStatus.ACTIVE, userAfterRequest.getUserStatus());
    }
}
