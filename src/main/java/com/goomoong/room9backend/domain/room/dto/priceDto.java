package com.goomoong.room9backend.domain.room.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class priceDto {

    @Positive
    private Integer personnel; // 인원
    private String startDate;
    private String finalDate;
}
