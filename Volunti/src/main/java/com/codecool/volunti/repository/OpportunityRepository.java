package com.codecool.volunti.repository;

import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Integer> {

    List<Opportunity> findByOrganisationOrderByIdAsc(Organisation organisation);

    long count();

}