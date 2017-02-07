package com.codecool.volunti.model;

import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.SpokenLanguage;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="Organisation")
public class Organisation {

    @Id
    @Column(name="organisation_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
    @Column(name="needed_help")
    private String helpType;

    @NotEmpty
    @Size(min=3)
    @Column(name="what_learn")
    private String whatLearn;




    public Organisation(){}

    public Organisation(String name, Category category, String country, String city, String address, SpokenLanguage spokenLanguage, String mission, String helpType, String whatLearn) {
        this.name = name;
        this.category = category;
        this.country = country;
        this.city = city;
        this.address = address;
        this.spokenLanguage = spokenLanguage;
        this.mission = mission;
        this.helpType = helpType;
        this.whatLearn = whatLearn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SpokenLanguage getSpokenLanguage() {
        return spokenLanguage;
    }

    public void setSpokenLanguage(SpokenLanguage spokenLanguage) {
        this.spokenLanguage = spokenLanguage;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getHelpType() {
        return helpType;
    }

    public void setHelpType(String helpType) {
        this.helpType = helpType;
    }

    public String getWhatLearn() {
        return whatLearn;
    }

    public void setWhatLearn(String whatLearn) {
        this.whatLearn = whatLearn;
    }
}
