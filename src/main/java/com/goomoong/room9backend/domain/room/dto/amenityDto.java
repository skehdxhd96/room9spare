package com.goomoong.room9backend.domain.room.dto;

import com.goomoong.room9backend.domain.room.Amenity;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class amenityDto {

    private String facility;

    public amenityDto(Amenity amenity) {
        this.facility = amenity.getFacility();
    }
}