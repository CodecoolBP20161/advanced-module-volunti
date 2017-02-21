package com.codecool.volunti.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

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
        this.mockMvc.perform(get("/organisation/1/opportunities")).andExpect(status().isOk());

        this.mockMvc.perform(get("/organisation/1/opportunities")).andExpect(
                content().string( containsString("First opportunity") )
        );

        /*
        this.mockMvc.perform(get("/organisation/1/opportunities")).andExpect(
                xpath("//table[@class='table-hover']/tbody/tr/td/h4/b").string("First opportunity")
        );
        */

    }

}
