package com.codecool.volunti.service;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.SpokenLanguage;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import static org.assertj.core.api.Assertions.*;

import com.codecool.volunti.repository.OrganisationRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
public class OrganisationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrganisationRepository repository;

    @Test
    public void testExample() throws Exception {
        this.entityManager.persist(new Organisation("Test 1", Category.TEACHING, "Country", "City", "Address", SpokenLanguage.ENGLISH, "Mission minimum 10 character", "Desc 1 min 3 character", "Desc 2 min 3 character"));
        Organisation org = this.repository.findByName("Test 1");
        assertThat(org.getName()).isEqualTo("Test 1");
        assertThat(org.getCategory()).isEqualTo(Category.TEACHING);
    }

}