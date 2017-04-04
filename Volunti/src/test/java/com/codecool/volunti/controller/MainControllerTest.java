package com.codecool.volunti.controller;

import com.codecool.volunti.service.AbstractServiceTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by levente on 2017.03.06..
 */
public class MainControllerTest extends AbstractServiceTest{

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

    }

    @Test
    public void indexPage() throws Exception {
        mockMvc.perform(get("/").with(csrf())).andExpect(status().isOk());
    }
}
