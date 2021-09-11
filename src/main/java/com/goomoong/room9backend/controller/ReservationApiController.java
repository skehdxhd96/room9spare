package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.domain.reservation.dto.ReservationDto;
import com.goomoong.room9backend.security.userdetails.CustomUserDetails;
import com.goomoong.room9backend.service.reservation.reservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {

    private final reservationService reservationService;

    //예약 결제 api -> 예약 테이블에 데이터 넣고 아임포트 param에 전달까지
    @PostMapping("/room/book/{roomId}")
    public JSONObject roomBooked(@PathVariable Long roomId, @RequestBody ReservationDto.request request,
                                 @AuthenticationPrincipal CustomUserDetails currentUser) throws Exception {
        return reservationService.reserveRoom(currentUser.getUser(), roomId, request);
    }

    /**
     * 선택한 방의 전체 예약 내역을 보여주는 api(이미 예약된 날짜를 보여주는 api). DONE
     */
    @GetMapping("/room/book/{roomId}/list")
    public List<ReservationDto.booked> getBookList(@PathVariable Long roomId) {
        return reservationService.getAllBookingList(roomId);
    }

    /**
     * Guest 자신의 예약 내역 확인하기. DONE
     */
    @GetMapping("/room/mybook")
    public List<ReservationDto.MyList> myBookList(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return reservationService.getMyAllBook(currentUser.getId());
    }
}
