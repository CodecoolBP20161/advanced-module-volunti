package com.codecool.volunti.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Filter2OpportunityDTO {

    private int id;

    private String title;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date availabilityFrom;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dateAvailabilityTo;

    private List<String> name;

    private String category;

    private String country;

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public Date getAvailabilityFrom() {
//        return availabilityFrom;
//    }
//
//    public void setAvailabilityFrom(Date availabilityFrom) {
//        this.availabilityFrom = availabilityFrom;
//    }
//
//    public Date getDateAvailabilityTo() {
//        return dateAvailabilityTo;
//    }
//
//    public void setDateAvailabilityTo(Date dateAvailabilityTo) {
//        this.dateAvailabilityTo = dateAvailabilityTo;
//    }
//
//    public List<String> getName() {
//        return name;
//    }
//
//    public void setName(List<String> name) {
//        this.name = name;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
}
