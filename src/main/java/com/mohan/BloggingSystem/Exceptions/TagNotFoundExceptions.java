package com.mohan.BloggingSystem.Exceptions;

public class TagNotFoundExceptions extends RuntimeException{
    public TagNotFoundExceptions(String message) {
        super("The requested Tag " + message + " not found");
    }
}
