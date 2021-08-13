package com.goomoong.room9backend.domain.room.dto;

import com.goomoong.room9backend.domain.file.dto.GetRoomfileDto;
import com.goomoong.room9backend.domain.room.Room;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCommonRoom {
    private Long roomId;
    private String username;
    private String title;
    private String Location;
    private int price;
    private int like;
    private List<GetRoomfileDto> url;

    public GetCommonRoom(Room room) {
        this.roomId = room.getId();
        this.username = room.getUsers().getNickname();
        this.title = room.getTitle();
        this.Location = room.getDetailLocation();
        this.price = room.getPrice();
        this.like = room.getLiked();
        this.url = room.getRoomImg()
                .stream().map(i -> new GetRoomfileDto(i)).collect(Collectors.toList());
    }
}
