package com.codecool.volunti.service;

import com.codecool.volunti.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;

public class ValidationService {
    private Logger LOGGER = LoggerFactory.getLogger(ValidationService.class);

    private UserService userService;
    private OrganisationService organisationService;

    public ValidationService(OrganisationService organisationService, UserService userService){
        this. organisationService = organisationService;
        this. userService = userService;
    }

    public boolean CheckIfValueExists(HashMap<String, String> payload){
        LOGGER.info("Field Validation Started.");
        String entity = payload.get("entityName");
        String fieldName = payload.get("fieldName");
        String valueToCheck = payload.get("value").trim();
        switch (entity) {
            case "user":
                switch (fieldName) {
                    case "email":
                        LOGGER.info("Entity: User");
                        return userService.getByEmail(valueToCheck) != null;
                    default:
                        LOGGER.error("The given field name in " + entity + " doesnt exists.");
                        throw new NotImplementedException();
                }
            case "organisation":
                switch (fieldName) {
                    case "name":
                        LOGGER.info("Entity: Organization");
                        return organisationService.getByName(valueToCheck) != null;
                    default:
                        LOGGER.error("The given field name in " + entity + " doesnt exists.");
                        throw new NotImplementedException();
                }
            default:
                LOGGER.error("Not implemented validation type or wrong request body.");
                throw new NotImplementedException();
        }
    }

}
