package com.codecool.volunti.service;

import com.codecool.volunti.model.User;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Objects;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;
    private User user1;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp() {
        user1 = new User("firstName", "lastName", "email@email.com", "password", "salt");
        userService.saveUser(user1);
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "\"user\"");
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