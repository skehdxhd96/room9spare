package com.goomoong.room9backend.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
public class Amenity {

    @Id @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Room_Id")
    private Room room;

    private String facility; // 부대시설

    // 양방향 연관관계 메서드 //
    public void setRoom(Room room) {
        this.room = room;
        room.getAmenities().add(this);
    }

    //== 생성 메소드 ==//
    public static Amenity createAmenities(String facility) {

        Amenity amenity = new Amenity();
        amenity.facility = facility;

        return amenity;
    }

    public void update(String facility) {
        this.facility = facility;
    }
}
