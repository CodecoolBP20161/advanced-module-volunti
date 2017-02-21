package com.codecool.volunti.service;


import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    @Autowired
    public UserService( UserRepository userRepository){

        this.userRepository = userRepository;
    }

    public User saveUser( User user){
        LOGGER.debug("saving user");
        return userRepository.save(encodePassword(user));
    }


    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private User encodePassword(User user){
        String password = user.getPassword();
        LOGGER.debug("password before hashing: {}", password);

        String salt = BCrypt.gensalt();
        LOGGER.debug("salt before hashing: {}", salt);

        String encodedPassword = BCrypt.hashpw(password, salt);
        LOGGER.debug("password after hashing: {}", encodedPassword);

        user.setSalt(salt);
        user.setPassword(encodedPassword);

        return user;

    }
}
