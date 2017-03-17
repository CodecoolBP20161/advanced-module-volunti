package com.codecool.volunti.service;


import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;

import java.nio.file.Path;

public interface StorageService {

    void deleteAll(Path rootLocation);

    void init(Path rootLocation);

    String store(InputStreamSource file, String fileName, Path rootLocation);

    Path load(String filename, Path rootLocation);

    Resource loadAsResource(String filename, Path rootLocation);

    void deleteOne(String filename, Path rootLocation);

}
