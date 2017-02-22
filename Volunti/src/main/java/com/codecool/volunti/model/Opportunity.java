package com.codecool.volunti.model;


import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "opportunities")
@Data
@ToString(exclude = "organisation")
public class Opportunity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @NotEmpty(message = "Title is empty")
    @Size(min=1, max=125, message = "Title size is not correct")
    private String title;

    @NotNull(message = "numberOfVolunteers is null")
    private Integer numberOfVolunteers;

    private String accommodationType;
    private String foodType;
    private int hoursExpected;
    private String hoursExpectedType;
    private int minimumStayInDays;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date availabilityFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateAvailabilityTo;

    private String costs;
    private String requirements;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "opportunities_skills", joinColumns = @JoinColumn(name = "opportunity_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private List<Skill> opportunitySkills;
}
