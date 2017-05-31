package com.codecool.volunti.service;

import com.codecool.volunti.model.User;
import com.codecool.volunti.model.enums.UserStatus;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.service.model.UserService;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private User user1;
    private User user2;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp() {
        user1 = new User("firstName", "lastName", "email@email.com", "password", null, null);
        userService.saveUser(user1);

        user2 = new User("firstName2", "lastName2", "email2@email.com", "password2", null, null);
    }

    @After
     public void tearDown() {
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        userRepository.delete(user1);
        userRepository.delete(user2);
    }

    @Test
    public void test_saveUser_shouldBeEqual() throws Exception {
        userService.saveUser(user2);
        assertEquals(user2.toString(), userService.getByEmail("email2@email.com").toString());
    }

    @Test
    public void test_getByEmailHappyPath_shouldBeEqual() throws Exception {
        User testUser = userService.getByEmail("email@email.com");
        assertEquals(user1, testUser);
    }

    @Test
    public void test_getByEmailNoUserFound_shouldReturnNull() throws Exception {
        User testUser = userService.getByEmail("fakemail@email.com");
        assertEquals(null, testUser);
    }

    @Test
    public void test_getByActivationIDHappyPath_shouldReturnNull() throws Exception {
        String testID = user1.getActivationID();
        User testUser = userService.getByActivationID(testID.toString());

        assertEquals(testID, testUser.getActivationID());
    }

    @Test
    public void test_getByActivationIDWrongIDFormat_shouldReturnNull() throws Exception {
        assertEquals(null, userService.getByActivationID("fakeID"));
    }

    @Test
    public void test_getByActivationIDNoSuchID_shouldReturnNull() throws Exception {
        assertEquals(null, userService.confirmRegistration(UUID.randomUUID().toString()));
    }

    @Test
    public void test_confirmRegistrationHappyPath_shouldBeActive() throws Exception {
        String testID = user1.getActivationID().toString();
        userService.confirmRegistration(testID);
        User testUser = userService.getByActivationID(testID);

        assertEquals(UserStatus.ACTIVE, testUser.getUserStatus());
    }

    @Test
    public void test_confirmRegistrationWrongIdFormat_shouldReturnNull() throws Exception {
        userService.confirmRegistration("fakeID");
        assertEquals(null, userService.confirmRegistration("fakeID"));

    }

    @Test
    public void test_confirmRegistrationNoSuchId_shouldReturnNull() throws Exception {
        assertEquals(null, userService.confirmRegistration(UUID.randomUUID().toString()));
    }

    @Test
    public void test_confirmRegistration_userIsAlreadyActive_shouldReturnNull() throws Exception {
        String testID = user1.getActivationID().toString();
        userService.confirmRegistration(testID);
        User testUser = userService.getByActivationID(testID);
        assertEquals(null, userService.confirmRegistration(testID));
    }

    @Test
    public void test_confirmRegistrationUserIsDisabled_shouldReturnNull() throws Exception {
        user2 = new User("firstName2", "lastName2", "email2@email.com", "password2", null, null);
        String testID = user2.getActivationID().toString();
        user2.setUserStatus(UserStatus.DISABLED);
        userService.saveUser(user2);
        assertEquals(null, userService.confirmRegistration(testID));
    }

}