package com.goomoong.room9backend.Service.room;

import com.goomoong.room9backend.Service.file.FileService;
import com.goomoong.room9backend.config.FolderConfig;
import com.goomoong.room9backend.repository.room.RoomImgRepository;
import com.goomoong.room9backend.repository.room.RoomRepository;
import com.goomoong.room9backend.domain.file.File;
import com.goomoong.room9backend.domain.file.RoomImg;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.dto.CreatedRequestRoomDto;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.exception.NoSuchRoomException;
import com.goomoong.room9backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final FolderConfig folderConfig;

    //방 생성
    @Transactional
    public Long addRoom(CreatedRequestRoomDto request) {

        User user = userRepository.findById(request.getUserId()).get();
        List<File> files = fileService.uploadFiles(folderConfig.getRoom(), request.getImages());
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
