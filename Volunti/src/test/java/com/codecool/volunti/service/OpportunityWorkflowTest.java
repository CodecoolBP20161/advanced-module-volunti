package com.codecool.volunti.service;

import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.Skill;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.SkillRepository;
import com.codecool.volunti.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

//TODO: delete and update method works only with dirtiesContext fix it!
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OpportunityWorkflowTest extends AbstractServiceTest {

    private Opportunity opportunity;
    private Organisation organisation;
    @Resource
    private WebApplicationContext webApplicationContext;

    @Autowired
    private OpportunityRepository opportunityRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
    private MockMvc mockMvc;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        spokenLanguages.add(SpokenLanguage.ENGLISH);
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);
        Skill skill = new Skill("new Skill");
        skillRepository.save(skill);

        organisation = new Organisation();
        organisation.setName("Test 1");
        organisation.setCategory(Category.TEACHING);
        organisation.setCountry(Country.HUNGARY);
        organisation.setZipcode("zipcode");
        organisation.setCity("City");
        organisation.setAddress("Address");
        organisation.setSpokenLanguage(spokenLanguages);
        organisation.setMission("Mission minimum 10 character");
        organisation.setDescription1("Desc 1 min 3 character");
        organisation.setDescription2("Desc 2 min 3 character");
        organisationRepository.save(organisation);

        opportunity = new Opportunity();
        opportunity.setOrganisation(organisation);
        opportunity.setTitle("First opportunity");
        opportunity.setNumberOfVolunteers(10);
        opportunity.setAccommodationType("Tent");
        opportunity.setFoodType("Vega");
        opportunity.setHoursExpected(3);
        opportunity.setHoursExpectedType(null);
        opportunity.setMinimumStayInDays(2);
        opportunity.setAvailabilityFrom(new java.sql.Date(2017 - 02 - 16));
        opportunity.setDateAvailabilityTo(new java.sql.Date(2017 - 02 - 21));
        opportunity.setCosts("free");
        opportunity.setRequirements("English");

        List<Skill> skills = new ArrayList<>();
        skills.add(skill);

        opportunity.setOpportunitySkills(skills);
        opportunityRepository.save(opportunity);

        User user1 = new User();
        user1.setFirstName("Test1 first name");
        user1.setLastName("Test1 last name");
        user1.setEmail("Email1");
        user1.setPassword("Password1");
        user1.setOrganisation(organisation);
        userRepository.save(user1);

    }

    @Test
    @WithMockUser(username="Email1",roles={"USER"})
    public void getTheList() throws Exception {

        this.mockMvc.perform(get("/profile/organisation/opportunities").with(csrf()))
                .andExpect(content()
                        .string(containsString(opportunity.getTitle()))
        );

        this.mockMvc.perform(get("/profile/organisation/opportunities").with(csrf()))
                .andExpect(content().string(not(containsString("TestTitle")))
                );

        this.mockMvc.perform(post("/profile/organisation/opportunity/0").with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("title=TestTitle&opportunitySkills=&_opportunitySkills" +
                "=1&numberOfVolunteers=12&accommodationType=sa&foodType=sadasd&hoursExpected=5&minimumStayInDays=5&availabilityFrom=" +
                "2017-02-25&dateAvailabilityTo=&costs=asd&requirements=sadsad"));

        this.mockMvc.perform(get("/profile/organisation/opportunities").with(csrf()))
                .andExpect(content().string(containsString("TestTitle"))
                );
    }

    @Test
    @WithMockUser(username="Email1",roles={"USER"})
    public void deleteItem() throws Exception {

        this.mockMvc.perform(get("/profile/organisation/opportunities").with(csrf()))
                .andExpect(content().string(containsString(opportunity.getTitle())
                ));

        this.mockMvc.perform(get("/profile/organisation/opportunity/delete/" + opportunity.getId() + "?").with(csrf())
                );
        this.mockMvc.perform(get("/profile/organisation/opportunities").with(csrf()))
                .andExpect(content().string(not(containsString(opportunity.getTitle())))
                );
    }

    @Test
    @WithMockUser(username="Email1",roles={"USER"})
    public void updateItem() throws Exception {

        this.mockMvc.perform(post("/profile/organisation/opportunity/" + opportunity.getId()).with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("title=First+title+edited&opportunitySkills=&_opportunitySkills=1" +
                        "&numberOfVolunteers=10&accommodationType=Tent&foodType=Vega&hoursExpected=" +
                        "3&minimumStayInDays=6&availabilityFrom=1970-01-01&dateAvailabilityTo=" +
                        "1970-01-01&costs=free&requirements=English"));

        this.mockMvc.perform(get("/profile/organisation/opportunities").with(csrf()))
                .andExpect(content().string(containsString("First title edited"))
                );
    }


}
