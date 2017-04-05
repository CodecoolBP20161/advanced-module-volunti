package com.codecool.volunti.service.model;

import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class VolunteerService {

    private VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository){
        this.volunteerRepository = volunteerRepository;
    }

    public void save(Volunteer volunteer) {
        volunteerRepository.save(volunteer);
    }

    public Volunteer getById(Integer id){ return volunteerRepository.getOne(id);}

}
