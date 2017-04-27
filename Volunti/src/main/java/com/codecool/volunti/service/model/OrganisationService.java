package com.codecool.volunti.service.model;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrganisationService {


    private OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository){
        this.organisationRepository = organisationRepository;
    }

    public Organisation saveOrganisation(Organisation organisation){
        return organisationRepository.save(organisation);
    }
    public void save(Organisation organisation) {
        organisationRepository.save(organisation);
    }

    public Organisation get(Integer id) {
        return organisationRepository.findOne(id);
    }

    public Organisation getByName(String name) {
        return organisationRepository.findByNameIgnoreCase(name);
    }

    public Set<String> getCategories() {
        return organisationRepository.findAll().stream().map(o -> o.getCategory().name()).collect(Collectors.toSet());
    }

    public Set<Country> getLocations() {
        return organisationRepository.findAll().stream().map(Organisation::getCountry).collect(Collectors.toSet());
    }

}
