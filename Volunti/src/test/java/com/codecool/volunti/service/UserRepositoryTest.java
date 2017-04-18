package com.codecool.volunti.service;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.repository.UserRepository;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


@Transactional
public class UserRepositoryTest extends AbstractServiceTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private JdbcTemplate jdbcTemplate;
    private Organisation organisation;
    private Volunteer volunteer;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp(){
        volunteer = mock(Volunteer.class);
        organisation = mock(Organisation.class);

        user1 = new User();
        user1.setFirstName("Test1 first name");
        user1.setLastName("Test1 last name");
        user1.setEmail("Email1");
        user1.setPassword("Password1");
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
        user1 = this.userRepository.save(user1);
        assertEquals("Test1 first name", user1.getFirstName());
        assertEquals("Test1 last name", user1.getLastName());
        assertEquals("Email1", user1.getEmail());
        assertEquals("Password1", user1.getPassword());
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
        user1 = this.userRepository.save(user1);
        user2 = this.userRepository.save(user2);
        assertEquals(countBefore + 2, countRowsInTable("users"));
    }

    protected int countRowsInTable(String tableName) {
        return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
    }
}
