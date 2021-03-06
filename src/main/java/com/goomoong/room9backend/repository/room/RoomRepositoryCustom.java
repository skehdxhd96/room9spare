package com.goomoong.room9backend.repository.room;

import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.dto.searchDto;
import com.goomoong.room9backend.domain.user.User;

import java.util.List;

public interface RoomRepositoryCustom {

    List<Room> findRoomWithFilter(searchDto search);

    List<Room> findMyRoom(User user);
}
