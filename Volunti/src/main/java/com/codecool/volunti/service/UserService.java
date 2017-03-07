package com.codecool.volunti.service;


import com.codecool.volunti.model.Role;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.enums.UserStatus;
import com.codecool.volunti.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.codecool.volunti.model.enums.RoleEnum.ROLE_USER;

@Slf4j
@Service
@Transactional
public class UserService {


    public User saveUser(User user) {
        log.debug("saving user");
        return userRepository.save(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User getByActivationID(String activationID) {
        try{
            UUID activationUUID = UUID.fromString(activationID);
            return userRepository.findByActivationID(activationUUID);
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
                return null; //TODO: Decide if we should let the user log in(return the user), or not(return null)
            } else if (newUser.getUserStatus().equals(UserStatus.DISABLED)) {
                log.warn("UserStatus is DISABLED.");
                return null;
            } else {
                throw new NotImplementedException();
            }

        }
    }

    @Autowired
    RoleService roleService;

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void save(User user) {
        userRepository.save(user);
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

    /*
    @Transactional
    public User createNewUser(User user) {
        User newUser = new User(user.getFirstName(), user.getLastName(), user.getEmail(), passwordEncoder.encode(user.getPassword()), );
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName(ROLE_USER.getRole()));
        newUser.setRoles(roles);
        save(newUser);
        return newUser;
    }
    */
}
