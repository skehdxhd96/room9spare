package com.goomoong.room9backend.domain.file.dto;

import com.goomoong.room9backend.domain.file.RoomImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetRoomfileDto {

    private String url;

    public GetRoomfileDto(RoomImg roomImg) {
        this.url = roomImg.getFile().getUrl();
    }
}
