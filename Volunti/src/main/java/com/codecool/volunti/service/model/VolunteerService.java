package com.codecool.volunti.service.model;

import com.codecool.volunti.model.Skill;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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

    public Set<String> getUserSkills(int id) {
        List<Skill> skills = volunteerRepository.findOne(id).getVolunteerSkills();
        return skills.stream().map(Skill::getName).collect(Collectors.toSet());
    }

}
