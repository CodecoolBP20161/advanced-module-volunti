package com.codecool.volunti.service;


import com.codecool.volunti.controller.exception_controller.StorageException;
import com.codecool.volunti.controller.exception_controller.StorageFileNotFoundException;
import com.codecool.volunti.model.Organisation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileSystemStorageService implements StorageService{

    private Path rootLocation = Paths.get("filestorage/profile_image/");

    @Override
    public String store(InputStreamSource file, String fileName) {

        deleteOne(fileName);
        String newFileName = UUID.randomUUID().toString();
        Path fileLocation = Paths.get( rootLocation.toString(), newFileName);
        log.info("store() method called...");
        log.info("route path" + fileLocation.toAbsolutePath());
        try {
            // TODO: fix, because it exists only for mulitpart files
            /*if (file == empty) {
                throw new StorageException("Failed to store empty file " + file.toString());
            }*/
            Files.copy(file.getInputStream(), fileLocation.toAbsolutePath());


        } catch (IOException e) {
            // e.printStackTrace();
            throw new StorageException("Failed to store file!", e);
        }

        return newFileName;
    }


    @Override
    public Path load(String filename) {

        return rootLocation.resolve(filename);
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void deleteOne(String fileName){
        log.info("deleteOne() menthod called ");
        Path fileLocation = Paths.get( rootLocation.toString(), fileName);
        try {
            Files.delete(fileLocation);
        } catch (IOException e) {
            e.printStackTrace();
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
            Path file = load(filename);
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
