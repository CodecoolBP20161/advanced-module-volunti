package com.codecool.volunti.model;

import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.SpokenLanguage;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="Organisation")
@Data
public class Organisation {

    @Id
    @Column(name="organisation_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int organisationId;

    @Size(min=2, max=100)
    @Column(name="name")
    private String name;


    @Column(name="category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotEmpty
    @Size(min=3, max=50)
    @Column(name="country")
    private String country;

    @NotEmpty
    @Size(min=3, max=50)
    @Column(name="city")
    private String city;

    @NotEmpty
    @Size(min=3, max=50)
    @Column(name="address")
    private String address;

    @Column(name="spoken_language")
    @Enumerated(EnumType.STRING)
    private SpokenLanguage spokenLanguage;

    @NotEmpty
    @Size(min=10)
    @Column(name="mission")
    private String mission;

    @NotEmpty
    @Size(min=3)
    @Column(name="description_1")
    private String description1;

    @NotEmpty
    @Size(min=3)
    @Column(name="description_2")
    private String description2;

    public Organisation(){}

    public Organisation(String name, Category category, String country, String city, String address, SpokenLanguage spokenLanguage, String mission, String description1, String description2) {
        this.name = name;
        this.category = category;
        this.country = country;
        this.city = city;
        this.address = address;
        this.spokenLanguage = spokenLanguage;
        this.mission = mission;
        this.description1 = description1;
        this.description2 = description2;
    }

}
