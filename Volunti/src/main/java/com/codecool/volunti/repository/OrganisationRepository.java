package com.codecool.volunti.repository;

import com.codecool.volunti.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends CrudRepository<Organisation, Integer> {
}
