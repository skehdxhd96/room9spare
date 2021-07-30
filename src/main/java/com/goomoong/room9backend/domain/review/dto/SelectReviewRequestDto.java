package com.goomoong.room9backend.domain.review.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelectReviewRequestDto {

    private Long user_id;
    private Long room_id;
}
