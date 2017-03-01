package com.codecool.volunti.model;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserTest {

    private Logger LOGGER = LoggerFactory.getLogger(UserTest.class);

    private User testUser;

    @Before
    public void setUp() throws Exception {
        testUser = new User("firstName", "lastName", "email@mail.com", "password", null, null);
    }

    @Test
    public void setSalt() throws Exception {
        LOGGER.info("salt: {}", testUser.getSalt());
        assertNotNull(testUser.getSalt());
    }

    @Test
    public void hashPassword() throws Exception {
        LOGGER.info("password before hashing: {}", testUser.getPassword());
        testUser.hashPassword(testUser.getPassword());
        LOGGER.info("password after hashing: {}", testUser.getPassword());
        assertEquals(true, BCrypt.checkpw("password", testUser.getPassword()));
    }

    @Test
    public void testUserStatus() {
        User user = new User();
        assertEquals( user.getId(), 0 );
        System.out.println(user);
    }
}
