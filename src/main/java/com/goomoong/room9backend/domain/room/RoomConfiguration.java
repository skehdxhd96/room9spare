package com.goomoong.room9backend.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goomoong.room9backend.domain.room.dto.confDto;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
public class RoomConfiguration {

    @Id @GeneratedValue
    @Column(name = "Configuration_Id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Room_Id")
    private Room room;

    private String confType;
    private int count;

    //== 연관관계 편의 메소드 ==//
    public void setRoom(Room room) {
        this.room = room;
        room.getRoomConfigurations().add(this);
    }

    // 연관관계 끊기 //
    public void Cancel(Room room) {
        this.room = null;
        room.getRoomConfigurations().remove(this);
    }

    //== 생성 메소드 ==//
    public static RoomConfiguration createConfiguration(String confType, int count) {
        RoomConfiguration roomConfiguration = new RoomConfiguration();
        roomConfiguration.confType = confType;
        roomConfiguration.count = count;

        return roomConfiguration;
    }
}
