package com.codecool.volunti.controller.exception_controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionController {

//    @ExceptionHandler(FileUploadBase.FileSizeLimitExceededException.class)
//    public String handleSizeExceededException(HttpServletRequest request, Exception ex) {
//        log.error("file size limit exception!");
//        return "error";
//    }
//
//    @ExceptionHandler(MultipartException.class)
//    public String handleMultiPartException(HttpServletRequest request, Exception ex, Model model) {
//        log.error("multipart file exception!");
//        model.addAttribute("exception", ex);
//        return "error";
//    }
//
//    @ExceptionHandler(IllegalStateException.class)
//    public String handleIllegalStateException(HttpServletRequest request, Exception ex) {
//        log.error("illegal state exception!");
//        return "error";
//    }

   @ExceptionHandler(Exception.class)
    public String exception(Exception exception, Model model){
        log.error("Exception!!!!");
        model.addAttribute("exception", exception);
        return "error";
    }

}
