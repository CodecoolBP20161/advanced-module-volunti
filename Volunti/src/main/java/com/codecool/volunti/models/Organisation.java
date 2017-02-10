package com.codecool.volunti.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String something;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "organisation", fetch = FetchType.EAGER)
    private List<Opportunity> opportunities;

}