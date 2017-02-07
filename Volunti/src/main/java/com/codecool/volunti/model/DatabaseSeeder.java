package com.codecool.volunti.model;


import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.codecool.volunti.model.repository.OrganisationRepository;
import com.codecool.volunti.model.repository.UserRepository;
import com.codecool.volunti.model.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private VolunteerRepository volunteerRepository;


    @Override
    public void run(String... strings) throws Exception {

        List<User> userList = new ArrayList<>();
        List<Organisation> organisationsList = new ArrayList<>();
        List<Volunteer> volunteerList = new ArrayList<>();

        User user1 = new User("Anna", "Kiss", "asd@gmail.com", "asdasd", "asd");

        Organisation organisation1 = new Organisation("UNICEF", Category.TEACHING, "Hungary", "Isaszeg", "Kossuth utca", SpokenLanguage.ENGLISH, "asdasdasdasdasdasdasd", "asdasd", "asdasd");
        Volunteer volunteer = new Volunteer();
        User user2 = new User("Béla", "Nagy", "asd@gmail.com", "asdasd", "asd", organisation1, volunteer);


        userList.add(user1);
        userList.add(user2);
        organisationsList.add(organisation1);
        volunteerList.add(volunteer);


        organisationRepository.save(organisationsList);
        volunteerRepository.save(volunteerList);
        userRepository.save(userList);

    }
}

