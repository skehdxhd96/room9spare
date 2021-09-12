package com.goomoong.room9backend.repository.reservation;

import com.goomoong.room9backend.domain.reservation.ReserveStatus;
import com.goomoong.room9backend.domain.reservation.roomReservation;
import com.goomoong.room9backend.repository.room.RoomRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.goomoong.room9backend.domain.reservation.QroomReservation.roomReservation;

@RequiredArgsConstructor
public class roomReservationRepositoryImpl implements roomReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<roomReservation> findByRoomId(Long roomId) {
        return queryFactory.select(roomReservation)
                .from(roomReservation)
                .join(roomReservation.users).fetchJoin()
                .join(roomReservation.room).fetchJoin()
                .where(roomReservation.room.id.eq(roomId))
                .fetch();
    }
}
