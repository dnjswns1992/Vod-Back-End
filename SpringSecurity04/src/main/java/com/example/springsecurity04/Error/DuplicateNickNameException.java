package com.example.springsecurity04.Error;

public class DuplicateNickNameException extends RuntimeException{

    public DuplicateNickNameException(String message){
        super(message);
    }
}
