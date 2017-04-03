package com.codecool.volunti.dto;


import com.codecool.volunti.model.enums.Country;
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

    private List<String> skills;

    private String category;

    private Country country;

}
