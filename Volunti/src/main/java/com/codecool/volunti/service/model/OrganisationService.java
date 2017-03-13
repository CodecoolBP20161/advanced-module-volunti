package com.codecool.volunti.service.model;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrganisationService {

    private OrganisationRepository organisationRepository;
    private StorageService storageService;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository, StorageService storageService) {
        this.organisationRepository = organisationRepository;
        this.storageService = storageService;
    }

    public Organisation save(Organisation organisation) {
        if (organisation.getProfilePictureFileForSave() != null ) {
            String newFileName = storageService.store(organisation.getProfilePictureFileForSave());
            organisation.setProfilePicture(newFileName);
        }
        return organisationRepository.save(organisation);
    }

    public Organisation get(Integer id) {
        return organisationRepository.findOne(id);
    }

    public Organisation getByName(String name) {
        return organisationRepository.findByNameIgnoreCase(name);
    }

    public Resource loadProfilePicture(Organisation organisation) {
        return storageService.loadAsResource(organisation.getProfilePicture());
    }
}
