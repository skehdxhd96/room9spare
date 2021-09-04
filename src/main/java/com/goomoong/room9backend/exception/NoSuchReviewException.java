package com.goomoong.room9backend.exception;

public class NoSuchReviewException extends RuntimeException {
    public NoSuchReviewException(String massage){
        super(massage);
    }
}
