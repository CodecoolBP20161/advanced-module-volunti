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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OpportunityRestControllerTest extends AbstractServiceTest{

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private String content ="";

    private String filters ="";

    @Autowired
    OpportunityRestController opportunityRestController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        MvcResult oppResult = null;
        try {
            oppResult = this.mockMvc.perform(get("/api/opportunities/find/all/1")).andExpect(status().isOk()).andReturn();
            content = oppResult.getResponse().getContentAsString();

            oppResult = this.mockMvc.perform(get("/api/opportunities/filters")).andExpect(status().isOk()).andReturn();
            filters = oppResult.getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findOppReturns200() throws Exception {
        this.mockMvc.perform(get("/api/opportunities/find/all/1")).andExpect(status().isOk());
    }

    @Test
    public void findOppReturnValIsJSON() throws Exception {
        this.mockMvc.perform(get("/api/opportunities/find/all/1")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void findOppReturnValIsContains() throws Exception {
        this.mockMvc.perform(get("/api/opportunities/find/all/1")).andExpect(content().string(content));
    }

    @Test
    public void filters() throws Exception {
        this.mockMvc.perform(get("/api/opportunities/filters")).andExpect(status().isOk());
    }

    @Test
    public void filterSkills() throws Exception {
        this.mockMvc.perform(get("/api/opportunities/filters")).andExpect(content().json(filters));
    }

    @Test
    public void filterCustom() throws Exception {
        this.mockMvc.perform(get("/api/opportunities/find/1?from=2020-10-10&to=" +
                "1999-10-10&location=Hungary&skills&category&pageSize=10"))
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }
}