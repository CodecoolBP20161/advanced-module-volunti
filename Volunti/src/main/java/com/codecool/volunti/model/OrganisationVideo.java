package com.codecool.volunti.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity()
@Data
@Table(name="organisation_video")
@Slf4j
public class OrganisationVideo {

    @Id
    @Column(name="video_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String embedCode;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "organisation_id")
    private Organisation organisationId;
}
