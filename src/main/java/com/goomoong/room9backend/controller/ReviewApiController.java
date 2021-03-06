package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.domain.review.Review;
import com.goomoong.room9backend.domain.review.dto.ReviewSearchDto;
import com.goomoong.room9backend.domain.review.dto.*;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.repository.room.RoomRepository;
import com.goomoong.room9backend.service.ReviewService;
import com.goomoong.room9backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final RoomRepository roomRepository;

    @GetMapping("/api/v1/reviews")
    public SelectReviewResultDto selectReviewsV1(@RequestParam @Nullable Long user, @RequestParam @Nullable Long room){
        ReviewSearchDto reviewSearchDto = new ReviewSearchDto();

        if(user != null)
            reviewSearchDto.setUser(userService.findById(user));
        else
            reviewSearchDto.setUser(null);

        if(room != null)
            reviewSearchDto.setRoom(roomRepository.findById(room).orElse(null));
        else
            reviewSearchDto.setRoom(null);

        List<Review> findReviews = reviewService.findByUserAndRoom(reviewSearchDto);

        List<ReviewDto> collect = findReviews.stream()
                .map(r -> ReviewDto.builder()
                        .id(r.getId())
                        .reviewContent(r.getReviewContent())
                        .reviewScore(r.getReviewScore())
                        .reviewCreated(r.getCreatedDate())
                        .reviewUpdated(r.getUpdatedDate())
                        .build()
                )
                .collect(Collectors.toList());

        return new SelectReviewResultDto(collect);
    }

    @GetMapping("/api/v1/reviews/latest")
    public SelectReviewResultDto selectLatestReviewV1(){
        List<Review> findReviews = reviewService.findLatestReview();

        List<ReviewDto> collect = findReviews.stream()
                .map(r -> ReviewDto.builder()
                        .id(r.getId())
                        .reviewContent(r.getReviewContent())
                        .reviewScore(r.getReviewScore())
                        .reviewCreated(r.getCreatedDate())
                        .reviewUpdated(r.getUpdatedDate())
                        .build()
                )
                .collect(Collectors.toList());

        return new SelectReviewResultDto(collect);
    }

    @PostMapping("/api/v1/reviews")
    public CreateReviewResponseDto saveReviewV1(@RequestBody @Validated CreateReviewRequestDto request) {
        User user = userService.findById(request.getUserId());
        Room room = roomRepository.findById(request.getUserId()).orElse(null);

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
