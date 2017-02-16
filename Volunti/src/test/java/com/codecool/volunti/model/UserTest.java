package com.codecool.volunti.model;

import lombok.Data;
import com.codecool.volunti.model.User;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testUserStatus() {
        User user = new User();
        assertEquals( user.getId(), 0 );
        System.out.println(user);
    }
}
