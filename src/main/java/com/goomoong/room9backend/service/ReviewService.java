package com.goomoong.room9backend.service;

import com.goomoong.room9backend.domain.Review;
import com.goomoong.room9backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Long save(Review review){
        reviewRepository.save(review);
        return review.getId();
    }

    @Transactional
    public void update(Long id,  String reviewContent, int reviewScore){
        Review review =  reviewRepository.findById(id).orElse(null);

        review.setReviewContent(reviewContent);
        review.setReviewScore(reviewScore);
        review.setReviewCreated(LocalDateTime.now());
    }

    @Transactional
    public void delete(Long id){
        reviewRepository.delete(findById(id));
    }

    public Review findById(Long id){
        return reviewRepository.findById(id).orElse(null);
    }

    public List<Review> findReviews(){ //queryDSL room_id, user_id 동적쿼리 생성 해야됨
        return reviewRepository.findAll();
    }
}
