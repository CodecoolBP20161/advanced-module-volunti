package com.codecool.volunti.service.model;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;

@Slf4j
@Service
@Transactional
public class ValidationService {

    @Autowired
    private UserService userService;
    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private VolunteerService volunteerService;


    public boolean checkIfValueExists(HashMap<String, String> payload){
        log.info("Field Validation executed.");
        String entity = payload.get("entityName");
        String fieldName = payload.get("fieldName");
        String valueToCheck = payload.get("value").trim();
        switch (entity) {
            case "user":
                switch (fieldName) {
                    case "email":
                        return userService.getByEmail(valueToCheck) != null;
                    default:
                        log.error("The given field name in " + entity + " doesnt exists.");
                        throw new NotImplementedException();
                }
            case "organisation":
                switch (fieldName) {
                    case "name":
                        return organisationService.getByName(valueToCheck) != null;
                    default:
                        log.error("The given field name in " + entity + " doesnt exists.");
                        throw new NotImplementedException();
                }
            case "volunteer":
                switch (fieldName) {
                    case "name":
                        return volunteerService.getById(Integer.valueOf(valueToCheck)) != null;
                    default:
                        log.error("The given field name in " + entity + " doesnt exists.");
                        throw new NotImplementedException();
                }
            default:
                log.error("Not implemented validation type or wrong request body.");
                throw new NotImplementedException();
        }
    }

}
