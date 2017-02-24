package com.codecool.volunti.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="volunteers")
@Data
public class Volunteer {

    @Id
    @Column(name="volunteer_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Volunteer() {
    }
}


