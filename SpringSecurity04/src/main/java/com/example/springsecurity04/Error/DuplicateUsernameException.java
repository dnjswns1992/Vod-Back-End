package com.example.springsecurity04.Error;

public class DuplicateUsernameException extends RuntimeException{

    public DuplicateUsernameException(String message){
        super(message);
    }
}
