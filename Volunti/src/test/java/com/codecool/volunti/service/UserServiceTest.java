package com.codecool.volunti.service;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.SpokenLanguage;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.ArrayList;
import java.util.Objects;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;
    private User user1;
    private JdbcTemplate jdbcTemplate;
    private Organisation organisation;
    private Volunteer volunteer;
    private ArrayList<SpokenLanguage> spokenLanguages;

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
        volunteer = new Volunteer();
        user1 = new User("firstName", "lastName", "email@email.com", "password", "salt", organisation, volunteer);
        userService.saveUser(user1);
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void saveUser() throws Exception {
        userService.saveUser(user1);
    }

    @Test
    public void getByEmailHappyPath() throws Exception {
        User testUser = userService.getByEmail("email@email.com");
        assertEquals(testUser, user1);
    }

    @Test
    public void getByEmailNoUserFound() throws Exception {
        User testUser = userService.getByEmail("fakemail@email.com");
        assertEquals(testUser, null);
    }

    @Test
    public void testGeneratingSalt() throws Exception {
        User testUser = userService.getByEmail("email@email.com");
        assertFalse(Objects.equals(testUser.getSalt(), "salt"));
    }

    @Test
    public void testPasswordHashing() throws Exception {
        User testUser = userService.getByEmail("email@email.com");
        String testPassword = BCrypt.hashpw("password", testUser.getSalt());

        assertEquals(testUser.getPassword(), testPassword);
    }

}