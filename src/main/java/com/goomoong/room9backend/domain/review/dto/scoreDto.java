package com.goomoong.room9backend.domain.review.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class scoreDto {

    private Double avgScore;
    private Integer reviewCount;

    public scoreDto() {
        this.avgScore = 0.0;
        this.reviewCount = 0;
    }
}
