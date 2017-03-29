package com.codecool.volunti.service;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

@Data
public class EmailProperties {

    private Logger LOGGER = LoggerFactory.getLogger(EmailProperties.class);
    private String propertiesFileName;
    private HashMap<String, String> emailPropertiesContainer = new HashMap<>();
    private InputStream inputStream;

    public HashMap<String, String> getPropValues(EmailType emailType) {

        try {
            Properties prop = new Properties();
            propertiesFileName = (emailType.toString()).toLowerCase() +"Email.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            }
            emailPropertiesContainer.put("subject", prop.getProperty("subject"));
            emailPropertiesContainer.put("text1", prop.getProperty("text1"));
            emailPropertiesContainer.put("text2", prop.getProperty("text2"));
            emailPropertiesContainer.put("welcome1", prop.getProperty("welcome1"));
            emailPropertiesContainer.put("welcome2", prop.getProperty("welcome2"));

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return emailPropertiesContainer;
    }
}
