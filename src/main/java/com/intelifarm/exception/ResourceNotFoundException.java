package com.intelifarm.exception;

public class ResourceNotFoundException extends BussinesException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}