package com.codecool.volunti.service;


import com.codecool.volunti.model.Organisation;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file, Organisation organisation);

    Path load(String filename);

}
