package com.codecool.volunti.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


public class ErrorException {
    private int code;
    private String message;
    public ErrorException(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}