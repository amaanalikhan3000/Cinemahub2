package com.example.cinemahub2.Exception.ExceptionsHandler;

public class InvalidRequestException extends RuntimeException{

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message,Throwable cause) {
        super(message,cause);
    }

}
