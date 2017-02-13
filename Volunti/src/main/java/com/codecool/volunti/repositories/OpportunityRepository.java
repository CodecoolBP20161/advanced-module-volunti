package com.codecool.volunti.repositories;

import com.codecool.volunti.models.Opportunity;
import org.springframework.data.repository.CrudRepository;


public interface OpportunityRepository extends CrudRepository<Opportunity, Integer> {
}
