package com.codecool.volunti.controller.exception_controller;

import com.codecool.volunti.controller.OrganisationProfileController;
import com.codecool.volunti.model.Organisation;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class FileValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Organisation.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Organisation multiModel = (Organisation) target;

    }
}
