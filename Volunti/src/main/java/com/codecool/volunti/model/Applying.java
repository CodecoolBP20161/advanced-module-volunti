package com.codecool.volunti.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "applications")
@Data
public class Applying {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Volunteer volunteer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Opportunity opportunity;

    private String question;
    private String whyToApply;


}
