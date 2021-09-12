package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.domain.reservation.dto.ReservationDto;
import com.goomoong.room9backend.service.reservation.reservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {

    private final reservationService reservationService;

    @PostMapping("/room/book/{roomId}")
    public void roomBooked(@PathVariable Long roomId, @RequestBody ReservationDto.request request) {
        reservationService.reserveRoomwithKakaoPayment(roomId, request);
    }
}
