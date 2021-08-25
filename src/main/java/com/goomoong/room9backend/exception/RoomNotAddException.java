package com.goomoong.room9backend.exception;

public class RoomNotAddException extends PermissionDeniedException{
    public RoomNotAddException() {
        super("HOST만 숙소 등록이 가능합니다.");
    }
}
