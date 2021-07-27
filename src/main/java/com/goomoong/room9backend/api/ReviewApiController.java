package com.goomoong.room9backend.api;

import com.goomoong.room9backend.domain.Review;
import com.goomoong.room9backend.domain.ReviewSearch;
import com.goomoong.room9backend.domain.Room;
import com.goomoong.room9backend.domain.User;
import com.goomoong.room9backend.service.ReviewService;
import com.goomoong.room9backend.service.RoomService;
import com.goomoong.room9backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final RoomService roomService;

    @GetMapping("/api/v1/reviews")
    public Result selectReviewsV1(@RequestBody @Validated ReviewRequest request){
        ReviewSearch reviewSearch = new ReviewSearch();

        if(request.getUser_id() != null)
            reviewSearch.setUser(userService.findById(request.getUser_id()));
        else
            reviewSearch.setUser(null);

        if(request.getRoom_id() != null)
            reviewSearch.setRoom(roomService.findById(request.getRoom_id()));
        else
            reviewSearch.setRoom(null);

        List<Review> findReviews = reviewService.findByUserAndRoom(reviewSearch);

        List<ReviewDto> collect = findReviews.stream()
                .map(r -> new ReviewDto(r.getId(), r.getReviewContent(), r.getReviewCreated(), r.getReviewScore()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping("/api/v1/reviews")
    public CreateReviewResponse saveReviewV1(@RequestBody @Validated CreateReviewRequest request) {
        Review review = new Review();
        User user = userService.findById(request.getUser_id());
        Room room = roomService.findById(request.getRoom_id());

        review.setUser(user);
        review.setRoom(room);
        review.setReviewContent(request.getReviewContent());
        review.setReviewCreated(LocalDateTime.now());
        review.setReviewScore(request.getReviewScore());

        Long id = reviewService.save(review);

        return new CreateReviewResponse(id);
    }

    @PutMapping("/api/v1/reviews/{id}")
    public UpdateReviewResponse updateReviewV1(@PathVariable Long id, @RequestBody @Validated UpdateReviewRequest request){

        reviewService.update(id, request.getReviewContent(), request.getReviewScore());

        Review review = reviewService.findById(id);

        return new UpdateReviewResponse(review.getId());
    }

    @DeleteMapping("/api/v1/reviews/{id}")
    public void deleteReviewV1(@PathVariable Long id){
        reviewService.delete(id);
    }

    @Data
    static class ReviewRequest{
        private Long user_id;
        private Long room_id;
    }

    @Data
    @AllArgsConstructor
    static class ReviewDto{
        private Long id;
        private String reviewContent;
        private LocalDateTime reviewCreated;
        private int reviewScore;
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }


    @Data
    static class CreateReviewRequest{

        private Long user_id;
        private Long room_id;
        private String reviewContent;
        private int reviewScore;
    }

    @Data
    static class CreateReviewResponse{

        private Long id;

        public CreateReviewResponse(Long id){
            this.id = id;
        }
    }

    @Data
    static class UpdateReviewRequest{

        private String reviewContent;
        private int reviewScore;
    }

    @Data
    static class UpdateReviewResponse{

        private Long id;

        public UpdateReviewResponse(Long id){
            this.id = id;
        }

    }
}
