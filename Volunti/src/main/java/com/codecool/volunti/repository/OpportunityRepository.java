package com.codecool.volunti.repository;

import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OpportunityRepository extends CrudRepository<Opportunity, Integer> {

    List<Opportunity> findByOrganisation(Organisation organisation);
//    void update(Opportunity opportunityNew, Opportunity opportunityOld, Organisation organisation);
}
