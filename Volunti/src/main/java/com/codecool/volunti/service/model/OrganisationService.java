package com.codecool.volunti.service.model;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.OrganisationSocialLink;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.OrganisationSocialLinkRepository;
import com.codecool.volunti.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class OrganisationService {

    private OrganisationRepository organisationRepository;
    private OrganisationSocialLinkRepository organisationSocialLinkRepository;
    private StorageService storageService;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository, StorageService storageService, OrganisationSocialLinkRepository organisationSocialLinkRepository) {
        this.organisationRepository = organisationRepository;
        this.storageService = storageService;
        this.organisationSocialLinkRepository = organisationSocialLinkRepository;
    }

    public Organisation get(Integer id) {
        return organisationRepository.findOne(id);
    }

    public Organisation getByName(String name) {
        return organisationRepository.findByNameIgnoreCase(name);
    }

    public Organisation save(Organisation organisation) {
        if (organisation.getProfilePictureFileForSave() != null ) {

            String oldProfilePicture = organisation.getProfilePicture();
            String newFileName = storageService.store(organisation.getProfilePictureFileForSave());
            organisation.setProfilePicture(newFileName);
            storageService.deleteOne(oldProfilePicture);

        }
        if (organisation.getBackgroundPictureFileForSave() != null ) {

            String oldBackgroundPicture = organisation.getBackgroundPicture();
            String newFileName = storageService.store(organisation.getBackgroundPictureFileForSave());
            organisation.setBackgroundPicture(newFileName);
            storageService.deleteOne(oldBackgroundPicture);

        }
        return organisationRepository.save(organisation);
    }


    public Resource loadProfilePicture(Organisation organisation) {
        return storageService.loadAsResource(organisation.getProfilePicture());
    }

    public Resource loadBackgroundPicture(Organisation organisation) {
        return storageService.loadAsResource(organisation.getBackgroundPicture());
    }

    public OrganisationSocialLink save(OrganisationSocialLink organisationSocialLink) {
        return organisationSocialLinkRepository.save(organisationSocialLink);
    }

    public List<OrganisationSocialLink> findByOrganisationId(Organisation organisation){
        return organisationSocialLinkRepository.findByOrganisationId(organisation);
    }

    public List<OrganisationSocialLink> findAll() {
        return organisationSocialLinkRepository.findAll();
    }


}
