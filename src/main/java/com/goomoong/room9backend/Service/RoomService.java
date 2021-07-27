package com.goomoong.room9backend.Service;

import com.goomoong.room9backend.Repository.RoomRepository;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.dto.CreatedRequestRoomDto;
import com.goomoong.room9backend.domain.room.dto.UpdateRequestRoomDto;
import com.goomoong.room9backend.domain.room.dto.confDto;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    //방 생성
    @Transactional
    public Long addRoom(CreatedRequestRoomDto request) {

        User user = userRepository.findById(request.getUserId()).get();

        List<confDto> conf = request.getConf();
        List<String> facilities = request.getFacilities();

        Room room = Room.createRoom(user, conf, request.getLimit(), request.getPrice(), request.getTitle(),
                                    request.getContent(), request.getDetailLocation(),
                                    request.getRule(), request.getAddCharge(), facilities);

        roomRepository.save(room);

        return room.getId();
    }

    //방 수정
    @Transactional
    public void updateRoom(Long id, UpdateRequestRoomDto request) {
        Room room = roomRepository.findOne(id);

        List<confDto> conf = request.getConf();
        List<String> facilities = request.getFacilities();

        room.modify(conf, request.getLimit(), request.getPrice(), request.getTitle(),
                request.getContent(), request.getDetailLocation(),
                request.getRule(), request.getAddCharge(), facilities);
    }

    //방 삭제
}
