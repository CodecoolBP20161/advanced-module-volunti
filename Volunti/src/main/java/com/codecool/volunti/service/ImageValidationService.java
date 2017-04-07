package com.codecool.volunti.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@Slf4j
public class ImageValidationService {

    public MultipartFile imageTypeValidator(MultipartFile file) {
        if (file.getContentType().endsWith("png") || file.getContentType().endsWith("jpeg") && !file.isEmpty()) {
            log.info("this image is png or jpeg");
            return file;
        } else {
            log.info("this image isn't png or jpeg");
            return file;
        }
    }

}
