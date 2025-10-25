package com.example.cinemahub2.Exception.ExceptionsHandler;

public class ResourceNotFoundException extends RuntimeException{

    // Default message for a 404 Not Found error. It's generic and compliant with API standards.
    private static final String DEFAULT_MESSAGE = "The requested resource was not found on the server.";

    // 1. Constructor: Uses a custom message (for specific detail, like including the missing ID).
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // 2. Constructor: Uses the static DEFAULT_MESSAGE (for simple 404 signaling).
    public ResourceNotFoundException(){
        super(DEFAULT_MESSAGE);
    }

    // 3. Constructor: Uses a custom message and wraps the original cause (if translating a framework error).
    public ResourceNotFoundException(String message,Throwable cause) {
        super(message,cause);
    }

    // 4. Constructor: Uses the DEFAULT_MESSAGE and wraps the original cause (e.g., entity not found in DB).
    public ResourceNotFoundException(Throwable cause) {
        super(DEFAULT_MESSAGE,cause);
    }


}
