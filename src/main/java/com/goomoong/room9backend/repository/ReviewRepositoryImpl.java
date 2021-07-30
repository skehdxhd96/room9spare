package com.goomoong.room9backend.repository;

import com.goomoong.room9backend.domain.*;
import com.goomoong.room9backend.domain.review.Review;
import com.goomoong.room9backend.domain.review.dto.ReviewSearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.goomoong.room9backend.domain.review.QReview.review;


public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Review> findByUserAndRoom(ReviewSearchDto reviewSearchDto){

        return queryFactory
                .select(review)
                .from(review)
                .where(userEq(reviewSearchDto.getUser()), roomEq(reviewSearchDto.getRoom()))
                .fetch();
    }

    private BooleanExpression userEq(User user) {
        if(user == null){
            return null;
        }
        return review.user.eq(user);
    }

    private BooleanExpression roomEq(Room room) {
        if(room == null){
            return null;
        }
        return review.room.eq(room);
    }
}
