package com.goomoong.room9backend.service;

import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.domain.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

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
        String updateThumbnailUrl = "update.jpg";
        String updateIntro = "hello world";

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        //when
        userService.update(1L, updateNickname, updateThumbnailUrl, null, null, null, updateIntro);

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