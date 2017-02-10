package com.codecool.volunti.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

}
