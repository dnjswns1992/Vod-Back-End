package com.example.StreamCraft.Handler.ErrorHandler;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}