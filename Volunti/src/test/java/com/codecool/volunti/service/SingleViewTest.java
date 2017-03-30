package com.codecool.volunti.service;

import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class SingleViewTest extends AbstractServiceTest {

    @Autowired
    OpportunityRepository opportunityRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void getSingleWPageExpect200() throws Exception {
        this.mockMvc.perform(get("/opportunities/1").with(csrf())).andExpect(status().isOk());
    }


    @Test
    public void opportunityAppears0nPage() throws Exception {
        Opportunity idOne = this.opportunityRepository.findOne(1);
        String title = idOne.getTitle();
        String numVolunteers = String.valueOf(idOne.getNumberOfVolunteers());
        String accomodation = idOne.getAccommodationType();

        this.mockMvc.perform(get("/opportunities/1").with(csrf())).andExpect(content().string(containsString(title)))
                .andExpect(content().string(containsString(numVolunteers)))
                .andExpect(content().string(containsString(accomodation)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void OrganisationAppears0nPage() throws Exception {
        Organisation idOne = this.organisationRepository.findOne(1);
        String address = idOne.getAddress();

        this.mockMvc.perform(get("/opportunities/1").with(csrf())).andExpect(content().string(containsString(address)))
                .andExpect(status().is2xxSuccessful());
    }
}
