package com.goomoong.room9backend.service;

import com.goomoong.room9backend.domain.Room;
import com.goomoong.room9backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room findById(Long id){
        return roomRepository.findById(id).orElse(null);
    }
}
