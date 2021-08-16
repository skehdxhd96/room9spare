package com.goomoong.room9backend.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewDto{

    private Long id;
    private String reviewContent;
    private LocalDateTime reviewCreated;
    private int reviewScore;
}
