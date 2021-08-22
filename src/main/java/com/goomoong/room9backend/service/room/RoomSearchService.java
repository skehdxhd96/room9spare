package com.goomoong.room9backend.service.room;

import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.dto.GetCommonRoom;
import com.goomoong.room9backend.domain.room.dto.searchDto;
import com.goomoong.room9backend.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomSearchService {

    private final RoomRepository roomRepository;

    public List<GetCommonRoom> search(searchDto search) {
        return roomRepository.findRoomWithFilter(search).stream()
                .map(r -> new GetCommonRoom(r)).collect(Collectors.toList());
    }
}
