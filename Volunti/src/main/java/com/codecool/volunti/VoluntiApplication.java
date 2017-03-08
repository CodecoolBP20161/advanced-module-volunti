package com.codecool.volunti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;

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


//    CREATE VIEW FILTER_TO_OPPORTUNITY AS
//    SELECT DISTINCT opp.id, opp.title, opp.availability_from, opp.date_availability_to,skills.name, org.category, org.country FROM OPPORTUNITIES opp
//    INNER JOIN ORGANISATION org
//    ON opp.organisation_id = org.organisation_id
//    INNER JOIN OPPORTUNITIES_SKILLS opp_skill ON opp.id = opp_skill.opportunity_id
//    INNER JOIN SKILLS skills
//    ON opp_skill.skill_id = skills.id

}
