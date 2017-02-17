package com.codecool.volunti.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "skills")
    private List<Opportunity> opportunities;

    public Skill(String name) {
        this.name = name;
    }
}
