package com.codecool.volunti.service;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RegistrationWorkflowTest extends AbstractServiceTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private String validOrganisationFormData = "organisationId=0" +
            "&name=TestName" +
            "&category=TELEVISION" +
            "&country=Barbados" +
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
    private Organisation organisation;
    private User user;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        organisation = new Organisation();
        organisation.setName("TestName");
        organisation.setCategory(Category.ADVERTISING_AGENCY);
        organisation.setCountry(Country.Hungary);
        organisation.setZipcode("ZIPCODE");
        organisation.setCity("TestCity");
        organisation.setAddress("Address");
        organisation.setMission("mission");
        organisation.setDescription1("Desc1");
        organisation.setDescription2("Desc2");

        user = new User("Test", "USer", "test.user@gmail.com", "testPassword", "testSalt");


    }

    @Test
    public void step1_GET_EmptySession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step1"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/organisation/step1"));
    }

    @Test
    public void step1_GET_OrganisationIsInSession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step1").sessionAttr("organisation", organisation))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/organisation/step1"))
                .andExpect(content().string(containsString("TestCity")));
    }

    @Test
    public void step1_POST_EmptySession() throws Exception {
        this.mockMvc.perform(post("/registration/organisation/step1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(validOrganisationFormData))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step1"));
    }

    @Test
    public void step1_POST_OrganisationIsInSession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step1"));
        this.mockMvc.perform(post("/registration/organisation/step1").sessionAttr("organisation", organisation)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(validOrganisationFormData))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step2/0"));
    }

    @Test
    public void step2_GET_EmptySession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step2/0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step1"));
    }

    @Test
    public void step2_GET_OrganisationIsInSession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step2/0").sessionAttr("organisation", organisation))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/step2"));
    }

    @Test
    public void Step2_POST_EmptySession() throws Exception {
        this.mockMvc.perform(post("/registration/organisation/step2/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(validUserFormData))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step1"));

    }

    @Test
    public void step3_GET_InValidActivationID() throws Exception {
        this.mockMvc.perform(post("/registration/organisation/step2/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(validUserFormData))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step1"));

    }

    @Test
    public void step3_GET_ValidActivationID() throws Exception {

        this.mockMvc.perform(post("/registration/organisation/step2/")
                            .sessionAttr("organisation", organisation)
                            .sessionAttr("user", user)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(validOrganisationFormData))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step2/0"));

    }
}
