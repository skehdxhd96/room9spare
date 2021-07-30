package com.goomoong.room9backend.exception;

public class NoSuchUserException extends RuntimeException{
    public NoSuchUserException(String message) {
        super(message);
    }
}
