package com.restapi.exception;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException() {
        super("Username already exists");
    }
}
