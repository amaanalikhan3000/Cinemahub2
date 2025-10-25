package com.example.cinemahub2.Exception.ExceptionsHandler;

public class InvalidRequestException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "The request was invalid or improperly structured. Please check the data format and required fields.";

    // 1. Default constructor: Uses the static default message.
    public InvalidRequestException(String message) {
        super(message);
    }

    // 2. Constructor accepting a custom message: Allows developers to provide specific details
    // (e.g., "Field 'X' cannot be null.") when throwing the exception.
    public InvalidRequestException() {
        super(DEFAULT_MESSAGE);
    }

    // 3. Constructor accepting a custom message and the original cause: Useful when translating
    // a lower-level exception (like a framework validation error) into this business exception.
    public InvalidRequestException(String message,Throwable cause) {
        super(message,cause);
    }

    // 4. Constructor accepting only the cause: Uses the default message while still wrapping
    // and preserving the original, root exception.
    public InvalidRequestException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
