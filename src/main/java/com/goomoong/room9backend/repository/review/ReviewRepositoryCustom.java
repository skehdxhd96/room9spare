package com.goomoong.room9backend.repository.review;

import com.goomoong.room9backend.domain.review.Review;
import com.goomoong.room9backend.domain.review.dto.ReviewSearchDto;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryCustom {
    List<Review> findByUserAndRoom(ReviewSearchDto reviewSearchDto);
    Optional<List<Review>> findAvgScoreAndCountByRoom(Long roomId);
}
