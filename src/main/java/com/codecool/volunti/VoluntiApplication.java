package com.codecool.volunti;

import com.codecool.volunti.configuration.DataloaderProperties;
import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.repository.VolunteerRepository;
import com.codecool.volunti.service.DataLoader;
import com.codecool.volunti.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;


import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(DataloaderProperties.class)
public class VoluntiApplication {

    DataLoader dataLoader;

    @Autowired
    StorageService storageService;

    @Autowired
    OrganisationRepository organisationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VolunteerRepository volunteerRepository;




    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            ErrorPage custom404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error");
            container.addErrorPages(custom404Page);
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(VoluntiApplication.class, args);
    }

    @PostConstruct
    void afterInit() {
        log.info("afterInit method called...");
        this.storageService.deleteAll();
        this.storageService.init();
    }

}
