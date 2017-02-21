package com.codecool.volunti.service;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class OrganisationService {


    private OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository){

        this.organisationRepository = organisationRepository;
    }

    public Organisation saveOrganisation(Organisation organisation){
        return organisationRepository.save(organisation);
    }

    public Organisation get(Integer id) {
        return organisationRepository.findOne(id);
    }

    public Organisation getByName(String name) {
        return organisationRepository.findByName(name);
    }

}
