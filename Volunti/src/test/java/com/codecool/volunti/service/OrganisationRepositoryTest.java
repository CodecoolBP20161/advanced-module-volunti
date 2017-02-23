package com.codecool.volunti.service;

import com.codecool.volunti.model.*;
import com.codecool.volunti.model.enums.*;
import com.codecool.volunti.repository.OrganisationRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.apache.tomcat.jdbc.pool.DataSource;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@Transactional
public class OrganisationRepositoryTest extends AbstractServiceTest {

    @Autowired
    private OrganisationRepository repository;
    private Organisation organisation;
    private Organisation organisation2;
    private ArrayList<SpokenLanguage> spokenLanguages;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrganisationRepository organisationRepository;


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp() {
        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        spokenLanguages.add(SpokenLanguage.ENGLISH);
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);

        organisation = new Organisation("Test 1", Category.TEACHING, Country.Hungary, "zipcode", "City", "Address", spokenLanguages, "Mission minimum 10 character", "Desc 1 min 3 character", "Desc 2 min 3 character");
        organisation2 = new Organisation("Test 2", Category.TEACHING, Country.Hungary, "zipcode", "City", "Address", spokenLanguages, "Mission minimum 10 character", "Desc 1 min 3 character", "Desc 2 min 3 character");


    }

    @Test
    public void testForTheFields() {

        organisation = this.repository.save(organisation);
        organisation = this.repository.findByName("Test 1");
        assertThat(organisation.getName()).isEqualTo("Test 1");
        assertThat(organisation.getCategory()).isEqualTo(Category.TEACHING);
        assertThat(organisation.getCountry()).isEqualTo(Country.Hungary);
        assertThat(organisation.getZipcode()).isEqualTo("zipcode");
        assertThat(organisation.getCity()).isEqualTo("City");
        assertThat(organisation.getAddress()).isEqualTo("Address");
        assertThat(organisation.getSpokenLanguage().toString()).isEqualTo("[ENGLISH, HUNGARIAN]");
        assertThat(organisation.getMission()).isEqualTo("Mission minimum 10 character");
        assertThat(organisation.getDescription1()).isEqualTo("Desc 1 min 3 character");
        assertThat(organisation.getDescription2()).isEqualTo("Desc 2 min 3 character");

    }

    @Test (expected = ConstraintViolationException.class)
    public void FirstFieldMissingTest(){

        organisation = new Organisation("", Category.TEACHING, Country.Hungary, "zipcode", "City", "Address", spokenLanguages, "Mission minimum 10 character", "Desc 1 min 3 character", "Desc 2 min 3 character");
        organisation = this.repository.save(organisation);

    }

    @Test (expected = ConstraintViolationException.class)
    public void CategoryIsNullTest(){

        organisation = new Organisation("Test 2", Category.TEACHING, null, "zipcode", "City", "Address", spokenLanguages, "Mission minimum 10 character", "Desc 1 min 3 character", "Desc 2 min 3 character");
        organisation = this.repository.save(organisation);

    }

    @Test
    public void addMoreOrganisation(){

        int countBefore = countRowsInTable("Organisation");
        this.organisationRepository.save(organisation);
        this.organisationRepository.save(organisation2);
        assertEquals(countBefore + 2, countRowsInTable("Organisation"));

    }

    protected int countRowsInTable(String tableName) {
        return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
    }

}