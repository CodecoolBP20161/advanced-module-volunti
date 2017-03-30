package com.codecool.volunti.service.model;


import com.codecool.volunti.model.OrganisationSocialLink;
import com.codecool.volunti.repository.OrganisationSocialLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrganisationSocialLinkService {

    private OrganisationSocialLinkRepository organisationSocialLinkRepository;

    @Autowired
    public OrganisationSocialLinkService(OrganisationSocialLinkRepository organisationSocialLinkRepository) {
        this.organisationSocialLinkRepository = organisationSocialLinkRepository;
    }


    public OrganisationSocialLink save(OrganisationSocialLink organisationSocialLink) {
        return organisationSocialLinkRepository.save(organisationSocialLink);
    }

    public OrganisationSocialLink findByOrganisationId(Integer organisationId){
        return organisationSocialLinkRepository.findByOrganisationId(organisationId);
    }

    public List<OrganisationSocialLink> findAll() {
        return organisationSocialLinkRepository.findAll();
    }


}
