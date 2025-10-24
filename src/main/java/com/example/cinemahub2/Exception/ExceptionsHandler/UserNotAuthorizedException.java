package com.example.cinemahub2.Exception.ExceptionsHandler;

public class UserNotAuthorizedException extends RuntimeException{

    public UserNotAuthorizedException(String message) {
        super(message);
    }

    public UserNotAuthorizedException(String message,Throwable cause) {
        super(message,cause);
    }

}
