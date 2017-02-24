package com.codecool.volunti.model;


import com.codecool.volunti.model.enums.UserStatus;
import com.codecool.volunti.service.EmailService;
import com.codecool.volunti.service.EmailType;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name="users")
@Data
public class User {

    @Transient
    private Logger LOGGER = LoggerFactory.getLogger(User.class);

    @Id
    @Column(name="user_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(min=1, max=255)
    @Column(name="first_name")
    private String firstName;

    @NotEmpty
    @Size(min=1, max=255)
    @Column(name="last_name")
    private String lastName;

    @NotEmpty
    @Size(min=1)
    @Column(name="email")
    private String email;

    @Column(name="activation_id")
    private UUID activationID;

    @NotEmpty
    @Size(min=1)
    @Column(name="password")
    private String password;

    @Column(name="salt")
    private String salt;

    @OneToOne
    @JoinColumn(name="organisation_id")
    private Organisation organisation;

    @OneToOne
    @JoinColumn(name="volunteer_id")
    private Volunteer volunteer;

    @Column(name="user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public User() {
        activationID = UUID.randomUUID();
        userStatus = UserStatus.INACTIVE;
    }


    public User(String firstName, String lastName, String email, String password, String salt, Organisation organisation, Volunteer volunteer) {

        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setSalt(salt);
        this.setOrganisation(organisation);
        this.setVolunteer(volunteer);
    }

    public String signupSuccess(EmailService emailService, EmailType emailType) {
        LOGGER.info("signupSuccess() method called...");
        try {
            // send a notification
            emailService.sendEmail(this, emailType);
        } catch (Exception e) {
            LOGGER.warn("Email not sent: " + e.getMessage());
        }
        return "Thank you for registering with us.";
    }
}
