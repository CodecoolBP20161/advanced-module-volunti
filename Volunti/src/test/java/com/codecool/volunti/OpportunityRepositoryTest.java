package com.codecool.volunti;

import com.codecool.volunti.models.Opportunity;
import com.codecool.volunti.models.Organisation;
import com.codecool.volunti.models.Skill;
import com.codecool.volunti.repositories.OpportunityRepository;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OpportunityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OpportunityRepository repository;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private Organisation organisation;
    private Opportunity opportunity;
    private Opportunity opportunity1;

    @Before
    public void setUp() {
        organisation = new Organisation("Something");
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("new Skill"));
        opportunity = new Opportunity(organisation, "First opportunity", 10, "Tent",
                "Vega", 3, "none", 2,
                new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English", skills);

        opportunity1 = new Opportunity(organisation, "First opportunity", 10, "Tent",
                "Vega", 3, "none", 2,
                new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English", skills);
    }


    @After
    public void tearDown() {
        opportunity = null;
        opportunity1 = null;
        organisation = null;
    }


    @Test
    public void testForGetters() {
        this.entityManager.persist(opportunity);
        assertThat(opportunity.getTitle()).isEqualTo("First opportunity");
        assertThat(opportunity.getAccommodationType()).isEqualTo("Tent");
        assertThat(opportunity.getFoodType()).isEqualTo("Vega");
        assertThat(opportunity.getHoursExpectedType()).isEqualTo("none");
        assertThat(opportunity.getCosts()).isEqualTo("free");
        assertThat(opportunity.getRequirements()).isEqualTo("English");
        assertThat(opportunity.getNumberOfVolunteers()).isEqualTo(10);
        assertThat(opportunity.getHoursExpected()).isEqualTo(3);
        assertThat(opportunity.getMinimumStayInDays()).isEqualTo(2);
        assertThat(opportunity.getAvailabilityFrom()).isEqualTo(new Date(2017 - 02 - 16));
        assertThat(opportunity.getDateAvailabilityTo()).isEqualTo(new Date(2017 - 02 - 21));
    }

    @Test(expected = ConstraintViolationException.class)
    public void titleFieldIsEmpty() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("First Skill"));
        opportunity = new Opportunity(organisation, "", 10, "Tent",
                "Vega", 3, "none", 2,
                new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English", skills);
        this.entityManager.persist(opportunity);
    }

    @Test(expected = ConstraintViolationException.class)
    public void numberOfVolunteersFieldIsNull() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("First Skill"));
        opportunity = new Opportunity(organisation, "First opportunity", null, "Tent",
                "Vega", 3, "none", 2,
                new java.sql.Date(2017 - 02 - 16), new java.sql.Date(2017 - 02 - 21), "free", "English", skills);
        this.entityManager.persist(opportunity);
    }

    @Test
    public void addMorOpportunity() throws Exception {
        repository.save(opportunity);
        repository.save(opportunity1);
        assertEquals(countRowsInTable("opportunities"), 2);
    }

    protected int countRowsInTable(String tableName) {
        return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
    }
}
