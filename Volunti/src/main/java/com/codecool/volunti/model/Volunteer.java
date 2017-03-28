package com.codecool.volunti.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Volunteer")
@Data
public class Volunteer {

    @Id
    @Column(name="volunteer_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "volunteer_country")
    private String country;

    public Volunteer() {
    }
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "volunteers_skills", joinColumns = @JoinColumn(name = "volunteer_id", referencedColumnName = "volunteer_id"), inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private List<Skill> volunteerSkills;
}


