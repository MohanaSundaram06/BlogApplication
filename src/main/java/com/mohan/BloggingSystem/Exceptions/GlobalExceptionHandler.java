package com.mohan.BloggingSystem.Exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;


@Component
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AuthorNotFoundException.class,PostNotFoundException.class,
            TagNotFoundExceptions.class})
    public ResponseEntity<String> ExceptionHandler(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({UserAlreadyExistsException.class,TagAlreadyExistsException.class})
    public ResponseEntity<String> resourceAlreadyExistsExceptionHandler(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> validatorExceptionHandler(ConstraintViolationException ex){
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


//    @ExceptionHandler({ AuthenticationException.class})
//    public ResponseEntity<String> handleAuthenticationException(Exception ex) {
//
//        String re = "Authentication failed at controller advice";
////        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
//        System.out.println(re);
//        return new ResponseEntity<>(re, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler({ AccessDeniedException.class})
//    public ResponseEntity<String> handleAccessDeniedException(Exception ex) {
//
//        String re = "Authentication failed at controller advice";
////        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
//        System.out.println(re);
//        return new ResponseEntity<>(re, HttpStatus.UNAUTHORIZED);
//    }
}
