package com.codecool.volunti.service;

import com.codecool.volunti.model.User;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import static junit.framework.TestCase.assertEquals;

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
        user1 = new User("firstName", "lastName", "email@email.com", "password", null, null);
        userService.saveUser(user1);
    }

    @After
     public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }
        
    @Test
    public void saveUser() throws Exception {
        User user2 = new User("firstName2", "lastName2", "email2@email.com", "password2", null, null);
        userService.saveUser(user2);

        assertEquals(user2, userService.getByEmail("email2@email.com"));
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
}