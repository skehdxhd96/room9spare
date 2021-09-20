package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.config.MockSecurityFilter;
import com.goomoong.room9backend.domain.file.File;
import com.goomoong.room9backend.domain.file.RoomImg;
import com.goomoong.room9backend.domain.reservation.dto.ReservationDto;
import com.goomoong.room9backend.domain.review.dto.scoreDto;
import com.goomoong.room9backend.domain.room.Amenity;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.RoomConfiguration;
import com.goomoong.room9backend.domain.room.dto.GetCommonRoom;
import com.goomoong.room9backend.domain.room.dto.GetDetailRoom;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.security.userdetails.CustomUserDetails;
import com.goomoong.room9backend.service.reservation.reservationService;
import com.goomoong.room9backend.service.room.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.goomoong.room9backend.ApiDocumentUtils.getDocumentRequest;
import static com.goomoong.room9backend.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ReservationApiControllerTest {

    @MockBean
    private reservationService reservationService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    private User user;
    private Room room;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .apply(springSecurity(new MockSecurityFilter()))
                .build();

        user = User.builder()
                .id(1L)
                .accountId("1")
                .name("mock")
                .nickname("mockusername")
                .role(Role.GUEST)
                .thumbnailImgUrl("mock.jpg")
                .email("mock@abc")
                .birthday("0101")
                .gender("male")
                .intro("test").build();

        Set<RoomConfiguration> rset = new LinkedHashSet<>();
        rset.add(RoomConfiguration.builder().confType("화장실").count(3).build());
        rset.add(RoomConfiguration.builder().confType("방").count(5).build());

        Set<Amenity> aset = new LinkedHashSet<>();
        aset.add(Amenity.builder().facility("전자레인지").build());
        aset.add(Amenity.builder().facility("도어락").build());


        File file1 = File.builder().id(1L).fileName("test1").originalName("test1.png").extension("png").url("https://roomimg.s3.ap-northeast-2.amazonaws.com/도메인이름/랜덤으로생성된느번호pngFIle.png").build();
        File file2 = File.builder().id(2L).fileName("test2").originalName("test2.jpg").extension("jpg").url("https://roomimg.s3.ap-northeast-2.amazonaws.com/도메인이름/랜덤으로생성된느번호jpgFIle.jpg").build();

        List<RoomImg> rllist = new ArrayList<>();
        rllist.add(RoomImg.builder().id(1L).file(file1).build());
        rllist.add(RoomImg.builder().id(2L).file(file2).build());

        room = Room.builder()
                .id(1L)
                .users(user)
                .roomConfigures(rset)
                .amenities(aset)
                .roomImg(rllist)
                .limited(10)
                .price(10000)
                .title("아메리카노")
                .content("내용1입니다.")
                .detailLocation("서울")
                .rule("상세페이지 들어가기 전 간단한 정보만 표기합니다.")
                .charge(1000)
                .liked(3).build();
    }

    @Test
    @DisplayName(value = "숙소 예약 api")
    public void reserveRoom() throws Exception{
        //given
        ReservationDto.response testResponse = ReservationDto.response.builder()
                .reservationId(1L)
                .title(room.getTitle())
                .detailLocation(room.getDetailLocation())
                .rule(room.getRule())
                .petWhether(true)
                .totalAmount(100000)
                .startDate("2021-09-20")
                .finalDate("2021-09-21")
                .reserveSuccess(true)
                .errorMsg("reserveSuccess가 true이면 null")
                .build();
        given(reservationService.reserveRoom(any(), any(),any())).willReturn(testResponse);

        //when
        ResultActions result = mvc.perform(post("/room/book/{roomId}", 1L)
                .param("startDate","2021-09-20")
                .param("finalDate","2021-09-21")
                .param("personnel", "4")
                .param("petWhether", "true")
                .param("aboutPayment", "{\"paymentId\":\"merchant_uid\",\"paymentMethod\":\"kakaopay\",\"paymentStatus\":true, \"paymentAmount\":100000, \"paymentErrorMsg\":\"error_msg\"}")
                .principal(new UsernamePasswordAuthenticationToken(CustomUserDetails.create(user), null))
                .header("Authorization", "Bearer accessToken")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        result
                .andDo(print())
                .andDo(document("reserve-room",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("카카오 사용자 Bearer Token")
                        ),
                        pathParameters(
                                parameterWithName("roomId").description("숙소 아이디")
                        ),
                        requestParameters(
                                parameterWithName("startDate").description("예약시작날짜(yyyy-mm-dd)"),
                                parameterWithName("finalDate").description("예약마지막날짜(yyyy-mm-dd)"),
                                parameterWithName("personnel").description("총 인원"),
                                parameterWithName("petWhether").description("반려견 여부"),
                                parameterWithName("aboutPayment").description("결제 데이터(" +
                                        "paymentId : 아임포트 merchant_uid parameter" +
                                        "paymentMethod : 아임포트 pay_method parameter" +
                                        "paymentStatus : 아임포트 success parameter" +
                                        "paymentAmount : 아임포트 paid_amount parameter" +
                                        "paymentErrorMsg : 아임포트 error_msg parameter(결제 성공일 경우 null)")
                        ),
                        responseFields(
                                fieldWithPath("reservationId").type(JsonFieldType.NUMBER).description("예약 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("숙소 제목"),
                                fieldWithPath("detailLocation").type(JsonFieldType.STRING).description("숙소 위치"),
                                fieldWithPath("rule").type(JsonFieldType.STRING).description("숙소 규칙"),
                                fieldWithPath("petWhether").type(JsonFieldType.BOOLEAN).description("반려견 여부"),
                                fieldWithPath("totalAmount").type(JsonFieldType.NUMBER).description("총 가격"),
                                fieldWithPath("startDate").type(JsonFieldType.STRING).description("예약 시작 날짜(yyyy-mm-dd)"),
                                fieldWithPath("finalDate").type(JsonFieldType.STRING).description("예약 마지막 날짜(yyyy-mm-dd)"),
                                fieldWithPath("reserveSuccess").type(JsonFieldType.BOOLEAN).description("결제 성공 여부"),
                                fieldWithPath("errorMsg").type(JsonFieldType.STRING).description("에러메세지(결제성공일 경우 null)")
                        )
                )).andExpect(status().isCreated());
    }

    @Test
    @DisplayName(value = "한 숙소의 총 예약 목록 가져오기")
    public void getAllBookListTest() throws Exception{
        //given
        given(reservationService.getAllBookingList(any())).willReturn();

        //when
        ResultActions results = mvc.perform(RestDocumentationRequestBuilders.get("/room/book/{roomId}/list", 1L));

        //then
        results
                .andDo(print())
                .andDo(document("reserve-bookedList",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("roomId").description("숙소 아이디")
                        ),
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("총 예약 개수"),
                                fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("방 아이디"),
                                fieldWithPath("booked.[].startDate").type(JsonFieldType.STRING).description("예약 시작 날짜(yyyy-mm-dd)"),
                                fieldWithPath("booked.[].finalDate").type(JsonFieldType.STRING).description("예약 마지막 날짜(yyyy-mm-dd)")
                        )
                ))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.roomId").value(1L))
                .andExpect(jsonPath("$.booked[0].startDate").value("mockusername"))
                .andExpect(jsonPath("$.booked[0].finalDate").value("아메리카노"));
    }

    @Test
    @DisplayName(value = "내가 예약한 숙소 목록 가져오기")
    public void getMyBookListTest() throws Exception{
        //given
        given(reservationService.getMyAllBook(any())).willReturn();

        //when
        ResultActions results = mvc.perform(RestDocumentationRequestBuilders.get("/room/mybook")
                .principal(new UsernamePasswordAuthenticationToken(CustomUserDetails.create(user), null))
                .header("Authorization", "Bearer accessToken"));

        //then
        results
                .andDo(print())
                .andDo(document("reserve-myBook",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("카카오 사용자 Bearer Token")
                        ),
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("총 예약 개수"),
                                fieldWithPath("booked.[].roomId").type(JsonFieldType.NUMBER).description("방 아이디"),
                                fieldWithPath("booked.[].startDate").type(JsonFieldType.STRING).description("예약 시작 날짜(yyyy-mm-dd)"),
                                fieldWithPath("booked.[].finalDate").type(JsonFieldType.STRING).description("예약 마지막 날짜(yyyy-mm-dd)"),
                                fieldWithPath("booked.[].personnel").type(JsonFieldType.STRING).description("총 인원"),
                                fieldWithPath("booked.[].detailLocation").type(JsonFieldType.STRING).description("숙소 위치"),
                                fieldWithPath("booked.[].title").type(JsonFieldType.STRING).description("숙소 제목")
                        )
                ))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.roomId").value(1L))
                .andExpect(jsonPath("$.booked[0].startDate").value("mockusername"))
                .andExpect(jsonPath("$.booked[0].finalDate").value("아메리카노"))
                .andExpect(jsonPath("$.booked[0].personnel").value("아메리카노"))
                .andExpect(jsonPath("$.booked[0].detailLocation").value("아메리카노"))
                .andExpect(jsonPath("$.booked[0].title").value("아메리카노"));
    }
}
