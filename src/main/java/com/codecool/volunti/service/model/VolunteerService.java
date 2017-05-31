package com.codecool.volunti.service.model;

import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.repository.VolunteerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;


@Service
@Transactional
public class VolunteerService {

    private VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public void save(Volunteer volunteer) {
        volunteerRepository.save(volunteer);
    }
}

