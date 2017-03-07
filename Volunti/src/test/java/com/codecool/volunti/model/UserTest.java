package com.codecool.volunti.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserTest {

    private User testUser;

    @Before
    public void setUp() throws Exception {
        testUser = new User("firstName", "lastName", "email@mail.com", "password", null, null);
    }

    @Test
    public void test_UserId_Should_Return_True() {
        User user = new User();
        assertEquals( user.getId(), 0 );
    }
}
