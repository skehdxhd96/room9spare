package com.goomoong.room9backend.repository.review;

import com.goomoong.room9backend.domain.review.Review;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    List<Review> findTop3ByOrderByIdDesc();
}