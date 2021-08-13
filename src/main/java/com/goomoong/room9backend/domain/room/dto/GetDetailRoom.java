package com.goomoong.room9backend.domain.room.dto;

import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.RoomConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDetailRoom extends GetCommonRoom {

    private String content;
    private String rule;
    private int charge; // 추가요금

    private List<confDto> room_configuration = new ArrayList<>();
    private List<amenityDto> room_amenity = new ArrayList<>();

    public GetDetailRoom(Room room) {
        super(room);
        this.content = room.getContent();
        this.rule = room.getRule();
        this.charge = room.getCharge();
        this.room_configuration = room.getRoomConfigures()
                .stream().map(c -> new confDto(c)).collect(Collectors.toList());
        this.room_amenity = room.getAmenities()
                .stream().map(a -> new amenityDto(a)).collect(Collectors.toList());
    }

}
