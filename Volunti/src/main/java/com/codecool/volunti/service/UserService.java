package com.codecool.volunti.service;


import com.codecool.volunti.model.User;
import com.codecool.volunti.model.enums.UserStatus;
import com.codecool.volunti.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.UUID;


@Service
public class UserService {

    private Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public User saveUser(User user) {

        LOGGER.debug("saving user");
        return userRepository.save(user);

    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User getByActivationID(String activationID) {
        try{
            LOGGER.info("UUID before convertion: " + activationID);
            //UUID activationUUID = UUID.fromString(activationID);
            return userRepository.findByActivationID(activationID);
        } catch (IllegalArgumentException e){
            LOGGER.error("Failed to convert String to UUID, null is returned.");
            return null;
        }

    }

    public User confirmRegistration(String activationID) {
        LOGGER.info("The confirmation of a registration has been started...");
        User newUser = getByActivationID(activationID);
        if (newUser == null) {
            LOGGER.warn("Activation ID cannot be found in the database.");
            return null;
        } else {
            if (newUser.getUserStatus().equals(UserStatus.INACTIVE)) {
                newUser.setUserStatus(UserStatus.ACTIVE);
                LOGGER.info("UserStatus changed to ACTIVE.");
                return this.saveUser(newUser);
            } else if (newUser.getUserStatus().equals(UserStatus.ACTIVE)) {
                LOGGER.warn("UserStatus is already ACTIVE.");
                return newUser; //TODO: Decide if we should let the user log in(return the user), or not(return null)
            } else if (newUser.getUserStatus().equals(UserStatus.DISABLED)) {
                LOGGER.warn("UserStatus is DISABLED.");
                return null;
            } else {
                throw new NotImplementedException();
            }

        }
    }
    public User handlePasswordActivationID(String activationID) {
        LOGGER.info("forgetPassword activationID check is started.");
        User newUser = getByActivationID(activationID);
        if (newUser == null) {
            LOGGER.warn("Activation ID cannot be found in the database.");
            return null;
        } else {
            if (newUser.getUserStatus().equals(UserStatus.DISABLED)) {
                LOGGER.warn("The requested User's status is DISABLED. Null is returned.");
                return null;

            } else {
                return newUser;
            }

        }
    }


}
