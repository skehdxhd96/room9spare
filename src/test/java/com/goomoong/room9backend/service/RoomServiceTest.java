package com.goomoong.room9backend.service;

import com.goomoong.room9backend.config.FolderConfig;
import com.goomoong.room9backend.config.QuerydslConfig;
import com.goomoong.room9backend.domain.file.File;
import com.goomoong.room9backend.domain.file.RoomImg;
import com.goomoong.room9backend.domain.room.Amenity;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.RoomConfiguration;
import com.goomoong.room9backend.domain.room.dto.CreatedRequestRoomDto;
import com.goomoong.room9backend.domain.room.dto.amenityDto;
import com.goomoong.room9backend.domain.room.dto.confDto;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.repository.room.RoomImgRepository;
import com.goomoong.room9backend.repository.room.RoomRepository;
import com.goomoong.room9backend.repository.user.UserRepository;
import com.goomoong.room9backend.service.file.FileService;
import com.goomoong.room9backend.service.room.RoomService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Spy
    @InjectMocks
    private RoomService roomService;
    @Mock
    private FileService fileService;
    @Mock
    private RoomImgRepository roomImgRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private FolderConfig folderConfig;

    private static MockedStatic<RoomConfiguration> mRoomConfiguration;
    private static MockedStatic<Amenity> mAmenity;
    private static MockedStatic<Room> mRoom;

    @BeforeEach
    public void BeforeClass() {
        mRoomConfiguration = mockStatic(RoomConfiguration.class);
        mAmenity = mockStatic(Amenity.class);
        mRoom = mockStatic(Room.class);
    }

    @AfterEach
    public void afterClass() {
        mRoomConfiguration.close();
        mAmenity.close();
        mRoom.close();
    }

    @Test
    @DisplayName("부대시설/구성요소/이미지가 있는 숙소 생성 테스트")
    public void 숙소생성하기() throws Exception {
        //given
        User user = mock(User.class);
        List<File> testFiles = new ArrayList<>();
        List<MultipartFile> testList1 = new ArrayList<>();
        List<confDto> testList2 = new ArrayList<>();
        List<amenityDto> testList3 = new ArrayList<>();
        File testFile = mock(File.class);
        MultipartFile testMultipartFile = mock(MultipartFile.class);
        RoomConfiguration testConf = mock(RoomConfiguration.class);
        Amenity testAmenity = mock(Amenity.class);
        testFiles.add(testFile);
        testList1.add(testMultipartFile);
        testList2.add(new confDto(testConf));
        testList3.add(new amenityDto(testAmenity));
        Set<RoomConfiguration> testhrc = new HashSet<>();
        Set<Amenity> testha = new HashSet<>();
        CreatedRequestRoomDto request = mock(CreatedRequestRoomDto.class);
        Room testRoom = mock(Room.class);

        given(userService.findById(anyLong())).willReturn(user);
        given(RoomConfiguration.createRoomConfig(testList2)).willReturn(testhrc);
        given(Amenity.createRoomFacility(testList3)).willReturn(testha);
        given(user.getRole()).willReturn(Role.HOST);
        given(Room.createRoom(user, request, testhrc, testha)).willReturn(testRoom);
        given(testRoom.getId()).willReturn(1L);

        //when
        Long savedRoomId = roomService.addRoom(request, user);

        //then
        Assertions.assertEquals(testRoom.getId(), savedRoomId);
    }
}
