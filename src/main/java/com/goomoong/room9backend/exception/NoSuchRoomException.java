package com.goomoong.room9backend.exception;

public class NoSuchRoomException extends RuntimeException{
    public NoSuchRoomException(String message) {
        super(message);
    }
}
