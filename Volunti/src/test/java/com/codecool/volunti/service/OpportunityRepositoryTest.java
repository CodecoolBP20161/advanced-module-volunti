package com.codecool.volunti.service;

import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.Skill;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.repository.OpportunityRepository;
import com.codecool.volunti.repository.OrganisationRepository;
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
    private OrganisationRepository organisationRepository;

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

        organisation = new Organisation("Test For Opps 1", Category.TEACHING, "Country", "zipcode","City", "Address", spokenLanguages, "Mission minimum 10 character", "Desc 1 min 3 character", "Desc 2 min 3 character");
        skill = new Skill("new Skill");
        List<Skill> skills = new ArrayList<>();
        skills.add(skill);
        opportunity = new Opportunity(organisation, "First opportunity", 10, "Tent",
                "Vega", 3, "none", 2,
                new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English", skills);

        opportunity1 = new Opportunity(organisation, "Second opportunity", 10, "Tent",
                "Vega", 3, "none", 2,
                new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English", skills);
    }


    @Test
    public void testSkill() {

    }


    @Test
    public void testForGetters() {
        this.opportunityRepository.save(opportunity);
        opportunity = this.opportunityRepository.findOne(1);
        assertThat(opportunity.getTitle()).isEqualTo("First opportunity");
        assertThat(opportunity.getAccommodationType()).isEqualTo("Tent");
        assertThat(opportunity.getFoodType()).isEqualTo("Vega");
        assertThat(opportunity.getHoursExpectedType()).isEqualTo("none");
        assertThat(opportunity.getCosts()).isEqualTo("free");
        assertThat(opportunity.getRequirements()).isEqualTo("English");
        assertThat(opportunity.getNumberOfVolunteers()).isEqualTo(10);
        assertThat(opportunity.getHoursExpected()).isEqualTo(3);
        assertThat(opportunity.getMinimumStayInDays()).isEqualTo(2);
        assertThat(opportunity.getAvailabilityFrom()).isEqualTo(new Timestamp(2017 - 02 - 16));
        assertThat(opportunity.getDateAvailabilityTo()).isEqualTo(new Timestamp(2017 - 02 - 21));
        assertThat(skill.getName()).isEqualTo("new Skill");

        System.out.println(opportunity.getOpportunitySkills());
    }

    @Test(expected = ConstraintViolationException.class)
    public void titleFieldIsEmpty() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("First Skill"));
        opportunity = new Opportunity(organisation, "", 10, "Tent",
                "Vega", 3, "none", 2,
                new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English", skills);
        this.opportunityRepository.save(opportunity);
    }

    @Test(expected = ConstraintViolationException.class)
    public void numberOfVolunteersFieldIsNull() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("First Skill"));
        opportunity = new Opportunity(organisation, "First opportunity", null, "Tent",
                "Vega", 3, "none", 2,
                new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English", skills);
        this.opportunityRepository.save(opportunity);
    }

    @Test(expected = ConstraintViolationException.class)
    public void skillNameIsNull() {
        skill = new Skill(null);
        this.skillRepository.save(skill);
    }

    @Test
    public void addMoreSkill() {
        int countBefore = countRowsInTable("skills");
        this.skillRepository.save(skill);
        this.skillRepository.save(new Skill("second Skill"));
        assertEquals(countRowsInTable("skills"), countBefore + 2);
    }

    @Test
    public void addMoreOpportunity() {
        int countBefore = countRowsInTable("opportunities");
        this.opportunityRepository.save(opportunity);
        this.opportunityRepository.save(opportunity1);
        assertEquals(countRowsInTable("opportunities"), countBefore + 2);
        assertEquals(countRowsInTable("skills"), 1);
    }

    protected int countRowsInTable(String tableName) {
        return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
    }
}
