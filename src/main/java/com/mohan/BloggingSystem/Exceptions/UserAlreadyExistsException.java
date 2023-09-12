package com.mohan.BloggingSystem.Exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message) {
        super( "The user with given mail id "+ message +" already exists");
    }
}
