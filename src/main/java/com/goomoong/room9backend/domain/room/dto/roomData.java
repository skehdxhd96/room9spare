package com.goomoong.room9backend.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


public class roomData {

    @Data
    @AllArgsConstructor
    public static class GET<T> {
        private int count;
        private T room;
    }

    @Data
    @AllArgsConstructor
    public static class price {
        private Long totalPrice;
    }
}
