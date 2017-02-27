package com.codecool.volunti.repository;

import com.codecool.volunti.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String Email);
    User findByActivationID(UUID activationID);

}
