package com.mohan.BloggingSystem.Exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@Component
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AuthorNotFoundException.class,PostNotFoundException.class,
            TagNotFoundExceptions.class})
    public ResponseEntity<ApiErrorResponse> ExceptionHandler(RuntimeException ex){

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND,
                ex.getMessage());

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserAlreadyExistsException.class,TagAlreadyExistsException.class})
    public ResponseEntity<ApiErrorResponse> resourceAlreadyExistsExceptionHandler(RuntimeException ex){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST,
                ex.getMessage());

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> validatorExceptionHandler(ConstraintViolationException ex){
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST,
               errors.toString());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> securityExceptionHandler(Exception ex) {
        ApiErrorResponse apiErrorResponse = null;
        if (ex instanceof BadCredentialsException) {
            apiErrorResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                    "Enter valid credentials");
        }
        if (ex instanceof AccessDeniedException) {
            apiErrorResponse = new ApiErrorResponse(HttpStatus.FORBIDDEN,
                    "Access denied");

        }

        if (ex instanceof SignatureException) {
            apiErrorResponse = new ApiErrorResponse(HttpStatus.FORBIDDEN,
                    "Invalid JWT Signature");
        }
        if (ex instanceof ExpiredJwtException) {
            apiErrorResponse = new ApiErrorResponse(HttpStatus.FORBIDDEN,
                    "JWT Token already expired ");
        }

        if (ex instanceof MalformedJwtException) {
            apiErrorResponse = new ApiErrorResponse(HttpStatus.FORBIDDEN,
                    "Invalid JWT Token");
        }

        return new ResponseEntity<>(apiErrorResponse,apiErrorResponse.getStatus());
    }


}
