package com.goomoong.room9backend.repository.roomRepository;

import com.goomoong.room9backend.domain.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
