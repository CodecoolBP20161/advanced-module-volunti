package com.codecool.volunti.service;


import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService( UserRepository userRepository){

        this.userRepository = userRepository;
    }

    public User saveUser( User user){

        return userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
