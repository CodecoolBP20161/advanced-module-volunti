package com.codecool.volunti.service;


import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {

    void deleteAll();

    void init();

    String store(File file);

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteOne(String filename);

    File createTemp(MultipartFile file);

}
