package com.goomoong.room9backend.util;

import com.goomoong.room9backend.domain.reservation.dto.ReservationDto;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class AboutDate {
    public static LocalDateTime getLocalDateTimeFromString(String date) {
        LocalDate selectDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime currentTime = LocalTime.now();
        LocalDateTime returnDate = LocalDateTime.of(selectDate, currentTime);
        return returnDate;
    }

    public static String getStringFromLocalDateTime(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static long compareDay(String date1, String date2) {
        LocalDate startDate = LocalDate.parse(date1, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate finalDate = LocalDate.parse(date2, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        long days = ChronoUnit.DAYS.between(startDate, finalDate);
        return days;
    }

    public static boolean reservePossible(ReservationDto.request request, LocalDateTime date1, LocalDateTime date2) {
        return true;
    }
}
