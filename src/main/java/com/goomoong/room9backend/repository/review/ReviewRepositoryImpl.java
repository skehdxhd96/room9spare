package com.goomoong.room9backend.repository.review;

import com.goomoong.room9backend.domain.review.Review;
import com.goomoong.room9backend.domain.review.dto.ReviewSearchDto;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.user.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.goomoong.room9backend.domain.review.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory queryFactory;

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
