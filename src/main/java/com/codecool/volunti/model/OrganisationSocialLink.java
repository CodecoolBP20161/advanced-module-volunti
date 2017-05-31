package com.codecool.volunti.model;


import com.codecool.volunti.model.enums.SocialLink;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Table(name="organisation_social_link")
@Data
@Slf4j
@ToString(exclude = "organisationId")
public class OrganisationSocialLink {

    @Id
    @Column(name="social_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="social_link_type")
    @Enumerated(EnumType.STRING)
    private SocialLink socialLinkType;

    @Column(name="social_link_url")
    private String socialLinkUrl;

    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="organisation_id")
    private Organisation organisationId;

    public OrganisationSocialLink(SocialLink socialLinkType, String socialLinkUrl, Organisation organisationId) {
        this.socialLinkType = socialLinkType;
        this.socialLinkUrl = socialLinkUrl;
        this.organisationId = organisationId;
    }

    public OrganisationSocialLink() {
    }
}
