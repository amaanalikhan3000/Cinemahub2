package com.example.cinemahub2.Exception.ExceptionsHandler;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){
        super(message);
    }

    public UserNotFoundException(String message,Throwable cause){
        super(message,cause);
    }

}
