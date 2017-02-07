package com.codecool.volunti.model.repository;


import com.codecool.volunti.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {
}
