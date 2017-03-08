package com.codecool.volunti.repository;

import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Integer> {

    List<Opportunity> findByOrganisationOrderByIdAsc(Organisation organisation);

}