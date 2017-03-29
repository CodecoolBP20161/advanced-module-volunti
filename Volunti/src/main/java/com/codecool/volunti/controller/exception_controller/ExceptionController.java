package com.codecool.volunti.controller.exception_controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public String exception(Exception exception, Model model){
        model.addAttribute("exception", exception);
        return "error";
    }

}
