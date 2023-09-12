package com.mohan.BloggingSystem.Exceptions;

public class TagAlreadyExistsException extends RuntimeException{
    public TagAlreadyExistsException(String message) {
        super( "The Tag with given name "+ message +" already exists");
    }
}
