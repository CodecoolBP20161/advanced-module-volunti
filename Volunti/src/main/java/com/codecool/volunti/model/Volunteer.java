package com.codecool.volunti.model;

import com.codecool.volunti.model.enums.SpokenLanguage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="volunteers")
@Data
public class Volunteer {

    @Id
    @Column(name="volunteer_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(min=1)
    private String motto;

    @NotEmpty
    @Size(min=1)
    private String interest;

    @Column(name="date_of_birth")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dateOfBirth;

    @NotEmpty
    @Size(min=1)
    @Column(name="spoken_languages")
    private ArrayList<SpokenLanguage> spokenLanguages;

    @Column(name = "volunteer_country")
    private String country;

    public Volunteer() {
    }
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "volunteers_skills", joinColumns = @JoinColumn(name = "volunteer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> volunteerSkills;
}


