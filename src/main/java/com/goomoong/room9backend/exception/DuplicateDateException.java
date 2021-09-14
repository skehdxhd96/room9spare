package com.goomoong.room9backend.exception;

public class DuplicateDateException extends RuntimeException{
    public DuplicateDateException(String message) {
        super(message);
    }
}
