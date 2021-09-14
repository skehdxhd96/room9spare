package com.goomoong.room9backend.domain.reservation.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.goomoong.room9backend.domain.reservation.roomReservation;
import lombok.*;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class request {

        private String startDate;
        private String finalDate;
        private Integer personnel;
        private Boolean petWhether;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class response {
        private Long reservationId;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class booked {
        private String startDate;
        private String finalDate;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyList {

        private Integer personnel;
        private LocalDateTime startDate;
        private LocalDateTime finalDate;
        private String detailLocation;
        private String title;

        public MyList(roomReservation roomReservation) {
            this.personnel = roomReservation.getPersonnel();
            this.startDate = roomReservation.getStartDate();
            this.finalDate = roomReservation.getStartDate();
            this.detailLocation = roomReservation.getRoom().getDetailLocation();
            this.title = roomReservation.getRoom().getTitle();
        }
    }
}
