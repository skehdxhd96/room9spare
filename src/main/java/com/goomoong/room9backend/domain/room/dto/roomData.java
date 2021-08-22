package com.goomoong.room9backend.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class roomData<T> {

    private int count;
    private T room;
}
