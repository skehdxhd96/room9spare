package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.domain.review.Review;
import com.goomoong.room9backend.domain.review.dto.ReviewSearchDto;
import com.goomoong.room9backend.domain.Room;
import com.goomoong.room9backend.domain.User;
import com.goomoong.room9backend.domain.review.dto.*;
import com.goomoong.room9backend.service.ReviewService;
import com.goomoong.room9backend.service.RoomService;
import com.goomoong.room9backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final RoomService roomService;

    @GetMapping("/api/v1/reviews")
    public SelectResultDto selectReviewsV1(@RequestBody @Validated SelectReviewRequestDto request){
        ReviewSearchDto reviewSearchDto = new ReviewSearchDto();

        if(request.getUser_id() != null)
            reviewSearchDto.setUser(userService.findById(request.getUser_id()));
        else
            reviewSearchDto.setUser(null);

        if(request.getRoom_id() != null)
            reviewSearchDto.setRoom(roomService.findById(request.getRoom_id()));
        else
            reviewSearchDto.setRoom(null);

        List<Review> findReviews = reviewService.findByUserAndRoom(reviewSearchDto);

        List<ReviewDto> collect = findReviews.stream()
                .map(r -> new ReviewDto(r.getId(), r.getReviewContent(), r.getReviewCreated(), r.getReviewScore()))
                .collect(Collectors.toList());

        return new SelectResultDto(collect);
    }

    @PostMapping("/api/v1/reviews")
    public CreateReviewResponseDto saveReviewV1(@RequestBody @Validated CreateReviewRequestDto request) {
        User user = userService.findById(request.getUser_id());
        Room room = roomService.findById(request.getRoom_id());

        Review review = Review.builder()
                                .user(user)
                                .room(room)
                                .reviewContent(request.getReviewContent())
                                .reviewScore(request.getReviewScore())
                                .build();

        Review savedReview = reviewService.save(review);

        return new CreateReviewResponseDto(savedReview.getId());
    }

    @PatchMapping("/api/v1/reviews/{id}")
    public UpdateReviewResponseDto updateReviewV1(@PathVariable Long id, @RequestBody @Validated UpdateReviewRequestDto request){

        reviewService.update(id, request.getReviewContent(), request.getReviewScore());

        Review review = reviewService.findById(id);

        return new UpdateReviewResponseDto(review.getId());
    }

    @DeleteMapping("/api/v1/reviews/{id}")
    public void deleteReviewV1(@PathVariable Long id){
        reviewService.delete(id);
    }
}
