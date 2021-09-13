package com.goomoong.room9backend.service.room;

import com.goomoong.room9backend.domain.review.dto.scoreDto;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.dto.GetCommonRoom;
import com.goomoong.room9backend.domain.room.dto.searchDto;
import com.goomoong.room9backend.repository.room.RoomRepository;
import com.goomoong.room9backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomSearchService {

    private final RoomRepository roomRepository;
    private final ReviewService reviewService;

    public List<GetCommonRoom> search(searchDto search) {

        List<Room> filterRooms = roomRepository.findRoomWithFilter(search);
        List<GetCommonRoom> roomList = new ArrayList<>();
        for (Room filterRoom : filterRooms) {
            scoreDto scoredto = reviewService.getAvgScoreAndCount(filterRoom.getId());
            roomList.add(new GetCommonRoom(filterRoom, scoredto));
        }
        return roomList;
    }

    public List<GetCommonRoom> randSearch(){
        long count = roomRepository.count();
        int idx = (int)(Math.random() * count);
        Page<Room> roomPages = roomRepository.findAll(PageRequest.of(idx, 2));

        List<GetCommonRoom> roomList = new ArrayList<>();
        for (Room roomPage : roomPages.getContent()) {
            scoreDto scoredto = reviewService.getAvgScoreAndCount(roomPage.getId());
            roomList.add(new GetCommonRoom(roomPage, scoredto));
        }
        return roomList;
    }
}
