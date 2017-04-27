package com.codecool.volunti.controller.exception_controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    /*@ExceptionHandler(Exception.class)
    public String exception(Exception exception, Model model){
        model.addAttribute("exception", exception);
        return "error";
    }*/

    @ExceptionHandler(FileUploadBase.FileSizeLimitExceededException.class)
    public String handleSizeExceededException(HttpServletRequest request, Exception ex) {
        log.error("too large file");
        return "error";
    }

}
