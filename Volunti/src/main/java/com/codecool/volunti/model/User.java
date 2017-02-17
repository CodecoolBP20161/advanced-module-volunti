package com.codecool.volunti.model;


import com.codecool.volunti.model.enums.UserStatus;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name="\"User\"")
@Data
public class User {

    @Id
    @Column(name="user_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min=2, max=100)
    @Column(name="first_name")
    private String firstName;

    @Size(min=2, max=100)
    @Column(name="last_name")
    private String lastName;

    @NotEmpty
    @Size(min=2, max=50)
    @Column(name="email")
    private String email;

    @Column(name="activation_id")
    private UUID activationID;

    @NotEmpty
    @Size(min=3)
    @Column(name="password")
    private String password;

    @Column(name="salt")
    private String salt;

    @ManyToOne
    @JoinColumn(name="organisation_id")
    private Organisation organisation;

    @ManyToOne
    @JoinColumn(name="volunteer_id")
    private Volunteer volunteer;

    @Column(name="user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public User() {
        activationID = UUID.randomUUID();
        userStatus = UserStatus.INACTIVE;
    }

    public User(String firstName, String lastName, String email, String password, String salt) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    public User(String firstName, String lastName, String email, String password, String salt, Organisation organisation) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.organisation = organisation;
    }

    public User(String firstName, String lastName, String email, String password, String salt, Volunteer volunteer) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.volunteer = volunteer;
    }

    public User(String firstName, String lastName, String email, String password, String salt, Organisation organisation, Volunteer volunteer) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.organisation = organisation;
        this.volunteer = volunteer;
    }
}
