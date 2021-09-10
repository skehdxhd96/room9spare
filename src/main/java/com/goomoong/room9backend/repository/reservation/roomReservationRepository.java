package com.goomoong.room9backend.repository.reservation;

import com.goomoong.room9backend.domain.reservation.roomReservation;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roomReservationRepository extends JpaRepository<roomReservation, Long> {
    roomReservation findByRoomAndUsers(Room room, User user);
}
