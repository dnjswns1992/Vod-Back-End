package com.example.springsecurity04.Handler.ErrorHandler;

public class DuplicateNickNameException extends RuntimeException{

    public DuplicateNickNameException(String message){
        super(message);
    }
}
