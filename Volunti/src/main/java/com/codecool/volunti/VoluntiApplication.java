package com.codecool.volunti;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
@EnableAsync
@EnableJpaRepositories(basePackages = {"com.codecool.volunti.repository"})
public class VoluntiApplication {

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
        log.info("seeData method called...");
        for (Organisation organisation : organisationRepository.findAll()) {
            log.info(organisation.toString());
        }

        for (User user : userRepository.findAll()) {
            log.info(user.toString());
        }

        for (Volunteer volunteer : volunteerRepository.findAll()) {
            log.info(volunteer.toString());
        }
    }

}
