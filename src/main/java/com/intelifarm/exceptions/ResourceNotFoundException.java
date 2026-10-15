package com.intelifarm.exceptions;

public class ResourceNotFoundException extends BussinesException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}