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
        if (file.getContentType().endsWith("png") || file.getContentType().endsWith("jpeg") && !file.isEmpty()) {
            log.info("this image is png or jpeg");
            return file;
        } else {
            log.info("this image isn't png or jpeg");
            return file;
        }
    }

    public File resize(File origImg, int newW, int newH) throws IOException {

        BufferedImage img = ImageIO.read(origImg);

        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        File convFile2 = new File(String.valueOf(dimg));
        ImageIO.write(dimg, "jpg", convFile2);

        return convFile2;
    }

}
