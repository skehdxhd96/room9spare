package com.goomoong.room9backend.domain.room.dto;

import com.goomoong.room9backend.domain.room.Amenity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class amenityDto {

    private String facility;

    public amenityDto(Amenity amenity) {
        this.facility = amenity.getFacility();
    }
}
