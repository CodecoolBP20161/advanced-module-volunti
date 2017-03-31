package com.codecool.volunti.controller;

import com.codecool.volunti.service.AbstractServiceTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OpportunityRestControllerTest extends AbstractServiceTest{

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private String content ="";

    @Autowired
    OpportunityRestController opportunityRestController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        MvcResult oppResult = null;
        try {
            oppResult = this.mockMvc.perform(get("/api/opportunities/find/1").with(csrf())).andExpect(status().isOk()).andReturn();
            content = oppResult.getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findOppReturns200() throws Exception {
        this.mockMvc.perform(get("/api/opportunities/find/1").with(csrf())).andExpect(status().isOk());
    }


    @Test
    public void findOppReturnValIsContains() throws Exception {
        this.mockMvc.perform(get("/api/opportunities/find/1").with(csrf())).andExpect(content().contentType("text/html;charset=UTF-8"));
    }


    @Test
    public void filterSkills() throws Exception {
        this.mockMvc.perform(get("/api/opportunities/filters").with(csrf())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").exists());
    }


    @Test
    public void filterCustom() throws Exception {
        this.mockMvc.perform(get("/api/opportunities/find/1?from=1999-10-10&to=" +
                "2020-10-10&location=Hungary&skills&category&pageSize=10").with(csrf()))
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }
}