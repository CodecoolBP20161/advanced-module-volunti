package com.codecool.volunti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;


@SpringBootApplication
public class VoluntiApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoluntiApplication.class);




    public static void main(String[] args) {
        SpringApplication.run(VoluntiApplication.class, args);
    }

//

}
