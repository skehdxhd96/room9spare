package com.goomoong.room9backend.api;

import com.goomoong.room9backend.domain.Review;
import com.goomoong.room9backend.service.ReviewService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

//    @GetMapping("/api/v1/reviews")
//    public List<Review> ReviewsV1(@RequestBody @Validated ReviewRequest request){
//        return reviewService.findReviews();
//    }

    @PostMapping("/api/v1/reviews")
    public CreateReviewResponse saveReviewV1(@RequestBody @Validated CreateReviewRequest request) {
        Review review = new Review();

        review.setUser(review.getUser());
        review.setRoom(review.getRoom());
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

//    @Data
//    static class ReviewRequest{
//
//        private Long user_id;
//        private Long room_id;
//    }

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
