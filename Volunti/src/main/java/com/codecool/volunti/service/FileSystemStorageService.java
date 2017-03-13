package com.codecool.volunti.service;


import com.codecool.volunti.controller.exception_controller.StorageException;
import com.codecool.volunti.controller.exception_controller.StorageFileNotFoundException;
import com.codecool.volunti.model.Organisation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
@Service
public class FileSystemStorageService implements StorageService{

    private Path rootLocation = Paths.get("resources/static/images/profile_image/");


    @Override
    public void store(MultipartFile file, Organisation organisation) {

        rootLocation = Paths.get("resources/static/images/profile_image/" + ((Integer) organisation.getOrganisationId()).toString() );

        log.info("store() method called...");
        log.info("route path" + rootLocation.toAbsolutePath());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.toAbsolutePath());

        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }


    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }



    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
