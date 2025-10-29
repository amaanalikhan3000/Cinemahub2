package com.example.cinemahub2.Exception;

import com.example.cinemahub2.DTO.ErrorResponse;
import com.example.cinemahub2.Exception.ExceptionsHandler.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //Creation of UserNotFoundException to handle user's email not found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotFoundException(UserNotFoundException message,
                                                                      WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponse error = new ErrorResponse(
                message.getMessage(),
                404,
                status.getReasonPhrase(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error, status);
    }

    //Creation of BookingConflictException to handle conflicts
    @ExceptionHandler(BookingConflictException.class)
    public ResponseEntity<ErrorResponse> handlerBookingConflictException(BookingConflictException message,
                                                                         WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        ErrorResponse error = new ErrorResponse(
                message.getMessage(),
                409,
                status.getReasonPhrase(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error, status);
    }

    //Creation of BookingConflictException to handle unauthorized users
    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotAuthorizedException(UserNotAuthorizedException message,
                                                                           WebRequest request) {

        HttpStatus status = HttpStatus.FORBIDDEN;

        ErrorResponse error = new ErrorResponse(
                message.getMessage(),
                403,
                status.getReasonPhrase(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error, status);

    }

    //Creation of BookingConflictException to handle invalid requests
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidRequestException(InvalidRequestException message,
                                                                        WebRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse error = new ErrorResponse(
                message.getMessage(),
                400,
                status.getReasonPhrase(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error,status);

    }

    //Creation of BookingConflictException to handle resources not found, e.g: movie not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidRequestException(ResourceNotFoundException message,
                                                                        WebRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponse error = new ErrorResponse(
                message.getMessage(),
                404,
                status.getReasonPhrase(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error,status);

    }

    //Creation of BookingConflictException to handle server errors
    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidRequestException(InternalServerException message,
                                                                        WebRequest request){

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse error = new ErrorResponse(
                message.getMessage(),
                500,
                status.getReasonPhrase(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error,status);

    }


}