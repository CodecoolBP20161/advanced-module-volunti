package com.codecool.volunti.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


public class ErrorException extends RuntimeException{
    private final int code;
    private final String message;
    public ErrorException(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}