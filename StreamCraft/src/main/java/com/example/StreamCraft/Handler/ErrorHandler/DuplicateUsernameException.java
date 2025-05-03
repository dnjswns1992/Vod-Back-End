package com.example.StreamCraft.Handler.ErrorHandler;

public class DuplicateUsernameException extends RuntimeException{

    public DuplicateUsernameException(String message){
        super(message);
    }
}
