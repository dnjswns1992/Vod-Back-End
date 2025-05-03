package com.example.StreamCraft.Handler.ErrorHandler;

public class DuplicateNickNameException extends RuntimeException{

    public DuplicateNickNameException(String message){
        super(message);
    }
}
