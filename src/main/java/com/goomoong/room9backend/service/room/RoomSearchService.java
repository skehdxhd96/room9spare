package com.goomoong.room9backend.service.room;

import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.dto.searchDto;
import com.goomoong.room9backend.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public List<Room> randSearch(){
        long count = roomRepository.count()/2;
        int idx = (int)(Math.random() * count);
        Page<Room> roomPage = roomRepository.findAll(PageRequest.of(idx, 2));
        return roomPage.getContent();
    }
}
