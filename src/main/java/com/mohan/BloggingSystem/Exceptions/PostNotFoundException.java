package com.mohan.BloggingSystem.Exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super("The requested Post " + message + " not found");
    }
}
