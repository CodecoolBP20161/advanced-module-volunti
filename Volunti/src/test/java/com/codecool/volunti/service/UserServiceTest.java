package com.codecool.volunti.service;

import com.codecool.volunti.model.User;
import com.codecool.volunti.model.enums.UserStatus;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class UserServiceTest extends AbstractServiceTest {

    private Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);


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

    @Test
    public void getByActivationIDHappyPath() throws Exception {
        UUID testID = user1.getActivationID();
        LOGGER.info("testID: {}", testID);
        User testUser = userService.getByActivationID(testID.toString());
        LOGGER.info("testUser.activationID: {}", testUser.getActivationID());

        assertEquals(testID, testUser.getActivationID());
    }

    @Test (expected = IllegalArgumentException.class)
    public void getByActivationIDNoSuchID() throws Exception {
        userService.getByActivationID("fakeID");
    }

    @Test
    public void confirmRegistrationHappyPath() throws Exception {
        String testID = user1.getActivationID().toString();
        LOGGER.info("testID: {}", testID);
        userService.ConfirmRegistration(testID);
        User testUser = userService.getByActivationID(testID);
        LOGGER.info("testUser.userStatus: {}", testUser.getUserStatus());

        assertEquals(UserStatus.ACTIVE, testUser.getUserStatus());
    }

    @Test (expected = IllegalArgumentException.class)
    public void confirmRegistrationWrongIdFormat() throws Exception {
        userService.ConfirmRegistration("fakeID");
    }

    @Test
    public void confirmRegistrationNoSuchId() throws Exception {
        assertEquals(null, userService.ConfirmRegistration(UUID.randomUUID().toString()));
    }

    @Test
    public void confirmRegistrationUserIsAlreadyActive() throws Exception {
        String testID = user1.getActivationID().toString();
        userService.ConfirmRegistration(testID);
        User testUser = userService.getByActivationID(testID);
        LOGGER.info("enabled user status: {}", testUser.getUserStatus());

        assertEquals(null, userService.ConfirmRegistration(testID));
    }

    @Test
    public void confirmRegistrationUserIsDisabled() throws Exception {
        User user2 = new User("firstName2", "lastName2", "email2@email.com", "password2", null, null);
        String testID = user2.getActivationID().toString();
        user2.setUserStatus(UserStatus.DISABLED);
        LOGGER.info("disabled user status: {}", user2.getUserStatus());
        userService.saveUser(user2);

        assertEquals(null, userService.ConfirmRegistration(testID));
    }

}