package com.goomoong.room9backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goomoong.room9backend.config.MockSecurityFilter;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.domain.user.dto.UpdateRequestDto;
import com.goomoong.room9backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static com.goomoong.room9backend.ApiDocumentUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.config.location=" +
        "classpath:application.yml," +
        "classpath:aws.yml")
public class UserApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(RestDocumentationContextProvider restDocumentation) {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .apply(springSecurity(new MockSecurityFilter()))
                .build();
    }

    @Test
    @DisplayName("전체 회원 조회 api 테스트")
    public void findAllUsersApiTest() throws Exception {
        //given
        User user = User.builder().id(1L).accountId("1").name("mock").nickname("mock").role(Role.GUEST).thumbnailImgUrl("mock.jpg").email("mock@abc").birthday("0101").gender("male").intro("test").build();
        List<User> userList = new ArrayList<>();
        userList.add(user);

        given(userService.findAllUsers()).willReturn(userList);

        //when
        ResultActions result = mvc.perform(get("/api/v1/users"));


        //then
        result
                .andDo(print())
                .andDo(document("user-getAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("users.[].id").description("user id").type(Long.class),
                                fieldWithPath("users.[].nickname").description("user nickname"),
                                fieldWithPath("users.[].thumbnailImgUrl").description("user thumbnail image url"),
                                fieldWithPath("users.[].email").description("user email"),
                                fieldWithPath("users.[].birthday").description("user birthday"),
                                fieldWithPath("users.[].gender").description("user gender"),
                                fieldWithPath("users.[].intro").description("user introduction")
                        )
                ))
                .andExpect(jsonPath("$.users[0].id").value(1L))
                .andExpect(jsonPath("$.users[0].nickname").value("mock"))
                .andExpect(jsonPath("$.users[0].thumbnailImgUrl").value("mock.jpg"))
                .andExpect(jsonPath("$.users[0].email").value("mock@abc"))
                .andExpect(jsonPath("$.users[0].birthday").value("0101"))
                .andExpect(jsonPath("$.users[0].gender").value("male"))
                .andExpect(jsonPath("$.users[0].intro").value("test"));
    }

    @Test
    @DisplayName("Id로 회원 조회 api 테스트")
    public void findUserByIdApiTest() throws Exception {
        //given
        User user = User.builder().id(1L).nickname("mock").thumbnailImgUrl("mock.jpg").email("mock@abc").birthday("0101").gender("male").intro("test").build();
        given(userService.findById(1L)).willReturn(user);

        //when
        ResultActions result = mvc.perform(RestDocumentationRequestBuilders.get("/api/v1/users/{id}", 1L));

        //then
        result
                .andDo(print())
                .andDo(document("user-getById",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("user id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("user id").type(Long.class),
                                fieldWithPath("nickname").description("user nickname"),
                                fieldWithPath("thumbnailImgUrl").description("user thumbnail image url"),
                                fieldWithPath("email").description("user email"),
                                fieldWithPath("birthday").description("user birthday"),
                                fieldWithPath("gender").description("user gender"),
                                fieldWithPath("intro").description("user introduction")
                        )
                ))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nickname").value("mock"))
                .andExpect(jsonPath("$.thumbnailImgUrl").value("mock.jpg"))
                .andExpect(jsonPath("$.email").value("mock@abc"))
                .andExpect(jsonPath("$.birthday").value("0101"))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.intro").value("test"));
    }

    @Test
    @DisplayName("사용자 전환 테스트")
    public void changeRoleApiTest() throws Exception {
        //given
        User user = User.builder().id(1L).role(Role.GUEST).build();
        given(userService.changeRole(1L)).willReturn(user);

        //when
        ResultActions result = mvc.perform(RestDocumentationRequestBuilders.post("/api/v1/users/{id}/role", 1L));

        //then
        result
                .andDo(print())
                .andDo(document("user-changeRole",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("user id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("user id")
                        )
                ))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("사용자 정보 수정 테스트")
    public void updateApiTest() throws Exception {
        //given
        UpdateRequestDto updateRequestDto = UpdateRequestDto.builder()
                .nickname("update").thumbnailImgUrl("update.jpg").email("update@abc").birthday("1231").gender("female").intro("update")
                .build();
        User response = User.builder()
                .id(1L).nickname("update").thumbnailImgUrl("update.jpg").email("update@abc").birthday("1231").gender("female").intro("update")
                .build();

        given(userService.update(1L, updateRequestDto.getNickname(), updateRequestDto.getThumbnailImgUrl(), updateRequestDto.getEmail(), updateRequestDto.getBirthday(), updateRequestDto.getGender(), updateRequestDto.getIntro())).willReturn(response);

        //when
        ResultActions result = mvc.perform(RestDocumentationRequestBuilders.post("/api/v1/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(updateRequestDto))
        );

        //then
        result
                .andDo(print())
                .andDo(document("user-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("user id")
                        ),
                        requestFields(
                                fieldWithPath("nickname").description("user nickname"),
                                fieldWithPath("thumbnailImgUrl").description("user thumbnail image url"),
                                fieldWithPath("email").description("user email"),
                                fieldWithPath("birthday").description("user birthday"),
                                fieldWithPath("gender").description("user gender"),
                                fieldWithPath("intro").description("user introduction")
                        ),
                        responseFields(
                                fieldWithPath("id").description("user id").type(Long.class)
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
