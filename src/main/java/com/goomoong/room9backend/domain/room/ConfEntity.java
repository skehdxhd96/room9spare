package com.goomoong.room9backend.domain.room;

import com.goomoong.room9backend.domain.room.dto.confDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class ConfEntity {

    @Id @GeneratedValue
    private Long id;

    private RoomConfiguration roomConfiguration;

    public ConfEntity(confDto dto) {
        this.roomConfiguration = new RoomConfiguration(dto.getConfType(), dto.getCount());
    }
}
