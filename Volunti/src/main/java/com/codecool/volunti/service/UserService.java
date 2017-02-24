package com.codecool.volunti.service;


import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        return userRepository.save(user);
    }


    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
