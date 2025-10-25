package com.example.cinemahub2.Exception.ExceptionsHandler;

public class UserNotFoundException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "The requested user account could not be found.";

    // 1. Default constructor: Uses the static DEFAULT_MESSAGE.
    public UserNotFoundException(){
        super(DEFAULT_MESSAGE);
    }

    // 2. Constructor: Uses a custom message (e.g., "User with ID: 10 failed login.").
    public UserNotFoundException(String message){
        super(message);
    }

    // 3. Constructor: Uses a custom message and wraps the original cause.
    public UserNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    // 4. Constructor: Uses the DEFAULT_MESSAGE and wraps the original cause.
    public UserNotFoundException(Throwable cause){
        super(DEFAULT_MESSAGE, cause);
    }

}
