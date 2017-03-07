package com.codecool.volunti.model;


import com.codecool.volunti.model.enums.UserStatus;
import com.codecool.volunti.service.EmailService;
import com.codecool.volunti.service.EmailType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="users")
@Data
@Slf4j
public class User {


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
    private String activationID;

    @NotEmpty
    @Size(min=1)
    @Column(name="password")
    private String password;

    /*
    @Column(name="salt")
    private String salt;
*/
    @OneToOne
    @JoinColumn(name="organisation_id")
    private Organisation organisation;

    @OneToOne
    @JoinColumn(name="volunteer_id")
    private Volunteer volunteer;

    @Column(name="user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public User() {
        activationID = UUID.randomUUID().toString();
        userStatus = UserStatus.INACTIVE;
        //setSalt();
    }

    public User(String firstName, String lastName, String email, String password, Organisation organisation, Volunteer volunteer) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setOrganisation(organisation);
        this.setVolunteer(volunteer);
        //setSalt();
        this.setPassword(password);

        if (activationID == null){
            activationID = UUID.randomUUID().toString();
        }
        /*
        if (salt == null){
            setSalt();
        }
        */
        if (userStatus == null){
            setUserStatus(UserStatus.INACTIVE);
        }
    }

    /*
    private void setSalt(){
        salt = BCrypt.gensalt();
    }
*/
    /*
    public void hashPassword(String password){
        this.password = BCrypt.hashpw(password, salt);
    }
    */

    public String signupSuccess(EmailService emailService, EmailType emailType) {
        log.info("signupSuccess() method called...");
        try {
            // send a notification
            emailService.sendEmail(this, emailType);
        } catch (Exception e) {
            log.warn("Email not sent: " + e.getMessage());
        }
        return "Thank you for registering with us.";
    }
}
