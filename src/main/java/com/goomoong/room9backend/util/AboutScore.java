package com.goomoong.room9backend.util;

import com.goomoong.room9backend.domain.review.Review;

import java.util.List;

public class AboutScore {

    public static Double getAvgScore(List<Review> reviewDatas) {

        Double score = 0.0;

        if(reviewDatas.size() == 0) {
            return score;
        }

        for (Review reviewData : reviewDatas) {
            score += (double) reviewData.getReviewScore();
        }

        return score / (double) reviewDatas.size();
    }
}
