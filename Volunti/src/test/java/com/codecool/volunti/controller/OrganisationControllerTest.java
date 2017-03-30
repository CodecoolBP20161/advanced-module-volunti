package com.codecool.volunti.controller;

import com.codecool.volunti.service.AbstractServiceTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
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


public class OrganisationControllerTest extends AbstractServiceTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void getAllOpps() throws Exception {
        this.mockMvc.perform(get("/opportunities").with(csrf())).andExpect(status().isOk());
    }

    @Test
    public void singleOppViewLoads() throws Exception {
        this.mockMvc.perform(get("/opportunities/1").with(csrf())).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void singleOppViewTypeIsHtml() throws Exception {
        this.mockMvc.perform(get("/opportunities/1").with(csrf())).andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    public void singleOppViewContainsTable() throws Exception {
        this.mockMvc.perform(get("/opportunities/1").with(csrf()))
                .andExpect(content().string(containsString("<thead>\n" +
                        "                        <tr>\n" +
                        "                            <th>Title</th>\n" +
                        "                            <th>Required</th>\n" +
                        "                            <th>Food</th>\n" +
                        "                            <th>num. days</th>\n" +
                        "                        </tr>\n" +
                        "                        </thead>"))
        );
    }
}