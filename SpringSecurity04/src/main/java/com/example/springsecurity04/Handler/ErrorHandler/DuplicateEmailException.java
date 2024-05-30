package com.example.springsecurity04.Handler.ErrorHandler;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}