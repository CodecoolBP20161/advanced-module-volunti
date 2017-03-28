package com.codecool.volunti.controller;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.service.AbstractServiceTest;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class OrganisationProfileControllerTest extends AbstractServiceTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private OrganisationProfileController organisationProfileController;

    private Organisation organisation;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp() throws Exception {
        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        spokenLanguages.add(SpokenLanguage.ENGLISH);
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);
        organisation = new Organisation("testOrg", Category.TEACHING, Country.HUNGARY,
                "1065", "Isaszeg", "Kossuth utca", spokenLanguages,
                "mission", "description1",
                "description2", "profileHash", "backgroundhash");

        organisationRepository.save(organisation);
    }

    @After
    public void tearDown() throws Exception {
        JdbcTestUtils.deleteFromTableWhere(jdbcTemplate, "organisations", "name='testOrg'");
    }


    @Test
    public void renderOrganisationProfile() throws Exception {

    }

    @Test
    public void serveFile() throws Exception {

    }

    @Test
    public void serveText_validName() throws Exception {
        Json jsonifiedOrganisation = new Json(organisation.toString());
        Json jsonifiedOrganisationFromDatabase = organisationProfileController.serveText("testOrg");

        assertEquals(jsonifiedOrganisation.value(), jsonifiedOrganisationFromDatabase.value());
    }

    @Test
    public void serveText_invalidName() throws Exception {
        Json invalidName = organisationProfileController.serveText("fakeName");
        Json expected = new Json("this organisation has not been registered yet");
        assertEquals(expected.value(), invalidName.value());

    }

    @Test
    public void saveText() throws Exception {

    }

    @Test
    public void saveImage() throws Exception {

    }

}