package com.codecool.volunti.repository;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.OrganisationSocialLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationSocialLinkRepository extends JpaRepository<OrganisationSocialLink, Integer> {

    List<OrganisationSocialLink> findByOrganisationId(Organisation organisation);

    List<OrganisationSocialLink> findAll();

}
