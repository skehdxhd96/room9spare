package com.goomoong.room9backend.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequestRoomDto {

    //Configuration
    private List<confDto> conf;

    //Amenity
    private List<String> facilities;

    //room
    private int limit; // 제한인원
    private int price; // 가격
    private String title; // 방 제목
    private String content; // 방 소개글(내용)
    private String detailLocation; // 방 상세 위치
    private String rule; // 방 규칙
    private int addCharge; // 추가요금

}
