package com.codecool.volunti.model;

import javax.persistence.*;

@Entity
@Table(name="Volunteer")
public class Volunteer {

    @Id
    @Column(name="volunteer_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Volunteer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


