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
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp() {
        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        spokenLanguages.add(SpokenLanguage.ENGLISH);
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);

        organisation = new Organisation();
        organisation.setName("Test 1");
        organisation.setCategory(Category.TEACHING);
        organisation.setCountry(Country.Hungary);
        organisation.setZipcode("zipcode");
        organisation.setCity("City");
        organisation.setAddress("Address");
        organisation.setSpokenLanguage(spokenLanguages);
        organisation.setMission("Mission minimum 10 character");
        organisation.setDescription1("Desc 1 min 3 character");
        organisation.setDescription2("Desc 2 min 3 character");

        organisation2 = new Organisation();
        organisation2.setName("Test 2");
        organisation2.setCategory(Category.TEACHING);
        organisation2.setCountry(Country.Hungary);
        organisation2.setZipcode("zipcode");
        organisation2.setCity("City");
        organisation2.setAddress("Address");
        organisation2.setSpokenLanguage(spokenLanguages);
        organisation2.setMission("Mission minimum 10 character");
        organisation2.setDescription1("Desc 1 min 3 character");
        organisation2.setDescription2("Desc 2 min 3 character");
        
    }

    @Test
    public void test_OrganisationFields_Should_Return_True() {

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
    public void test_OrganisationFirstFieldMissing_Should_Return_Exception(){

        organisation = new Organisation("", Category.TEACHING, Country.Hungary, "zipcode", "City", "Address", spokenLanguages, "Mission minimum 10 character", "Desc 1 min 3 character", "Desc 2 min 3 character");
        organisation = this.repository.save(organisation);

    }

    @Test (expected = ConstraintViolationException.class)
    public void test_OrganisationCategoryIsNull_Should_Return_Exception(){

        organisation = new Organisation("Test 2", Category.TEACHING, null, "zipcode", "City", "Address", spokenLanguages, "Mission minimum 10 character", "Desc 1 min 3 character", "Desc 2 min 3 character");
        organisation = this.repository.save(organisation);

    }

    @Test
    public void test_addMoreOrganisation_Should_Return_True(){

        int countBefore = countRowsInTable("organisations");
        this.repository.save(organisation);
        this.repository.save(organisation2);
        assertEquals(countBefore + 2, countRowsInTable("organisations"));

    }

    protected int countRowsInTable(String tableName) {
        return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
    }

}