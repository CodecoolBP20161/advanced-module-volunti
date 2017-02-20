package com.codecool.volunti.service;

import com.codecool.volunti.model.*;
import com.codecool.volunti.model.enums.*;
import com.codecool.volunti.repository.OrganisationRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
public class OrganisationRepositoryTest extends AbstractServiceTest {

    @Autowired
    private OrganisationRepository repository;
    private Organisation organisation;

    @Before
    public void setUp() {
        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        spokenLanguages.add(SpokenLanguage.ENGLISH);
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);

        organisation = new Organisation("Test 1", Category.TEACHING, "Country", "zipcode", "City", "Address", spokenLanguages, "Mission minimum 10 character", "Desc 1 min 3 character", "Desc 2 min 3 character");
        this.repository.save(organisation);
    }

    @Test
    public void testForTheFields() {

        organisation = this.repository.findByName("Test 1");
        assertThat(organisation.getName()).isEqualTo("Test 1");
        assertThat(organisation.getCategory()).isEqualTo(Category.TEACHING);
        assertThat(organisation.getCountry()).isEqualTo("Country");
        assertThat(organisation.getZipcode()).isEqualTo("zipcode");
        assertThat(organisation.getCity()).isEqualTo("City");
        assertThat(organisation.getAddress()).isEqualTo("Address");
        assertThat(organisation.getSpokenLanguage().toString()).isEqualTo("[ENGLISH, HUNGARIAN]");
        assertThat(organisation.getMission()).isEqualTo("Mission minimum 10 character");
        assertThat(organisation.getDescription1()).isEqualTo("Desc 1 min 3 character");
        assertThat(organisation.getDescription2()).isEqualTo("Desc 2 min 3 character");

    }

}