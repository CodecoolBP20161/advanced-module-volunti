package com.codecool.volunti.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "FILTER_TO_OPPORTUNITY")  // A view!
public class Filter2Opportunity {

    @Id
    private int id;

    private String title;

    @Column(name = "availability_from")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date availabilityFrom;

    @Column(name = "date_Availability_to")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dateAvailabilityTo;

    private String name;

    private String category;

    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getAvailabilityFrom() {
        return availabilityFrom;
    }

    public void setAvailabilityFrom(Date availabilityFrom) {
        this.availabilityFrom = availabilityFrom;
    }

    public Date getDateAvailabilityTo() {
        return dateAvailabilityTo;
    }

    public void setDateAvailabilityTo(Date dateAvailabilityTo) {
        this.dateAvailabilityTo = dateAvailabilityTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Filter2Opportunity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", availabilityFrom=" + availabilityFrom +
                ", dateAvailabilityTo=" + dateAvailabilityTo +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
