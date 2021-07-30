package com.goomoong.room9backend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goomoong.room9backend.domain.Room;
import com.goomoong.room9backend.domain.User;
import com.goomoong.room9backend.domain.review.Review;
import com.goomoong.room9backend.domain.review.dto.CreateReviewRequestDto;
import com.goomoong.room9backend.domain.review.dto.ReviewSearchDto;
import com.goomoong.room9backend.domain.review.dto.SelectReviewRequestDto;
import com.goomoong.room9backend.domain.review.dto.UpdateReviewRequestDto;
import com.goomoong.room9backend.service.ReviewService;
import com.goomoong.room9backend.service.RoomService;
import com.goomoong.room9backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ReviewApiController.class)
@TestPropertySource(locations = "classpath:/application.properties")
class ReviewApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private UserService userService;

    @MockBean
    private RoomService roomService;

    private Review review;
    private User user;
    private Room room;

    @BeforeEach
    public void init(){
        user = new User();
        room = new Room();

        user.setId(1L);
        room.setId(1L);

        review = Review.builder()
                .id(1L)
                .user(user)
                .room(room)
                .reviewContent("test")
                .reviewScore(1)
                .build();
    }

    @Test
    public void 리뷰_조회_by유저_by방() throws Exception{
        //given
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        given(userService.findById(1L)).willReturn(user);
        given(roomService.findById(1L)).willReturn(room);
        given(reviewService.findByUserAndRoom(any(ReviewSearchDto.class))).willReturn(reviews);

        //when
        //then
        mvc.perform(get("/api/v1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(SelectReviewRequestDto.builder().user_id(1L).room_id(1L).build())))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test")))
                .andDo(print());

    }

    @Test
    public void 리뷰_생성() throws Exception{
        //given
        given(reviewService.save(any(Review.class))).willReturn(review);

        //when
        //then
        mvc.perform(post("/api/v1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CreateReviewRequestDto.builder().user_id(1L).room_id(1L).reviewContent("test").reviewScore(1).build())))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1}"))
                .andDo(print());
    }

    @Test
    public void 리뷰_갱신() throws Exception{
        //given
        given(reviewService.findById(1L)).willReturn(review);

        //when
        //then
        mvc.perform(patch("/api/v1/reviews/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UpdateReviewRequestDto.builder().reviewContent("test2").reviewScore(2).build())))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1}"))
                .andDo(print());

    }

    @Test
    public void 리뷰_삭제() throws Exception{
        //given
        //when
        //then
        mvc.perform(delete("/api/v1/reviews/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(print());

    }

}