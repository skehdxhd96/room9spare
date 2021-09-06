package com.goomoong.room9backend.service.room;

import com.goomoong.room9backend.domain.room.Amenity;
import com.goomoong.room9backend.domain.room.RoomConfiguration;
import com.goomoong.room9backend.domain.room.dto.GetCommonRoom;
import com.goomoong.room9backend.domain.room.dto.priceDto;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.exception.RoomNotAddException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.goomoong.room9backend.util.AboutDate.compareDay;
import static com.goomoong.room9backend.util.AboutDate.getLocalDateTimeFromString;

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
    public Long addRoom(CreatedRequestRoomDto request) throws IOException {

        User user = userService.findById(request.getUserId());
        Set<RoomConfiguration> roomConfig = RoomConfiguration.createRoomConfig(request.getConf());
        Set<Amenity> amenities = Amenity.createRoomFacility(request.getFacilities());

        if(user.getRole() != Role.HOST) {
            throw new RoomNotAddException();
        }

        List<File> files = fileService.uploadFiles(folderConfig.getRoom(), request.getImages());
        Room room = Room.createRoom(user, request, roomConfig, amenities);
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

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public long getTotalPrice(Long id, priceDto priceDto) {

        Room room = getRoomDetail(id);
        long days = compareDay(priceDto.getStartDate(), priceDto.getFinalDate());

        if(room.getLimited() < priceDto.getPersonnel()) {
            int addCharge = room.getCharge() * (priceDto.getPersonnel() - room.getLimited());
            return addCharge + room.getPrice() * days;
        }
        return room.getPrice() * days;
    }
}
