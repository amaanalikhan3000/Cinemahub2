package com.example.cinemahub2.Exception.ExceptionsHandler;

public class BookingConflictException extends RuntimeException {

    // Default message for a 409 Conflict error, indicating a concurrency or state clash.
    private static final String DEFAULT_MESSAGE = "The operation failed due to a conflict with the current resource state (e.g., resource already exists or is reserved).";

    // 1. Constructor: Uses a custom message (for specific detail).
    public BookingConflictException(String message) {
        super(message);
    }

    // 2. Constructor: Uses the static DEFAULT_MESSAGE (for simple, consistent signaling).
    public BookingConflictException(){
        super(DEFAULT_MESSAGE);
    }

    // 3. Constructor: Uses a custom message and wraps the original cause (for error translation).
    public BookingConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    // 4. Constructor: Uses the DEFAULT_MESSAGE and wraps the original cause (common for DB constraint conflicts).
    public BookingConflictException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

}
