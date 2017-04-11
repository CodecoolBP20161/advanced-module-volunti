package com.codecool.volunti.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class ImageValidationService {

    public MultipartFile imageTypeValidator(MultipartFile file) {
        log.info("imageTypeValidator() method called");

        if (file.getContentType().endsWith("png") || file.getContentType().endsWith("jpeg") && !file.isEmpty()) {
            log.info("this image is png or jpeg");
            return file;
        } else {
            log.info("this image isn't png or jpeg");
            return file;
        }
    }

    public BufferedImage resize(File origImg, int newW, int newH) throws IOException {
        log.info("resizing method() called...");

        BufferedImage img = ImageIO.read(origImg);

        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_DEFAULT);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

}
