package com.codecool.volunti.model;


import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "opportunities")
@Data
public class Opportunity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)

    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @NotEmpty(message = "Title is empty")
    private String title;

    @NotNull(message = "numberOfVolunteers is null")
    private Integer numberOfVolunteers;

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
    private List<Skill> opportunitySkills;

    public Opportunity() {
    }

    public Opportunity(String title, Integer numberOfVolunteers, String accommodationType, String foodType, int hoursExpected, String hoursExpectedType, int minimumStayInDays, Date availabilityFrom, Date dateAvailabilityTo, String costs, String requirements) {
        this.title = title;
        this.numberOfVolunteers = numberOfVolunteers;
        this.accommodationType = accommodationType;
        this.foodType = foodType;
        this.hoursExpected = hoursExpected;
        this.hoursExpectedType = hoursExpectedType;
        this.minimumStayInDays = minimumStayInDays;
        this.availabilityFrom = availabilityFrom;
        this.dateAvailabilityTo = dateAvailabilityTo;
        this.costs = costs;
        this.requirements = requirements;
    }

    public Opportunity(String title, Integer numberOfVolunteers, String accommodationType, String foodType, int hoursExpected, String hoursExpectedType, int minimumStayInDays, Date availabilityFrom, Date dateAvailabilityTo, String costs, String requirements, List<Skill> skills) {
        this(title, numberOfVolunteers, accommodationType, foodType, hoursExpected, hoursExpectedType, minimumStayInDays, availabilityFrom, dateAvailabilityTo, costs, requirements);
        opportunitySkills = skills;
    }

    public Opportunity(Organisation organisation, String title, Integer numberOfVolunteers, String accommodationType, String foodType, int hoursExpected, String hoursExpectedType, int minimumStayInDays, Date availabilityFrom, Date dateAvailabilityTo, String costs, String requirements) {
        this(title, numberOfVolunteers, accommodationType, foodType, hoursExpected, hoursExpectedType, minimumStayInDays, availabilityFrom, dateAvailabilityTo, costs, requirements);
        this.organisation = organisation;
    }

    public Opportunity(Organisation organisation, String title, Integer numberOfVolunteers, String accommodationType, String foodType, int hoursExpected, String hoursExpectedType, int minimumStayInDays, Date availabilityFrom, Date dateAvailabilityTo, String costs, String requirements, List<Skill> skills) {
        this(organisation, title, numberOfVolunteers, accommodationType, foodType, hoursExpected, hoursExpectedType, minimumStayInDays, availabilityFrom, dateAvailabilityTo, costs, requirements);
        opportunitySkills = skills;
    }
}
