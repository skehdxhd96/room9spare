package com.goomoong.room9backend.service;

import com.goomoong.room9backend.config.FolderConfig;
import com.goomoong.room9backend.domain.room.Amenity;
import com.goomoong.room9backend.domain.room.RoomConfiguration;
import com.goomoong.room9backend.repository.room.RoomImgRepository;
import com.goomoong.room9backend.repository.room.RoomRepository;
import com.goomoong.room9backend.service.file.FileService;
import com.goomoong.room9backend.service.room.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Spy @InjectMocks
    private RoomService roomService;
    @Mock
    private FileService fileService;
    @Mock
    private RoomImgRepository roomImgRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private UserService userService;
    @Mock
    private FolderConfig folderConfig;

}
