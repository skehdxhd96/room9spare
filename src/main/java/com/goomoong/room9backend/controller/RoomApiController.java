package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.service.room.RoomSearchService;
import com.goomoong.room9backend.service.room.RoomService;
import com.goomoong.room9backend.domain.room.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoomApiController {

    private final RoomService roomService;
    private final RoomSearchService roomSearchService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/room/create")
    public CreatedResponseRoomDto createdRoom(@ModelAttribute @Validated CreatedRequestRoomDto request) {
        return new CreatedResponseRoomDto(roomService.addRoom(request));
    }

    @GetMapping("/room")
    public roomData getAllRooms() {
        return new roomData(roomService.findAll().size(), roomService.findAll());
    }

    @GetMapping("/room/{roomId}")
    public GetDetailRoom getDetailRoom(@PathVariable("roomId") Long id) {
        return new GetDetailRoom(roomService.getRoomDetail(id));
    }

    @GetMapping("/room/like")
    public roomData getRoombyLike() { // 좋아요순서(메인화면)
        return new roomData(roomService.findTop5Liked().size(), roomService.findTop5Liked());
    }

    @GetMapping("/room/date")
    public roomData getRoombydate() { // 최신순서(메인화면)
        return new roomData(roomService.findTop5CreatedDate().size(), roomService.findTop5CreatedDate());
    }

    @GetMapping("/room/search")
    public roomData getRoomWithFilter(@Validated searchDto search) {
        return new roomData(roomSearchService.search(search).size(), roomSearchService.search(search));
    }
}
