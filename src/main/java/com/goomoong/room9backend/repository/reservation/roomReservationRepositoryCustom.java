package com.goomoong.room9backend.repository.reservation;

import com.goomoong.room9backend.domain.reservation.roomReservation;

import java.util.List;

public interface roomReservationRepositoryCustom {

    /**
     * Reserve_Status와 roomId를 이용한 예약 테이블 조회
     */
    List<roomReservation> findByRoomId(Long roomId);

}
