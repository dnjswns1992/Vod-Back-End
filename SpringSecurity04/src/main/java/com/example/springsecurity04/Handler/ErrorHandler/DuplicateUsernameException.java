package com.example.springsecurity04.Handler.ErrorHandler;

public class DuplicateUsernameException extends RuntimeException{

    public DuplicateUsernameException(String message){
        super(message);
    }
}
