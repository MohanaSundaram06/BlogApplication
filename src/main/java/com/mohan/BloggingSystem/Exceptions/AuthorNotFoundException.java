package com.mohan.BloggingSystem.Exceptions;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(String message) {
        super("The requested Author " +message + " not found");

    }
}
