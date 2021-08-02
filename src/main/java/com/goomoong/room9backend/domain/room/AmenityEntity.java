package com.goomoong.room9backend.domain.room;

import com.goomoong.room9backend.domain.room.dto.amenityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class AmenityEntity {

    @Id @GeneratedValue
    private Long id;

    private Amenity amenity;

    public AmenityEntity(String facility) {
        this.amenity = new Amenity(facility);
    }
}
