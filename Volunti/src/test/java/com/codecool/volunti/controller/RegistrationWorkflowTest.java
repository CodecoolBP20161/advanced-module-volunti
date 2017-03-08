package com.codecool.volunti.controller;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
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

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.assertEquals;

public class  RegistrationWorkflowTest extends AbstractServiceTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleService roleService;

    private Volunteer volunteer;
    private Organisation organisation;
    private User user;
    private MockMvc mockMvc;
    private UserService userService = new UserService(userRepository, roleService);
    private JdbcTemplate jdbcTemplate;

    private String validOrganisationFormData = "organisationId=0" +
            "&name=TestName" +
            "&category=TELEVISION" +
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


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

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
        organisation.setDescription1("Desc1");
        organisation.setDescription2("Desc2");

        user = new User("Test", "User", "test.user@gmail.com", "testPassword", organisation, null);


    }

    @Test
    public void test_step1_GET_EmptySession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step1"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/organisation/organisation"));
    }

    @Test
    public void test_step1_GET_OrganisationIsInSession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step1").sessionAttr("organisation", organisation))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/organisation/organisation"))
                .andExpect(content().string(containsString("TestCity")));
    }

    @Test
    public void test_step1_POST_EmptySession() throws Exception {
        this.mockMvc.perform(post("/registration/organisation/step1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(validOrganisationFormData))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step1"));
    }

    @Test
    public void test_step1_POST_OrganisationIsInSession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step1"));
        this.mockMvc.perform(post("/registration/organisation/step1").sessionAttr("organisation", organisation)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(validOrganisationFormData))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step2/0"));

    }

    @Test
    public void test_step2_GET_EmptySession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step2/0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step1"));
    }

    @Test
    public void test_step2_GET_OrganisationIsInSession() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step2/0").sessionAttr("organisation", organisation))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/user"));
    }

    @Test
    public void test_step2_POST_EmptySession() throws Exception {
        this.mockMvc.perform(post("/registration/organisation/step2/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(validUserFormData))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/organisation/step1"));

    }

    @Test
    public void test_step3_GET_InValidActivationID() throws Exception {
        this.mockMvc.perform(get("/registration/organisation/step3/ThisIsDefinitelyNotAnUUID"))
                .andExpect(view().name("information"));


    }


    @Test
    public void test_step3_GET_ValidActivationID() throws Exception {
        String userUUID = UUID.randomUUID().toString();
        user.setActivationID(userUUID);
        Organisation organisation = organisationRepository.findByNameIgnoreCase("UNICEF");
        user.setOrganisation(organisation);
        userRepository.save(user);
        this.mockMvc.perform(get("/registration/organisation/step3/" + userUUID))
                .andExpect(content().string(containsString("Account Confirmation is done.")));
        User userAfterRequest = userRepository.findByEmail("test.user@gmail.com");
        assertEquals(UserStatus.ACTIVE, userAfterRequest.getUserStatus());
    }
}
