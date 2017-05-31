package com.codecool.volunti.service;


import com.codecool.volunti.controller.exception_controller.StorageException;
import com.codecool.volunti.controller.exception_controller.StorageFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileSystemStorageService implements StorageService{

    private Path rootLocation = Paths.get("filestorage/");

    @Override
    public String store(File file) {

        String newFileName = UUID.randomUUID().toString();
        Path fileLocation = Paths.get( rootLocation.toString(), newFileName);
        log.info("store() method called...");
        log.info("route path" + fileLocation.toAbsolutePath());

        try {
            if (file.length() == 0) {
                throw new StorageException("Failed to store empty file " + file.toString());
            }
            Files.copy(file.toPath(), fileLocation.toAbsolutePath());


        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("Failed to store file!", e);
        }

        return newFileName;
    }

    public File createTemp(MultipartFile file) {

        String newFileName = UUID.randomUUID().toString();
        Path fileLocation = Paths.get( rootLocation.toString(), newFileName);
        File convFile = new File(fileLocation.toString());
        try{
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(fileLocation.toString())));
            stream.write(bytes);
            stream.close();
        }catch (IOException e){
            throw new StorageException("Failed to store in the temporary place", e);
        }


        return convFile;
    }


    @Override
    public Path load(String filename) {

        return rootLocation.resolve(filename);
    }

    public Path loadFromResources(String filename) {
        return Paths.get("src/main/resources/static/images/").resolve(filename);
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void deleteOne(String fileName) {
        log.info(rootLocation.toString());
        log.info("deleteOne() menthod called ");
        Path fileLocation = Paths.get( rootLocation.toString(), fileName);

        try {
            Files.delete(fileLocation);
        } catch (IOException e) {
        }

    }

    @Override
    public void init() {
        try {
            log.info("it is in the init method");
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file;
            if(filename.contains("image")) {
                file = loadFromResources(filename);
            } else {
                file = load(filename);
            }
            log.info(file.toAbsolutePath().toString());
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }
}