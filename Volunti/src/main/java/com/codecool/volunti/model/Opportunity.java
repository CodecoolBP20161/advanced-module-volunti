package com.codecool.volunti.model;


import com.codecool.volunti.model.enums.OpportunityHoursExpectedType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "opportunities")
@Data
@ToString(exclude = {"organisation", "opportunitySkills"})
public class Opportunity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @NotEmpty(message = "Title is empty")
    @Size(min=1, max=125, message = "Title size is not correct")
    private String title;

    @NotNull(message = "numberOfVolunteers is null")
    @Min(value = 0, message = "The value must be positive")
    @Max(value = 100, message = "Value must be less than 100")
    private Integer numberOfVolunteers;

    private String accommodationType;
    private String foodType;

    @Min(value = 0, message = "The value must be positive")
    @Max(value = 100, message = "Value must be less than 100")
    private int hoursExpected;
    private OpportunityHoursExpectedType hoursExpectedType;

    @Min(value = 0, message = "The value must be positive")
    @Max(value = 100, message = "Value must be less than 100")
    private int minimumStayInDays;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date availabilityFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dateAvailabilityTo;

    private String costs;

    @Column(columnDefinition="TEXT")
    private String requirements;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "opportunities_skills", joinColumns = @JoinColumn(name = "opportunity_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private List<Skill> opportunitySkills;
}
