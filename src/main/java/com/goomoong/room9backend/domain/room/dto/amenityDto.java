package com.goomoong.room9backend.domain.room.dto;

import com.goomoong.room9backend.domain.room.Amenity;
import com.goomoong.room9backend.domain.room.AmenityEntity;
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

    public amenityDto(AmenityEntity amenityEntity) {
        this.facility = amenityEntity.getAmenity().getFacility();
    }
}
