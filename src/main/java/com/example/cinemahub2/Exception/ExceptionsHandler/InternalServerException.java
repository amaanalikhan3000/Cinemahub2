package com.example.cinemahub2.Exception.ExceptionsHandler;

public class InternalServerException extends RuntimeException{

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message,Throwable cause){
        super(message,cause);
    }


}
