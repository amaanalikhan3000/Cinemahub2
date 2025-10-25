package com.example.cinemahub2.Exception.ExceptionsHandler;

public class UserNotAuthorizedException extends RuntimeException{

    // Default message for a 403 Forbidden error. It clearly indicates a lack of privileges.
    private static final String DEFAULT_MESSAGE = "The authenticated user does not have the necessary permissions to perform this action.";

    // 1. Constructor: Uses the static DEFAULT_MESSAGE (for simple signaling when permissions fail).
    public UserNotAuthorizedException() {
        super(DEFAULT_MESSAGE);
    }

    // 2. Constructor: Uses a custom message (to specify the missing role or permission).
    public UserNotAuthorizedException(String message) {
        super(message);
    }

    // 3. Constructor: Uses a custom message and wraps the original cause (e.g., failed security check).
    public UserNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    // 4. Constructor: Uses the DEFAULT_MESSAGE and wraps the original cause.
    public UserNotAuthorizedException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

}
