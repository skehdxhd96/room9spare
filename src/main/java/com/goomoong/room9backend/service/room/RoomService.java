package com.goomoong.room9backend.service.room;

import com.goomoong.room9backend.domain.room.dto.GetCommonRoom;
import com.goomoong.room9backend.service.UserService;
import com.goomoong.room9backend.service.file.FileService;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final FileService fileService;
    private final RoomImgRepository roomImgRepository;
    private final RoomRepository roomRepository;
    private final UserService userService;
    private final FolderConfig folderConfig;

    //방 생성
    @Transactional
    public Long addRoom(CreatedRequestRoomDto request) {

        User user = userService.findById(request.getUserId());
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

    public List<GetCommonRoom> findAll() {
        return roomRepository.findAll()
                .stream().map(r -> new GetCommonRoom(r)).collect(Collectors.toList());
    }

    public List<GetCommonRoom> findTop5CreatedDate() {
        return roomRepository.findTop5ByOrderByCreatedDateDesc()
                .stream().map(r -> new GetCommonRoom(r)).collect(Collectors.toList());
    }

    public List<GetCommonRoom> findTop5Liked() {
        return roomRepository.findTop5ByOrderByLikedDesc()
                .stream().map(r -> new GetCommonRoom(r)).collect(Collectors.toList());
    }
}
