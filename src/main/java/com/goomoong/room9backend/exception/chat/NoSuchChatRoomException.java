package com.goomoong.room9backend.exception.chat;

public class NoSuchChatRoomException extends RuntimeException {
    public NoSuchChatRoomException(String message) {
        super(message);
    }
}
