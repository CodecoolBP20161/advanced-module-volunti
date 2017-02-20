package com.codecool.volunti;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.service.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories(basePackages = {"com.codecool.volunti.repository"})
public class VoluntiApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoluntiApplication.class);

    DataLoader dataLoader;

    @Autowired
    com.codecool.volunti.repository.OrganisationRepository organisationRepository;

    @Autowired
    com.codecool.volunti.repository.UserRepository userRepository;

    @Autowired
    com.codecool.volunti.repository.VolunteerRepository volunteerRepository;

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
