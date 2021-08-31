package com.goomoong.room9backend.domain.room.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderDto {
    LIKEDDESC("likedDesc", "좋아요많은순"),
    LIKEDASC("likedAsc", "좋아요적은순"),
    CREATEDASC("createdAsc", "오래된순"),
    CREATEDDESC("createdDesc", "최신순");

    private final String key;
    private final String value;
}
