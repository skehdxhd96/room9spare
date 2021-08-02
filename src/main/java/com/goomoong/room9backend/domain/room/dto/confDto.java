package com.goomoong.room9backend.domain.room.dto;

import com.goomoong.room9backend.domain.room.ConfEntity;
import com.goomoong.room9backend.domain.room.RoomConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class confDto {

    private String confType;
    private int count;

    public confDto(ConfEntity confEntity) {
        this.confType = confEntity.getRoomConfiguration().getConfType();
        this.count = confEntity.getRoomConfiguration().getCount();
    }
}
