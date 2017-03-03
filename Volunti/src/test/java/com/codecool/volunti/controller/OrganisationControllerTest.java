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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by levente on 2017.03.03..
 */
public class OrganisationControllerTest extends AbstractServiceTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllOpps() throws Exception {
        this.mockMvc.perform(get("/opportunities")).andExpect(status().isOk());
    }

    @Test
    public void singleOppView() throws Exception {
        this.mockMvc.perform(get("/opportunities/1")).andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    public void singleOppViewCointainsTable() throws Exception {
        this.mockMvc.perform(get("/opportunities/1"))
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