package com.goomoong.room9backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class paymentController {

    @PostMapping("/room/payment/{roomId}")
    public void payRoomWithKakao() {

    }
}
