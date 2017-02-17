package com.codecool.volunti.repository;

import com.codecool.volunti.model.Opportunity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OpportunityRepository extends CrudRepository<Opportunity, Integer> {

}
