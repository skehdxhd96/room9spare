package com.goomoong.room9backend.exception.chat;

public class NoSuchChatMessageException extends RuntimeException{
    public NoSuchChatMessageException(String message) {
        super(message);
    }
}
