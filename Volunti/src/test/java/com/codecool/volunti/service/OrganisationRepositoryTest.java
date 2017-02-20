package com.codecool.volunti.service;

import com.codecool.volunti.model.*;
import com.codecool.volunti.model.enums.*;
import com.codecool.volunti.repository.OrganisationRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class OrganisationRepositoryTest extends AbstractServiceTest {

    @Autowired
    private OrganisationRepository repository;

    @Test
    public void testExample() throws Exception {
        ArrayList<SpokenLanguage> spokenLanguages = new ArrayList<>();
        spokenLanguages.add(SpokenLanguage.ENGLISH);
        spokenLanguages.add(SpokenLanguage.HUNGARIAN);

        Organisation org = new Organisation("Test 1", Category.TEACHING, "Country", "zipcode", "City", "Address", spokenLanguages, "Mission minimum 10 character", "Desc 1 min 3 character", "Desc 2 min 3 character");
        this.repository.save(org);
        org = this.repository.findByName("Test 1");
        assertThat(org.getName()).isEqualTo("Test 1");
        assertThat(org.getCategory()).isEqualTo(Category.TEACHING);
    }

}