package com.goomoong.room9backend.service.reservation;

import com.goomoong.room9backend.domain.reservation.ReserveStatus;
import com.goomoong.room9backend.domain.reservation.dto.ReservationDto;
import com.goomoong.room9backend.domain.reservation.roomReservation;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.dto.priceDto;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.exception.DuplicateDateException;
import com.goomoong.room9backend.exception.ReservationNotAddException;
import com.goomoong.room9backend.repository.reservation.roomReservationRepository;
import com.goomoong.room9backend.repository.room.RoomRepository;
import com.goomoong.room9backend.service.UserService;
import com.goomoong.room9backend.service.room.RoomService;
import com.goomoong.room9backend.util.AboutDate;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class reservationService {

    private final roomReservationRepository roomReservationRepository;
    private final RoomRepository roomRepository;
    private final RoomService roomService;
    private final UserService userService;

    @Transactional
    public void reserveRoom(User user, Long roomId, ReservationDto.request request) throws Exception{

    }

    public boolean reserve_OK(ReservationDto.request request,Long roomId) {

        List<ReservationDto.booked> books = getAllBookingList(roomId);

        for (ReservationDto.booked book : books) {
            if (AboutDate.compareDay(request.getFinalDate(), book.getStartDate()) >= 1 ||
                    AboutDate.compareDay(book.getFinalDate(), request.getStartDate()) >= 1) {
                continue;
            }
            return false;
        }
        return true;
    }

    public List<ReservationDto.booked> getAllBookingList(Long roomId) {
        List<roomReservation> books = roomReservationRepository.getAllList(roomId);

        List<ReservationDto.booked> finalList = new ArrayList<>();
        for (roomReservation book : books) {
            finalList.add(ReservationDto.booked.builder()
                    .startDate(AboutDate.getStringFromLocalDateTime(book.getStartDate()))
                    .finalDate(AboutDate.getStringFromLocalDateTime(book.getFinalDate()))
                    .build());
        }

        return finalList;
    }

    public List<ReservationDto.MyList> getMyAllBook(Long userId) {
        return roomReservationRepository.getMyBookList(userId).stream()
                .map(a -> new ReservationDto.MyList(a)).collect(Collectors.toList());
    }
}
