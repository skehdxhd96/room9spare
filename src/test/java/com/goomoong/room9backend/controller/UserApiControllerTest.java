package com.goomoong.room9backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserApiController.class)
public class UserApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("전체 회원 조회 api 테스트")
    public void findAllUsersApiTest() throws Exception {
        //given
        User user = User.builder().accountId("1").role(Role.CUSTOMER).name("name").thumbnailUrl("test.jpg").build();
        List<User> userList = new ArrayList<>();
        userList.add(user);

        given(userService.findAllUsers()).willReturn(userList);

        //when
        ResultActions result = mvc.perform(get("/api/v1/users"));

        //then
        result
                .andDo(print())
                .andExpect(jsonPath("$[0].accountId").value("1"))
                .andExpect(jsonPath("$[0].role").value("CUSTOMER"))
                .andExpect(jsonPath("$[0].name").value("name"))
                .andExpect(jsonPath("$[0].thumbnailUrl").value("test.jpg"));

    }

    @Test
    @DisplayName("Id로 회원 조회 api 테스트")
    public void findUserByIdApiTest() throws Exception {
        //given
        User user = User.builder().thumbnailUrl("test.jpg").name("name").intro("hello").build();
        given(userService.findById(1L)).willReturn(user);

        //when
        ResultActions result = mvc.perform(get("/api/v1/users/{id}", 1L));

        //then
        result
                .andDo(print())
                .andExpect(jsonPath("$.thumbnailUrl").value("test.jpg"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.intro").value("hello"));
    }

    @Test
    @DisplayName("사용자 전환 테스트")
    public void changeRoleApiTest() throws Exception {
        //given
        User user = User.builder().accountId("1").role(Role.CUSTOMER).name("name").thumbnailUrl("test.jpg").build();

        given(userService.findById(1L)).willReturn(user);

        //when
        ResultActions result = mvc.perform(post("/api/v1/users/{id}/role", 1L));

        //then
        result
                .andDo(print())
                .andExpect(jsonPath("$.role").value("SELLER"));
    }

    @Test
    @DisplayName("사용자 정보 수정 테스트")
    public void updateApiTest() throws Exception {
        //given
        UserApiController.UpdateRequestDto updateRequestDto = UserApiController.UpdateRequestDto.builder().intro("update").thumbnailUrl("update.jpg").build();
        User response = User.builder().thumbnailUrl("update.jpg").name("name").intro("update").build();

        given(userService.update(1L, updateRequestDto.getThumbnailUrl(), updateRequestDto.getIntro())).willReturn(response);

        //when
        ResultActions result = mvc.perform(post("/api/v1/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(updateRequestDto))
        );

        //then
        result
                .andDo(print())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.thumbnailUrl").value("update.jpg"))
                .andExpect(jsonPath("$.intro").value("update"));
    }
}
