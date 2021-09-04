package com.goomoong.room9backend.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewDto{

    private Long id;
    private String reviewContent;
    private LocalDateTime reviewCreated;
    private LocalDateTime reviewUpdated;
    private int reviewScore;
}
