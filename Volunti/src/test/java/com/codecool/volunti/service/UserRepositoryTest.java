package com.codecool.volunti.service;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.repository.VolunteerRepository;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@Transactional
public class UserRepositoryTest extends AbstractServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    private User user1;
    private User user2;
    private JdbcTemplate jdbcTemplate;
    private Organisation organisation;
    private Volunteer volunteer;
    private ArrayList<SpokenLanguage> spokenLanguages;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Before
    public void setUp(){

        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        spokenLanguages.add(SpokenLanguage.ENGLISH);
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);

        organisation = new Organisation("Test 1", Category.TEACHING, Country.HUNGARY, "zipcode", "City", "Address", spokenLanguages, "Mission minimum 10 character", "Desc 1 min 3 character", "Desc 2 min 3 character");
        volunteer = new Volunteer();

        user1 = new User();
        user1.setFirstName("Test1 first name");
        user1.setLastName("Test1 last name");
        user1.setEmail("Email1");
        user1.setPassword("Password1");
        //user1.setSalt("salt1");
        user1.setOrganisation(organisation);
        user1.setVolunteer(volunteer);

        user2 = new User();
        user2.setFirstName("Test2 first name");
        user2.setLastName("Test2 last name");
        user2.setEmail("Email2");
        user2.setPassword("Password2");
        user2.setOrganisation(organisation);
        user2.setVolunteer(volunteer);

    }

    @Test
    public void test_UserFields_Should_Return_True(){

        organisation = this.organisationRepository.save(organisation);
        volunteer = this.volunteerRepository.save(volunteer);

        user1 = this.userRepository.save(user1);
        assertEquals("Test1 first name", user1.getFirstName());
        assertEquals("Test1 last name", user1.getLastName());
        assertEquals("Email1", user1.getEmail());
        assertEquals("Password1", user1.getPassword());
        //assertEquals("salt1", user1.getSalt());
        assertEquals(organisation, user1.getOrganisation());
        assertEquals(volunteer, user1.getVolunteer());
    }

    @Test (expected = ConstraintViolationException.class)
    public void test_UserFirstFieldMissing_Should_Return_Exception(){
        user1 = new User("", "Test1 last name", "Email1", "Password1", organisation, volunteer);
        this.userRepository.save(user1);
    }

    @Test (expected = ConstraintViolationException.class)
    public void test_UserCategoryIsNull_Should_Return_Exception(){
        user1 = new User("Test1 first name", null, "Email1", "Password1", organisation, volunteer);
        this.userRepository.save(user1);
    }

    @Test
    public void test_addMoreOrganisation_Should_Return_True(){

        int countBefore = countRowsInTable("users");

        organisation = this.organisationRepository.save(organisation);
        volunteer = this.volunteerRepository.save(volunteer);

        user1 = this.userRepository.save(user1);
        user2 = this.userRepository.save(user2);
        assertEquals(countBefore + 2, countRowsInTable("users"));

    }

    protected int countRowsInTable(String tableName) {
        return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
    }



}
