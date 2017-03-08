package com.codecool.volunti.repository;

import com.codecool.volunti.model.Organisation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends CrudRepository<Organisation, Integer> {

    Organisation findByName(String name);
    Organisation findByOrganisationId(int organisationId);
    long count();

}
