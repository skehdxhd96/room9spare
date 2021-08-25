package com.goomoong.room9backend.service.reservation;

import com.goomoong.room9backend.domain.reservation.dto.ReservationDto;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.exception.ReservationNotAddException;
import com.goomoong.room9backend.repository.reservation.roomReservationRepository;
import com.goomoong.room9backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class reservationService {

    private final roomReservationRepository roomReservationRepository;
    private final UserService userService;

    public void createReservation(Long roomId, ReservationDto.request request) {

        /**
         * process
         * 1. Role.Guest확인
         * 2. Date 겹치는지 확인
         */

        if(userService.findById(request.getUserId()).getRole() != Role.GUEST) {
            throw new ReservationNotAddException();
        }



        LocalDateTime startDate = LocalDateTime.of(LocalDate.of(request.getStartDate().getYear(),
                request.getStartDate().getMonth(), request.getStartDate().getDay()), LocalTime.now());
        LocalDateTime finalDate = LocalDateTime.of(LocalDate.of(request.getFinalDate().getYear(),
                request.getFinalDate().getMonth(), request.getFinalDate().getDay()), LocalTime.now());
        /**
         * request
         * day : 25
         * month : 8
         * year : 2021
         */

//        System.out.println("request.getStartDate().getDay() = " + request.getStartDate().getDay());
//        System.out.println("request.getStartDate().getMonth() = " + request.getStartDate().getMonth());
//        System.out.println("request.getStartDate().getYear() = " + request.getStartDate().getYear());

//        System.out.println("request.getFinalDate().getDay() = " + request.getFinalDate().getDay());
//        System.out.println("request.getFinalDate().getMonth() = " + request.getFinalDate().getMonth());
//        System.out.println("request.getFinalDate().getYear() = " + request.getFinalDate().getYear());
    }
}
