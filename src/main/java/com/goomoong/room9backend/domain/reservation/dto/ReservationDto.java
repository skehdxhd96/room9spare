package com.goomoong.room9backend.domain.reservation.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
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
        private Long userId; // 빼야돼
        private requestDate startDate;
        private requestDate finalDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class requestDate {
        private Integer day;
        private Integer month;
        private Integer year;
    }
}
