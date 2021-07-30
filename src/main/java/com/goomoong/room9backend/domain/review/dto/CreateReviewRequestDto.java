package com.goomoong.room9backend.domain.review.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateReviewRequestDto {

    private Long user_id;
    private Long room_id;
    private String reviewContent;
    private int reviewScore;
}
