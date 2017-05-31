package com.codecool.volunti.repository;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.OrganisationVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationVideoRepository extends JpaRepository<OrganisationVideo, Integer> {

    List<OrganisationVideo> findByOrganisationId(Organisation organisation);

    List<OrganisationVideo> findAll();
}