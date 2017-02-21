package com.codecool.volunti.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Table(name = "skills")
@ToString(exclude = {"opportunities", "volunteers"})
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "opportunitySkills")
    private List<Opportunity> opportunities;

    @ManyToMany(mappedBy = "volunteerSkills")
    private List<Volunteer> volunteers;

    public Skill(String name) {
        this.name = name;
    }
}
