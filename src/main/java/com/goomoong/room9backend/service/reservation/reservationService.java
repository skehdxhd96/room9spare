package com.goomoong.room9backend.service.reservation;

import com.goomoong.room9backend.domain.reservation.dto.ReservationDto;
import com.goomoong.room9backend.domain.reservation.roomReservation;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.exception.ReservationNotAddException;
import com.goomoong.room9backend.repository.reservation.roomReservationRepository;
import com.goomoong.room9backend.service.UserService;
import com.goomoong.room9backend.util.AboutDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class reservationService {

    private final roomReservationRepository roomReservationRepository;
    private final UserService userService;

    public void reserveRoomwithKakaoPayment(Long roomId, ReservationDto.request request) {

        /**
         * process
         * 1. Role.Guest확인
         * 2. 예약 테이블을 roomId로 조회
         * 2-1. 결과값이 없으면 예약 가능
         * 2-2. 2번의 결과값이 있으나 안겹치면 예약 가능
         * 2-3. 2번의 결과값 중 start - final과 request.start - request.final이 겹치면 예약 불가능
         * 3. 예약 불가능할 시 오류 리턴
         */

        if(userService.findById(request.getUserId()).getRole() != Role.GUEST) {
            throw new ReservationNotAddException();
        } else {
            List<roomReservation> reserveList = findReservationByRoomId(roomId);

            for(roomReservation r : reserveList) {
                boolean isImPossible = false;
                if(AboutDate.reservePossible(request, r.getStartDate(), r.getFinalDate())) { continue; }
                isImPossible = true;
                break;
            }
        }

        LocalDateTime l1 = AboutDate.getLocalDateTimeFromString(request.getStartDate());
        LocalDateTime l2 = AboutDate.getLocalDateTimeFromString(request.getFinalDate());
    }

    public List<roomReservation> findReservationByRoomId(Long roomId) {
        return roomReservationRepository.findByRoomId(roomId);
    }
}
