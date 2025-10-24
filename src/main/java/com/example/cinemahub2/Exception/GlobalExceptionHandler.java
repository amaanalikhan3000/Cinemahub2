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
                                                               WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponse error = new ErrorResponse(
                message.getMessage(),
                request.getDescription(false)
        );

       return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(BookingConflictException.class)
    public ResponseEntity<ErrorResponse> handlerBookingConflictException(BookingConflictException message,
                                                                         WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        ErrorResponse error = new ErrorResponse(
                message.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error,status);
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotAuthorizedException(UserNotAuthorizedException message,
                                                                           WebRequest request) {

        HttpStatus status = HttpStatus.FORBIDDEN;

        ErrorResponse error = new ErrorResponse(
                message.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error,status);

    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidRequestException(InvalidRequestException message,
                                                                        WebRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse error = new ErrorResponse(
                message.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error,status);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidRequestException(ResourceNotFoundException message,
                                                                        WebRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponse error = new ErrorResponse(
                message.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error,status);

    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidRequestException(InternalServerException message,
                                                                        WebRequest request){

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse error = new ErrorResponse(
                message.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error,status);

    }


}