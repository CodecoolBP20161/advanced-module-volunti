package com.codecool.volunti.controller;


import com.codecool.volunti.service.AbstractServiceTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class OrganisationProfileEditingWorkflowTest extends AbstractServiceTest {

    @Resource
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    public void test_renderOrganisationProfile_GET_Should_Render_Page_AsLoggedInUser() throws Exception {
        this.mockMvc.perform(get("/profile/organisation")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("profiles/organisation"));
    }

    @Test
    public void test_renderOrganisationProfile_GET_Should_Result_302() throws Exception {
        this.mockMvc.perform(get("/profile/organisation")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(status().isFound());
    }

    @Test
    public void test_renderOrganisationProfile_GET_Should_Redirect_ToLogin() throws Exception {
        this.mockMvc.perform(get("/profile/organisation")
                .with(csrf()))
                .andExpect((redirectedUrl("http://localhost/login")))
                .andExpect(status().isFound());
    }
}
