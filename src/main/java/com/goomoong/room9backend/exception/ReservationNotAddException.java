package com.goomoong.room9backend.exception;

public class ReservationNotAddException extends PermissionDeniedException{
    public ReservationNotAddException() {
        super("Guest만 예약 가능합니다.");
    }
}
