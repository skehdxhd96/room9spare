package com.goomoong.room9backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AllUsersResponseDto<T> {
    private T data;
}
