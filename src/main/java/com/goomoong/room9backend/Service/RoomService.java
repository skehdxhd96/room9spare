package com.goomoong.room9backend.Service;

import com.goomoong.room9backend.Repository.RoomImgRepository;
import com.goomoong.room9backend.Repository.RoomRepository;
import com.goomoong.room9backend.domain.file.File;
import com.goomoong.room9backend.domain.file.RoomImg;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.dto.CreatedRequestRoomDto;
import com.goomoong.room9backend.domain.room.dto.confDto;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.domain.user.UserRepository;
import com.goomoong.room9backend.exception.NoSuchRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final FileService fileService;
    private final RoomImgRepository roomImgRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    //방 생성
    @Transactional
    public Long addRoom(CreatedRequestRoomDto request) {

        User user = userRepository.findById(request.getUserId()).get();
        List<File> files = fileService.uploadFiles(request.getImages());
        Room room = Room.createRoom(user, request);
        List<RoomImg> roomImgs = new ArrayList<>();

        for (File file : files) {
            roomImgs.add(RoomImg.create(file, room));
        }
        roomRepository.save(room);
        roomImgRepository.saveAll(roomImgs);

        return room.getId();
    }

    public Room getRoomDetail(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new NoSuchRoomException("존재하지 않는 방입니다."));
    }
}
