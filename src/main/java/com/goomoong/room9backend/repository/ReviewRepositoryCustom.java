package com.goomoong.room9backend.repository;

import com.goomoong.room9backend.domain.Review;
import com.goomoong.room9backend.domain.ReviewSearch;

import java.util.List;

public interface ReviewRepositoryCustom {
    List<Review> findByUserAndRoom(ReviewSearch reviewSearch);
}
