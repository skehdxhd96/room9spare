package com.goomoong.room9backend.service;

import com.goomoong.room9backend.config.FolderConfig;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.repository.user.UserRepository;
import com.goomoong.room9backend.service.file.FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private FileService fileService;
    @Mock
    private FolderConfig folderConfig;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder().id(1L).nickname("mock").role(Role.GUEST).thumbnailImgUrl("mock.jpg").email("mock@abc").birthday("0101").gender("male").intro("test").build();
    }

    @Test
    @DisplayName("전체 유저 조회")
    public void findAllUsersTest() throws Exception {
        //given
        List<User> users = new ArrayList<>();
        users.add(user);

        given(userRepository.findAll()).willReturn(users);

        //when
        List<User> findUsers = userService.findAllUsers();

        //then
        Assertions.assertEquals(user.getName(), findUsers.get(0).getName());
    }

    @Test
    @DisplayName("Id로 유저 찾기")
    public void findByIdTest() throws Exception {
        //given
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        //when
        User findUser = userService.findById(1L);

        //then
        Assertions.assertEquals(user, findUser);
    }

    @Test
    @DisplayName("정보 업데이트")
    public void updateTest() throws Exception {
        //given
        String updateNickname = "update";
        MockMultipartFile mockFile = mock(MockMultipartFile.class);
        String updateThumbnailUrl = "update.jpg";
        String updateIntro = "hello world";

        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(fileService.uploadThumbnailImage(folderConfig.getUser(), mockFile)).willReturn(updateThumbnailUrl);

        //when
        userService.update(1L, updateNickname, mockFile, null, null, null, updateIntro);

        //then
        Assertions.assertEquals(user.getNickname(), updateNickname);
        Assertions.assertEquals(user.getThumbnailImgUrl(), updateThumbnailUrl);
        Assertions.assertEquals(user.getIntro(), updateIntro);
    }

    @Test
    @DisplayName("사용자 전환")
    public void changeRoleTest() throws Exception {
        //given
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        //when
        userService.changeRole(1L);

        //then
        Assertions.assertEquals(user.getRole(), Role.HOST);
    }
}