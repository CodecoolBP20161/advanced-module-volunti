package com.codecool.volunti.models;


import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Opportunity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @NotEmpty(message = "Title is empty")
    private String title;

    @NotEmpty(message = "numberOfVolunteers is empty")
    private int numberOfVolunteers;

    private String accomodationType;
    private String foodType;
    private int hoursexpected;
    private String hoursExpectedType;
    private int minimumStayInDays;
    private Date availabilityFrom;
    private Date dateAvailabilityTo;
    private String costs;
    private String requirements;

}
