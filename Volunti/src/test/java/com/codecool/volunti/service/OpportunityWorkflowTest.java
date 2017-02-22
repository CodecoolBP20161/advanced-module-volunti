package com.codecool.volunti.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class OpportunityWorkflowTest extends AbstractServiceTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getTheList() throws Exception {

        this.mockMvc.perform(get("/organisation/1/opportunities"))
                .andExpect(content()
                        .string(containsString("First opportunity"))
        );
        this.mockMvc.perform(get("/organisation/1/opportunities"))
                .andExpect(content()
                        .string(containsString("Second opportunity"))
                );

        this.mockMvc.perform(get("/organisation/1/opportunities"))
                .andExpect(content().string(not(containsString("TestTitle")))
                );

        this.mockMvc.perform(post("/organisation/1/opportunity/0")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("title=TestTitle&opportunitySkills=&_opportunitySkills" +
                "=1&numberOfVolunteers=12&accommodationType=sa&foodType=sadasd&hoursExpected=5&minimumStayInDays=5&availabilityFrom=" +
                "2017-02-25&dateAvailabilityTo=&costs=asd&requirements=sadsad"));

        this.mockMvc.perform(get("/organisation/1/opportunities"))
                .andExpect(content().string(containsString("TestTitle"))
                );
    }

    @Test
    public void deleteItem() throws Exception {

        this.mockMvc.perform(get("/organisation/1/opportunities"))
                .andExpect(content()
                        .string(containsString("Second opportunity"))
                );
        this.mockMvc.perform(get("/organisation/1/opportunity/delete/2?")
                );
        this.mockMvc.perform(get("/organisation/1/opportunities"))
                .andExpect(content().string(not(containsString("Second opportunity")))
                );
    }

    @Test
    public void updateItem() throws Exception {

        this.mockMvc.perform(post("/organisation/1/opportunity/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("title=First+title+edited&opportunitySkills=&_opportunitySkills=1" +
                        "&numberOfVolunteers=10&accommodationType=Tent&foodType=Vega&hoursExpected=" +
                        "3&minimumStayInDays=6&availabilityFrom=1970-01-01&dateAvailabilityTo=" +
                        "1970-01-01&costs=free&requirements=English"));

        this.mockMvc.perform(get("/organisation/1/opportunities"))
                .andExpect(content().string(containsString("First title edited"))
                );
    }

}
