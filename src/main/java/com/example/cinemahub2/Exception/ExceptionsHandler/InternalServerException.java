package com.example.cinemahub2.Exception.ExceptionsHandler;

public class InternalServerException extends RuntimeException{

    // Default message for a 500 Internal Server Error.
    private static final String DEFAULT_MESSAGE = "An unexpected internal server error has occurred. Please try again later or contact support.";

    // Constructor accepting a custom message
    public InternalServerException(String message) {
        super(message);
    }

    // Throws the exception using the predefined static DEFAULT_MESSAGE.
    public InternalServerException() {
        super(DEFAULT_MESSAGE);
    }

    // Constructor accepting a custom message and the original cause
    public InternalServerException(String message,Throwable cause){
        super(message,cause);
    }

    // The most common use case for 500 errors. It ensures the client receives the safe
    // DEFAULT_MESSAGE while preserving the full stack trace of the original error (the 'cause')
    public InternalServerException(Throwable cause) {
        super(DEFAULT_MESSAGE,cause);
    }


}
