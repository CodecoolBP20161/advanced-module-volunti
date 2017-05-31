package com.codecool.volunti.controller;

import com.codecool.volunti.model.enums.Category;
import com.codecool.volunti.model.enums.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class OrganisationRestController {

    @GetMapping(value = "/api/organisation/profile-data")
    public Map<String, Object> getData() {
        EnumSet<Country> allCountries = EnumSet.allOf(Country.class);
        EnumSet<Category> allCategories = EnumSet.allOf(Category.class);

        Map<String, Object> data = new HashMap<>();
        data.put("countries", allCountries);
        data.put("categories", allCategories);

        return data;
    }
}
