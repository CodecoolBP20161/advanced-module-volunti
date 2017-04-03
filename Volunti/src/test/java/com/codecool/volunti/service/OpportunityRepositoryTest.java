package com.codecool.volunti.service;

import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.Skill;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.SkillRepository;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@Transactional
public class OpportunityRepositoryTest extends AbstractServiceTest {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private SkillRepository skillRepository;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private Organisation organisation;
    private Opportunity opportunity;
    private Opportunity opportunity1;
    private Skill skill;


    @Before
    public void setUp() {

        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        spokenLanguages.add(SpokenLanguage.ENGLISH);
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);

        organisation = new Organisation("Test For Opps 1", Category.TEACHING, Country.HUNGARY, "zipcode","City", "Address", spokenLanguages, "Mission minimum 10 character", "Desc 1 min 3 character", "Desc 2 min 3 character");
        skill = new Skill("new Skill");
        List<Skill> skills = new ArrayList<>();
        skills.add(skill);

        opportunity = new Opportunity();
        opportunity.setOrganisation(organisation);
        opportunity.setTitle("First opportunity");
        opportunity.setNumberOfVolunteers(10);
        opportunity.setAccommodationType("Tent");
        opportunity.setFoodType("Vega");
        opportunity.setHoursExpected(3);
        opportunity.setHoursExpectedType(null);
        opportunity.setMinimumStayInDays(2);
        opportunity.setAvailabilityFrom(new java.sql.Date(2017 - 02 - 16));
        opportunity.setDateAvailabilityTo(new java.sql.Date(2017 - 02 - 21));
        opportunity.setCosts("free");
        opportunity.setRequirements("English");
        opportunity.setOpportunitySkills(skills);

        opportunity1 = new Opportunity();
        opportunity1.setOrganisation(organisation);
        opportunity1.setTitle("Second opportunity1");
        opportunity1.setNumberOfVolunteers(10);
        opportunity1.setAccommodationType("Tent");
        opportunity1.setFoodType("Vega");
        opportunity1.setHoursExpected(3);
        opportunity1.setHoursExpectedType(null);
        opportunity1.setMinimumStayInDays(2);
        opportunity1.setAvailabilityFrom(new java.sql.Date(2017 - 02 - 16));
        opportunity1.setDateAvailabilityTo(new java.sql.Date(2017 - 02 - 21));
        opportunity1.setCosts("free");
        opportunity1.setRequirements("English");
        opportunity1.setOpportunitySkills(skills);
     }

    @Test
    public void testForGetters() {
        this.opportunityRepository.save(opportunity);
        opportunity = this.opportunityRepository.findOne(201);
        assertThat(opportunity.getTitle()).isEqualTo("First opportunity");
        assertThat(opportunity.getAccommodationType()).isEqualTo("Tent");
        assertThat(opportunity.getFoodType()).isEqualTo("Vega");
        assertThat(opportunity.getHoursExpectedType()).isEqualTo(null);
        assertThat(opportunity.getCosts()).isEqualTo("free");
        assertThat(opportunity.getRequirements()).isEqualTo("English");
        assertThat(opportunity.getNumberOfVolunteers()).isEqualTo(10);
        assertThat(opportunity.getHoursExpected()).isEqualTo(3);
        assertThat(opportunity.getMinimumStayInDays()).isEqualTo(2);
        assertThat(opportunity.getAvailabilityFrom()).isEqualTo(new Timestamp(2017 - 02 - 16));
        assertThat(opportunity.getDateAvailabilityTo()).isEqualTo(new Timestamp(2017 - 02 - 21));
        assertThat(skill.getName()).isEqualTo("new Skill");
    }

    @Test(expected = ConstraintViolationException.class)
    public void titleFieldIsEmpty() {
        opportunity.setTitle("");
        this.opportunityRepository.save(opportunity);
    }

    @Test(expected = ConstraintViolationException.class)
    public void numberOfVolunteersFieldIsNull() {
        opportunity.setNumberOfVolunteers(null);
        this.opportunityRepository.save(opportunity);
    }

    @Test(expected = ConstraintViolationException.class)
    public void skillNameIsNull() {
        skill = new Skill(null);
        this.skillRepository.save(skill);
    }

    @Test(expected = ConstraintViolationException.class)
    public void numberOfVolunteersIsNegative() {
        opportunity.setNumberOfVolunteers(-1);
        this.opportunityRepository.save(opportunity);
    }

    @Test(expected = ConstraintViolationException.class)
    public void hoursExpectedIsNegative() {
        opportunity.setHoursExpected(-1);
        this.opportunityRepository.save(opportunity);
    }

    @Test(expected = ConstraintViolationException.class)
    public void minimumStayInDaysIsNegative() {
        opportunity.setMinimumStayInDays(-1);
        this.opportunityRepository.save(opportunity);
    }

    @Test
    public void addMoreSkill() {
        int countBefore = countRowsInTable("skills");
        this.skillRepository.save(skill);
        this.skillRepository.save(new Skill("second Skill"));
        assertEquals(countBefore + 2, countRowsInTable("skills"));
    }

    @Test
    public void addMoreOpportunity() {
        int countBefore = countRowsInTable("opportunities");
        int countSkillsBefore = countRowsInTable("skills");
        this.opportunityRepository.save(opportunity);
        this.opportunityRepository.save(opportunity1);
        assertEquals(countBefore + 2, countRowsInTable("opportunities"));
        assertEquals(countSkillsBefore, countRowsInTable("skills"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void titleSizeIsShorterThanRequired() {
        opportunity.setTitle("");
        this.opportunityRepository.save(opportunity);
    }

    @Test(expected = ConstraintViolationException.class)
    public void titleSizeIsLongerThanRequired() {
        opportunity.setTitle("First Skill First Skill First Skill First Skill First Skill First Skill " +
                "First Skill First Skill First Skill First Skill First Skill First Skill First Skill First Skill First Skill First Skill First Skill First Skill ");
        this.opportunityRepository.save(opportunity);
    }

    protected int countRowsInTable(String tableName) {
        return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
    }
}
