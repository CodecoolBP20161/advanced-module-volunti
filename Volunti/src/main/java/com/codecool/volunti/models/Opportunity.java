package com.codecool.volunti.models;


import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "opportunities")
@Data
public class Opportunity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @NotEmpty(message = "Title is empty")
    private String title;

    @NotEmpty(message = "numberOfVolunteers is empty")
    private int numberOfVolunteers;

    private String accommodationType;
    private String foodType;
    private int hoursExpected;
    private String hoursExpectedType;
    private int minimumStayInDays;
    private Date availabilityFrom;
    private Date dateAvailabilityTo;
    private String costs;
    private String requirements;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "opportunities_skills", joinColumns = @JoinColumn(name = "opportunity_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private List<Skill> skills;

    public Opportunity() {
    }

    public Opportunity(String title, int numberOfVolunteers, String accommodationType, String foodType, int hoursExpected, int minimumStayInDays, Date availabilityFrom, Date dateAvailabilityTo, String costs, String requirements, List<Skill> skills) {

        this.title = title;
        this.numberOfVolunteers = numberOfVolunteers;
        this.accommodationType = accommodationType;
        this.foodType = foodType;
        this.hoursExpected = hoursExpected;
        this.minimumStayInDays = minimumStayInDays;
        this.availabilityFrom = availabilityFrom;
        this.dateAvailabilityTo = dateAvailabilityTo;
        this.costs = costs;
        this.requirements = requirements;
        this.skills = skills;
    }
}
