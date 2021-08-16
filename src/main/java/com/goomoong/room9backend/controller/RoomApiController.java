package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.service.room.RoomSearchService;
import com.goomoong.room9backend.service.room.RoomService;
import com.goomoong.room9backend.repository.room.RoomRepository;
import com.goomoong.room9backend.domain.room.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoomApiController {

    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final RoomSearchService roomSearchService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/room/create")
    public CreatedResponseRoomDto createdRoom(@ModelAttribute CreatedRequestRoomDto request) {
        return new CreatedResponseRoomDto(roomService.addRoom(request));
    }

    @GetMapping("/room")
    public roomData getAllRooms() {

        List<GetCommonRoom> rooms = roomRepository.findAll()
                .stream().map(r -> new GetCommonRoom(r)).collect(Collectors.toList());

        return new roomData(rooms.size(), rooms);
    }

    @GetMapping("/room/{roomId}")
    public GetDetailRoom getDetailRoom(@PathVariable("roomId") Long id) {

        return new GetDetailRoom(roomService.getRoomDetail(id));
    }

    @GetMapping("/room/like")
    public roomData getRoombyLike() { // 좋아요순서(메인화면)
        List<GetCommonRoom> rooms = roomRepository.findTop5ByOrderByLikedDesc()
                .stream().map(r -> new GetCommonRoom(r)).collect(Collectors.toList());

        return new roomData(rooms.size(), rooms);
    }

    @GetMapping("/room/date")
    public roomData getRoombydate() { // 최신순서(메인화면)
        List<GetCommonRoom> rooms = roomRepository.findTop5ByOrderByCreatedDateDesc()
                .stream().map(r -> new GetCommonRoom(r)).collect(Collectors.toList());

        return new roomData(rooms.size(), rooms);
    }

    @GetMapping("/room/search")
    public roomData getRoomWithFilter(searchDto search) {
        List<GetCommonRoom> rooms = roomSearchService.search(search)
                .stream().map(r -> new GetCommonRoom(r)).collect(Collectors.toList());

        return new roomData(rooms.size(), rooms);
    }
}
