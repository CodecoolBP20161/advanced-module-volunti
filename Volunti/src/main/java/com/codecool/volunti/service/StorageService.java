package com.codecool.volunti.service;


import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

public interface StorageService {

    void deleteAll(Path rootLocation);

    void init(Path rootLocation);

    String store(File file, String fileName, Path rootLocation);

    String store(MultipartFile file, String fileName, Path rootLocation);

    Path load(String filename, Path rootLocation);

    Resource loadAsResource(String filename, Path rootLocation);

    void deleteOne(String filename, Path rootLocation);

}
