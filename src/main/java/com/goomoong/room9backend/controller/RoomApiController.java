package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.service.room.RoomSearchService;
import com.goomoong.room9backend.service.room.RoomService;
import com.goomoong.room9backend.domain.room.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoomApiController {

    private final RoomService roomService;
    private final RoomSearchService roomSearchService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/room/create")
    public CreatedResponseRoomDto createdRoom(@ModelAttribute @Validated CreatedRequestRoomDto request) throws IOException{
        return new CreatedResponseRoomDto(roomService.addRoom(request));
    }

    @GetMapping("/room")
    public roomData.GET<List<GetCommonRoom>> getAllRooms() {
        List<GetCommonRoom> roomList = roomService.findAll()
                .stream().map(r -> new GetCommonRoom(r)).collect(Collectors.toList());
        return new roomData.GET<>(roomList.size(), roomList);
    }

    @GetMapping("/room/{roomId}")
    public GetDetailRoom getDetailRoom(@PathVariable("roomId") Long id) {
        return new GetDetailRoom(roomService.getRoomDetail(id));
    }

    @GetMapping("/room/search")
    public roomData.GET<List<GetCommonRoom>> getRoomWithFilter(@Valid searchDto search) {
        List<GetCommonRoom> roomList = roomSearchService.search(search).stream()
                .map(r -> new GetCommonRoom(r)).collect(Collectors.toList());
        return new roomData.GET<>(roomList.size(), roomList);
    }

    @GetMapping("/room/price/{roomId}")
    public roomData.price getRoomPrice(@PathVariable("roomId") Long id, @Valid priceDto priceDto) {
        return new roomData.price(roomService.getTotalPrice(id, priceDto));
    }
}
