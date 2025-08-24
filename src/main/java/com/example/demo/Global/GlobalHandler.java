package com.example.demo.Global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.Global.CustomExceptions.CustomJWTexpiredException;
import com.example.demo.Global.CustomExceptions.EntityAlreadyExistsException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex){
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomJWTexpiredException.class)
    public ResponseEntity<ErrorResponse> handleJwtExipration(CustomJWTexpiredException ex){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleJwtExpiration(EntityAlreadyExistsException ex){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse("user already exists with that email",HttpStatus.CONFLICT.value()),HttpStatus.CONFLICT);
    }


}
 