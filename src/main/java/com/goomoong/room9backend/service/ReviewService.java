package com.goomoong.room9backend.service;

import com.goomoong.room9backend.domain.review.Review;
import com.goomoong.room9backend.domain.review.dto.ReviewSearchDto;
import com.goomoong.room9backend.repository.reviewRepository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Review save(Review review){
        reviewRepository.save(review);
        return review;
    }

    @Transactional
    public void update(Long id,  String reviewContent, int reviewScore){
        Review review =  reviewRepository.findById(id).orElse(null);

        review.update(reviewContent, reviewScore);
    }

    @Transactional
    public void delete(Long id){
        reviewRepository.delete(findById(id));
    }

    public Review findById(Long id){
        return reviewRepository.findById(id).orElse(null);
    }

    public List<Review> findByUserAndRoom(ReviewSearchDto reviewSearchDto){
        return reviewRepository.findByUserAndRoom(reviewSearchDto);
    }

}
