package com.codecool.volunti.model;

import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import com.codecool.volunti.model.enums.SpokenLanguage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="organisations")
@Data
public class Organisation {

    @Id
    @Column(name="organisation_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int organisationId;

    @NotEmpty
    @Size(min=1, max=255)
    @Column(name="name")
    private String name;

    // TODO: 2017.02.20. this field should be @NotEmpty and have a @Size(min=1, max=255)
    // annotation, but Spring throws a validation error in these cases
    @Column(name="category")
    @Enumerated(EnumType.STRING)
    private Category category;


    @Column(name="country")
    @Enumerated(EnumType.STRING)
    private Country country;

    @NotEmpty
    @Size(min=1, max=255)
    @Column(name="zipcode")
    private String zipcode;

    @NotEmpty
    @Size(min=1, max=255)
    @Column(name="city")
    private String city;

    @NotEmpty
    @Size(min=1, max=255)
    @Column(name="address")
    private String address;

    @NotEmpty
    @Size(min=1)
    @Column(name="spoken_language")
    private ArrayList<SpokenLanguage> spokenLanguage;

    @NotEmpty
    @Size(min=1)
    @Column(name="mission")
    private String mission;

    @NotEmpty
    @Size(min=1)
    @Column(name="description_1")
    private String description1;

    @NotEmpty
    @Size(min=1)
    @Column(name="description_2")
    private String description2;

    @Column(name="profile_picture")
    private String profilePicture;

    @Transient
    private File profilePictureFileForSave;

    @Column(name="background_picture")
    private String backgroundPicture;

    @Transient
    private File backgroundPictureFileForSave;

    @OneToMany(mappedBy="organisationId")
    @JsonSerialize(using = CustomListSerializer.class)
    private List<OrganisationSocialLink> social_link;

    public Organisation(){}

    public Organisation(String name, Category category, Country country, String zipcode, String city, String address, ArrayList<SpokenLanguage> spokenLanguage, String mission, String description1, String description2, String profilePicture, String backgroundPicture) {
        this.setName(name);
        this.setCategory(category);
        this.setCountry(country);
        this.setCity(city);
        this.setAddress(address);
        this.setSpokenLanguage(spokenLanguage);
        this.setMission(mission);
        this.setZipcode(zipcode);
        this.setDescription1(description1);
        this.setDescription2(description2);
        this.setProfilePicture(profilePicture);
        this.setBackgroundPicture(backgroundPicture);
    }

    public void setProfilePictureFromFile(File file) {
        this.profilePictureFileForSave = file;
    }

    public void setBackgroundPictureFromFile(File file) {
        this.backgroundPictureFileForSave = file;
    }


}
