package com.codecool.volunti.service;

import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class OpportunityWorkflowTest extends AbstractServiceTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    @Autowired
    private OpportunityRepository opportunityRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser
    public void getTheList() throws Exception {
        Opportunity idOne = this.opportunityRepository.findOne(1);
        String title = idOne.getTitle();

        this.mockMvc.perform(get("/organisation/1/opportunities").with(csrf()))
                .andExpect(content()
                        .string(containsString(title))
        );

        this.mockMvc.perform(get("/organisation/1/opportunities").with(csrf()))
                .andExpect(content().string(not(containsString("TestTitle")))
                );

        this.mockMvc.perform(post("/organisation/1/opportunity/0").with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("title=TestTitle&opportunitySkills=&_opportunitySkills" +
                "=1&numberOfVolunteers=12&accommodationType=sa&foodType=sadasd&hoursExpected=5&minimumStayInDays=5&availabilityFrom=" +
                "2017-02-25&dateAvailabilityTo=&costs=asd&requirements=sadsad"));

        this.mockMvc.perform(get("/organisation/1/opportunities").with(csrf()))
                .andExpect(content().string(containsString("TestTitle"))
                );
    }

    @Test
    @WithMockUser
    public void deleteItem() throws Exception {
        Organisation unicef = organisationRepository.findByName("UNICEF");
        List<Opportunity> opportunityList = opportunityRepository.findByOrganisationOrderByIdAsc(unicef);
        Opportunity opportunity = opportunityList.get(2);


        this.mockMvc.perform(get("/organisation/1/opportunities").with(csrf()))
                .andExpect(content().string(containsString(opportunity.getTitle())
                ));

        this.mockMvc.perform(get("/organisation/1/opportunity/delete/" + opportunity.getId() + "?").with(csrf())
                );
        this.mockMvc.perform(get("/organisation/1/opportunities").with(csrf()))
                .andExpect(content().string(not(containsString(opportunity.getTitle())))
                );
    }

    @Test
    @WithMockUser
    public void updateItem() throws Exception {

        this.mockMvc.perform(post("/organisation/1/opportunity/1").with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("title=First+title+edited&opportunitySkills=&_opportunitySkills=1" +
                        "&numberOfVolunteers=10&accommodationType=Tent&foodType=Vega&hoursExpected=" +
                        "3&minimumStayInDays=6&availabilityFrom=1970-01-01&dateAvailabilityTo=" +
                        "1970-01-01&costs=free&requirements=English"));

        this.mockMvc.perform(get("/organisation/1/opportunities").with(csrf()))
                .andExpect(content().string(containsString("First title edited"))
                );
    }

}
