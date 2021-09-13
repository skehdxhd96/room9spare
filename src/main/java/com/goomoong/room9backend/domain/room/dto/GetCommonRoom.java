package com.goomoong.room9backend.domain.room.dto;

import com.goomoong.room9backend.domain.file.dto.GetRoomfileDto;
import com.goomoong.room9backend.domain.review.dto.scoreDto;
import com.goomoong.room9backend.domain.room.Room;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCommonRoom {
    private Long roomId;
    private String username;
    private String title;
    private String location;
    private int limitPeople;
    private int price;
    private int like;
    private Double avgScore;
    private Integer reviewCount;
    private List<GetRoomfileDto> images;

    public GetCommonRoom(Room room, scoreDto scoreDto) {
        this.roomId = room.getId();
        this.username = room.getUsers().getNickname();
        this.title = room.getTitle();
        this.location = room.getDetailLocation();
        this.limitPeople = room.getLimited();
        this.price = room.getPrice();
        this.like = room.getLiked();
        this.avgScore = scoreDto.getAvgScore();
        this.reviewCount = scoreDto.getReviewCount();
        this.images = room.getRoomImg()
                .stream().map(i -> new GetRoomfileDto(i)).collect(Collectors.toList());
    }
}
