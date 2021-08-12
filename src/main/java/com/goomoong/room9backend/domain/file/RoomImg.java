package com.goomoong.room9backend.domain.file;

import com.goomoong.room9backend.domain.room.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
public class RoomImg {

    @Id @GeneratedValue
    @Column(name = "RoomImgId")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "File_Id")
    public File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Room_Id")
    public Room room;

    public static RoomImg create(File file, Room room) {
        RoomImg roomImg = new RoomImg();
        roomImg.file = file;
        roomImg.room = room;

        return roomImg;
    }
}
