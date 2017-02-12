package com.codecool.volunti;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.repository.OrganisationRepository;
import com.codecool.volunti.repository.UserRepository;
import com.codecool.volunti.repository.VolunteerRepository;
import com.codecool.volunti.service.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class VoluntiApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoluntiApplication.class);

    @Autowired
    DataLoader dataLoader;

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
    void seeData() {
        LOGGER.info("seeData method called...");
        for (Organisation organisation : organisationRepository.findAll()) {
            LOGGER.info(organisation.toString());
        }

        for (User user : userRepository.findAll()) {
            LOGGER.info(user.toString());
        }

        for (Volunteer volunteer : volunteerRepository.findAll()) {
            LOGGER.info(volunteer.toString());
        }
    }

}
