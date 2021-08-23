package com.goomoong.room9backend.exception;

public class DuplicateNicknameException extends RuntimeException{
    public DuplicateNicknameException(String message) {
        super(message);
    }
}
