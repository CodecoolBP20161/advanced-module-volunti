package com.codecool.volunti.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToMany(mappedBy = "opportunitySkills")
    private List<Opportunity> opportunities;

    @JsonIgnore
    @ManyToMany(mappedBy = "volunteerSkills")
    private List<Volunteer> volunteers;

    public Skill() {
    }

    public Skill(String name) {
        this.name = name;
    }
}
