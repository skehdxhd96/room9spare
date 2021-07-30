package com.goomoong.room9backend.domain.review.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateReviewRequestDto {

    private String reviewContent;
    private int reviewScore;
}
