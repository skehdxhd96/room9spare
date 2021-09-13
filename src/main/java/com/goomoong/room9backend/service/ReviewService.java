package com.goomoong.room9backend.service;

import com.goomoong.room9backend.domain.review.Review;
import com.goomoong.room9backend.domain.review.dto.ReviewSearchDto;
import com.goomoong.room9backend.domain.review.dto.scoreDto;
import com.goomoong.room9backend.exception.NoSuchReviewException;
import com.goomoong.room9backend.repository.review.ReviewRepository;
import com.goomoong.room9backend.util.AboutScore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Review review =  reviewRepository.findById(id).orElseThrow(() -> new NoSuchReviewException("존재하지 않는 리뷰입니다."));

        review.update(reviewContent, reviewScore);
    }

    @Transactional
    public void delete(Long id){
        reviewRepository.delete(findById(id));
    }

    public Review findById(Long id){
        return reviewRepository.findById(id).orElseThrow(() -> new NoSuchReviewException("존재하지 않는 리뷰입니다."));
    }

    public List<Review> findByUserAndRoom(ReviewSearchDto reviewSearchDto){
        return reviewRepository.findByUserAndRoom(reviewSearchDto);
    }

    public scoreDto getAvgScoreAndCount(Long roomId) {
        List<Review> reviewDatas = reviewRepository.findAvgScoreAndCountByRoom(roomId).orElse(null);
        if(reviewDatas.equals(null)) {
            return new scoreDto();
        }

        return scoreDto.builder()
                .avgScore(AboutScore.getAvgScore(reviewDatas))
                .reviewCount(reviewDatas.size())
                .build();
    }

}
