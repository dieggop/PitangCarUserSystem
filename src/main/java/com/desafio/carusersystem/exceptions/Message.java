package com.desafio.carusersystem.exceptions;

import org.springframework.http.HttpStatus;

public class Message {
    private String message;
    private int errorCode;

    public Message() {
        this.message = message;
        this.errorCode = errorCode;
    }
    public Message(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
    public Message(String message, HttpStatus errorCode) {
        this.message = message;
        this.errorCode = errorCode.value();
    }

    public String getMessage() {
        return "{" +
                "message='" + message + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }

}
