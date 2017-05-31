package com.codecool.volunti.controller;

import com.codecool.volunti.service.AbstractServiceTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class OrganisationRestControllerTest extends AbstractServiceTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    OrganisationRestController organisationRestController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void findContent() throws Exception {
        mockMvc.perform(get("/api/organisation/profile-data"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.countries").exists())
                .andExpect(jsonPath("$.categories").exists());
    }

    @Test
    public void findAllCategories() throws Exception {
        mockMvc.perform(get("/api/organisation/profile-data"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.categories.length()").value(13));
    }

    @Test
    public void findAllCountries() throws Exception {
        mockMvc.perform(get("/api/organisation/profile-data"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.countries.length()").value(238));
    }

}
