package com.sambasiva.finance_tracker.controllers;

import com.sambasiva.finance_tracker.exceptions.InvalidUsernameOrPasswordException;
import com.sambasiva.finance_tracker.exceptions.UserAlreadyExistsException;
import com.sambasiva.finance_tracker.models.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ResponseEntity<CustomErrorResponse> handleUserAlreadyExistException(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(new CustomErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity<CustomErrorResponse> handleInvalidUsernameOrPasswordException(InvalidUsernameOrPasswordException ex) {
        return new ResponseEntity<>(new CustomErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
    }

}
