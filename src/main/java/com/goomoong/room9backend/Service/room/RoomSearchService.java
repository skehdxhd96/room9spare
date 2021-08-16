package com.goomoong.room9backend.Service.room;

import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.dto.searchDto;
import com.goomoong.room9backend.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomSearchService {

    private final RoomRepository roomRepository;

    public List<Room> search(searchDto search) {
        return roomRepository.findRoomWithFilter(search);
    }
}
