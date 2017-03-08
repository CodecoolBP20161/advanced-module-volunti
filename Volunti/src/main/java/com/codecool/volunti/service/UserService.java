package com.codecool.volunti.service;


import com.codecool.volunti.model.User;
import com.codecool.volunti.model.enums.UserStatus;
import com.codecool.volunti.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;


@Slf4j
@Service
@Transactional
public class UserService {


    private UserRepository userRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User saveUser(User user) {
        log.debug("saving user");
        return userRepository.save(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User getByActivationID(String activationID) {
        try{
            log.info("UUID before convertion: " + activationID);
            //UUID activationUUID = UUID.fromString(activationID);
            return userRepository.findByActivationID(activationID);
        } catch (IllegalArgumentException e){
            log.error("Failed to convert String to UUID, null is returned.");
            return null;
        }
    }

    public User confirmRegistration(String activationID) {
        log.info("The confirmation of a registration has been started...");
        User newUser = getByActivationID(activationID);
        if (newUser == null) {
            log.warn("Activation ID cannot be found in the database.");
            return null;
        } else {
            if (newUser.getUserStatus().equals(UserStatus.INACTIVE)) {
                newUser.setUserStatus(UserStatus.ACTIVE);
                log.info("UserStatus changed to ACTIVE.");
                return this.saveUser(newUser);
            } else if (newUser.getUserStatus().equals(UserStatus.ACTIVE)) {

                log.warn("UserStatus is already ACTIVE.");
                return newUser; //TODO: Decide if we should let the user log in(return the user), or not(return null)
            } else if (newUser.getUserStatus().equals(UserStatus.DISABLED)) {
                log.warn("UserStatus is DISABLED.");
                return null;
            } else {
                throw new NotImplementedException();
            }

        }
    }

    public User handlePasswordActivationID(String activationID) {
        log.info("forgetPassword activationID check is started.");
        User newUser = getByActivationID(activationID);
        if (newUser == null) {
            log.warn("Activation ID cannot be found in the database.");
            return null;
        } else {
            if (newUser.getUserStatus().equals(UserStatus.DISABLED)) {
                log.warn("The requested User's status is DISABLED. Null is returned.");
                return null;

            } else {
                return newUser;
            }

        }
    }
}
