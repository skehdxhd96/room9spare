package com.goomoong.room9backend.repository.reservation;

import com.goomoong.room9backend.domain.reservation.roomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roomReservationRepository extends JpaRepository<roomReservation, Long> {
}
