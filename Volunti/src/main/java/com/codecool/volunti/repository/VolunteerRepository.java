package com.codecool.volunti.repository;


import com.codecool.volunti.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {
    long count();
}
