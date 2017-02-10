package com.codecool.volunti.repository;


import com.codecool.volunti.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface VolunteerRepository extends CrudRepository<Volunteer, Integer> {
}
