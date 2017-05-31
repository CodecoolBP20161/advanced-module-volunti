package com.codecool.volunti.controller.exception_controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.TransactionalException;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(FileUploadBase.FileSizeLimitExceededException.class)
    public String handleSizeExceededException(HttpServletRequest request, Exception ex) {
        log.error("file size limit exception!");
        return "File size is too large.";
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
    public @ResponseBody String handleMultiPartException(MultipartException e) {
        log.error("multipart file exception!");
        return "File size is too large.";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(HttpServletRequest request, Exception ex) {
        log.error("illegal state exception!");
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(HttpServletRequest request, Exception ex) {
        log.error("illegal argument ex!");
        ex.printStackTrace();
        return "error";
    }

   @ExceptionHandler(Exception.class)
    public String exception(Exception exception, Model model){
        log.error("Exception!!!!");
        log.info(exception.toString());
        model.addAttribute("exception", exception);
        return "error";
    }

}
