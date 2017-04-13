package com.codecool.volunti.controller;

import com.codecool.volunti.model.Skill;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.model.enums.UserStatus;
import com.codecool.volunti.repository.SkillRepository;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.repository.VolunteerRepository;
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
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class VolunteerRegistrationControllerTest extends AbstractServiceTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private SkillRepository skillRepository;

    private Volunteer volunteer;
    private User user;
    private MockMvc mockMvc;


    private String validVolunteerFormData = "volunteerId=0" +
            "&country=BARBADOS" +
            "&motto=TestMotto" +
            "&interest=TestInterest" +
            "&spokenLanguages=ACEHNESE&_spokenLanguages=1" +
            "&volunteerSkills=TEACHING" +
            "&dateOfBirth=2000-10-10";

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
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);

        volunteer = new Volunteer();
        volunteer.setCountry("Hungary");
        volunteer.setMotto("my motto");
        volunteer.setInterest("my interest");
        volunteer.setSpokenLanguages(spokenLanguages);
        List<Skill> volunteerSkills = new ArrayList<>();
        volunteerSkills.add(skillRepository.findOne(3));
        volunteer.setVolunteerSkills(volunteerSkills);

        user = new User("Test", "User", "test.user@gmail.com", "testPassword", null, volunteer);
    }

    @Test
    public void test_step1_GET_EmptySession() throws Exception {
        this.mockMvc.perform(get("/registration/volunteer/step1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/user"));
    }

    @Test
    public void test_step1_GET_UserIsInSession() throws Exception {
        this.mockMvc.perform(get("/registration/volunteer/step1").sessionAttr("user", user)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/user"))
                .andExpect(content().string(containsString("test.user")));
    }

    @Test
    public void test_step1_POST_EmptySession() throws Exception {
        this.mockMvc.perform(post("/registration/volunteer/step1")
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/volunteer/step1"));
    }

    @Test
    public void test_step1_POST_UserIsInSession() throws Exception {
        User user = new User();
        this.mockMvc.perform(get("/registration/volunteer/step1").with(csrf()));
        this.mockMvc.perform(post("/registration/volunteer/step1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "firstName")
                .param("lastName", "lastName")
                .param("email", "email@email.hu")
                .param("password", "password")
                .sessionAttr("user", user)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/volunteer/step2/" + user.getId()));

    }

    @Test
    public void test_step2_GET_EmptySession() throws Exception {
        this.mockMvc.perform(get("/registration/volunteer/step2/" + user.getId())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/volunteer/step1"));
    }

    @Test
    public void test_step2_GET_UserIsInSession() throws Exception {
        this.mockMvc.perform(get("/registration/volunteer/step2/" + user.getId())
                .sessionAttr("user", user)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/volunteer/volunteerForm"));
    }

    @Test
    public void test_step2_POST_EmptySession() throws Exception {
        this.mockMvc.perform(post("/registration/volunteer/step2/" + user.getId())
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/volunteer/step1"));

    }


    @Test
    public void test_step3_GET_InValidActivationID() throws Exception {
        this.mockMvc.perform(get("/registration/volunteer/step3/ThisIsDefinitelyNotAnUUID")
                .with(csrf()))
                .andExpect(view().name("information"));


    }

    @Test
    public void test_step3_GET_ValidActivationID() throws Exception {
        String userUUID = UUID.randomUUID().toString();
        user.setActivationID(userUUID);
        Volunteer volunteer = volunteerRepository.findByMottoIgnoreCase("my motto");
        user.setVolunteer(volunteer);
        userRepository.save(user);
        this.mockMvc.perform(get("/registration/volunteer/step3/" + userUUID)
                .with(csrf()))
                .andExpect(content().string(containsString("Account Confirmation is done.")));
        User userAfterRequest = userRepository.findByEmail("test.user@gmail.com");
        assertEquals(UserStatus.ACTIVE, userAfterRequest.getUserStatus());
    }
}






