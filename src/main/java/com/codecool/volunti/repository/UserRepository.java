package com.codecool.volunti.repository;

import com.codecool.volunti.model.User;
import com.codecool.volunti.model.enums.UserStatus;
import com.codecool.volunti.service.email.EmailType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByEmailAndUserStatus(String email, UserStatus userStatus);
    User findByActivationID(String activationID);

}
