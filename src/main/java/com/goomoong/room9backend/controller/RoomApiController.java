package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.Service.RoomService;
import com.goomoong.room9backend.domain.room.dto.CreatedRequestRoomDto;
import com.goomoong.room9backend.domain.room.dto.CreatedResponseRoomDto;
import com.goomoong.room9backend.domain.room.dto.UpdateRequestRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoomApiController {

    private final RoomService roomService;

    @PostMapping("/room/created")
    public CreatedResponseRoomDto createdRoom(@RequestBody CreatedRequestRoomDto request) {

        Long roomId = roomService.addRoom(request);

        return new CreatedResponseRoomDto(roomId);
    }

//    @PutMapping("/room/update/{roomId}")
//    public void updateRoom(@PathVariable("roomId") Long id, @RequestBody UpdateRequestRoomDto request) {
//        roomService.updateRoom(id, request);
//    }

}