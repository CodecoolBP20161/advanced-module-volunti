package com.codecool.volunti.service.model;


import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.OrganisationSocialLink;
import com.codecool.volunti.model.OrganisationVideo;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.OrganisationSocialLinkRepository;
import com.codecool.volunti.repository.OrganisationVideoRepository;
import com.codecool.volunti.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Slf4j
@Service
@Transactional
public class OrganisationService {

    private OrganisationRepository organisationRepository;
    private OrganisationSocialLinkRepository organisationSocialLinkRepository;
    private OrganisationVideoRepository organisationVideoRepository;
    private StorageService storageService;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository, StorageService storageService, OrganisationSocialLinkRepository organisationSocialLinkRepository, OrganisationVideoRepository organisationVideoRepository) {
        this.organisationRepository = organisationRepository;
        this.organisationVideoRepository = organisationVideoRepository;
        this.storageService = storageService;
        this.organisationSocialLinkRepository = organisationSocialLinkRepository;
    }

    public Organisation get(Integer id) {
        return organisationRepository.findOne(id);
    }

    public Organisation getByName(String name) {
        return organisationRepository.findByNameIgnoreCase(name);
    }

    public void saveProfilePicture(Organisation organisation) {
        String oldProfilePicture = organisation.getProfilePicture();
        String newFileName = storageService.store(organisation.getProfilePictureFileForSave());
        organisation.setProfilePicture(newFileName);
        storageService.deleteOne(oldProfilePicture);
    }

    public void saveBackgroundImage(Organisation organisation) {
        String oldBackgroundPicture = organisation.getBackgroundPicture();
        String newFileName = storageService.store(organisation.getBackgroundPictureFileForSave());
        organisation.setBackgroundPicture(newFileName);
        if(oldBackgroundPicture != null) storageService.deleteOne(oldBackgroundPicture);

    }

    public Organisation save(Organisation organisation) {
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

    public List<OrganisationVideo> findVideoByOrganisationId(Organisation organisation){
        return organisationVideoRepository.findByOrganisationId(organisation);
    }

    public List<OrganisationVideo> findAllVideo() {
        return organisationVideoRepository.findAll();
    }

    public OrganisationVideo save(OrganisationVideo organisationVideo) {
        return organisationVideoRepository.save(organisationVideo);
    }

    public void removeVideo(OrganisationVideo organisationVideo) {
        organisationVideoRepository.delete(organisationVideo);
    }

    public void updateOrganisationProfile(Organisation oldItem, Organisation newItem) {
        oldItem.setMission(newItem.getMission());
        oldItem.setDescription1(newItem.getDescription1());
        oldItem.setDescription2(newItem.getDescription2());
        oldItem.setName(newItem.getName());
        oldItem.setCategory(newItem.getCategory());
        oldItem.setCountry(newItem.getCountry());
        oldItem.setAddress(newItem.getAddress());
        oldItem.setCity(newItem.getCity());
        oldItem.setZipcode(newItem.getZipcode());
    }

//    public void setDefaultBackgroundImage(Organisation organisation) {
//        String staticPath = "src/main/resources/static/images/background_image/";
//        File testBackgroundImageFile = new File(staticPath + organisation.getCategory().name() + ".jpg" );
//        organisation.setBackgroundPictureFileForSave(testBackgroundImageFile);
//        save(organisation);
//    }

}