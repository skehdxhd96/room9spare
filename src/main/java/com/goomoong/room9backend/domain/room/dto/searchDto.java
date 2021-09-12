package com.goomoong.room9backend.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class searchDto {
    private String title;
    @Positive
    private Integer limitPrice;
    private String detailLocation;
    @Positive
    private Integer limitPeople;
    private OrderDto orderStandard;
}
