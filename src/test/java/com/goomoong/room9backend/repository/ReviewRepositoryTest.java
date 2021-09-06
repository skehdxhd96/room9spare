package com.goomoong.room9backend.repository;


import com.goomoong.room9backend.config.QuerydslConfig;
import com.goomoong.room9backend.domain.review.Review;
import com.goomoong.room9backend.domain.review.dto.ReviewSearchDto;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.repository.review.ReviewRepository;
import com.goomoong.room9backend.repository.room.RoomRepository;
import com.goomoong.room9backend.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void 리뷰_저장(){
        //given
        Review review = new Review();
        review.setReviewContent("test");

        //when
        Review savedReview = reviewRepository.save(review);

        //then
        Assertions.assertEquals(review.getReviewContent(), savedReview.getReviewContent(), "리뷰 저장");
    }

    @Test
    public void 리뷰_조회_by유저_by방(){
        //given
        User user = User.builder().id(1L).accountId("1").name("mock").nickname("mock").role(Role.GUEST).thumbnailImgUrl("mock.jpg").email("mock@abc").birthday("0101").gender("male").intro("test").build();
        Room room = new Room();

        User savedUser = userRepository.save(user);
        Room savedRoom = roomRepository.save(room);

        Review review = new Review();
        review.setUser(savedUser);
        review.setRoom(savedRoom);
        review.setReviewContent("test");

        Review savedReview= reviewRepository.save(review);

        //when
        List<Review> findReviews = reviewRepository.findByUserAndRoom(new ReviewSearchDto(savedUser, savedRoom));

        //then
        Assertions.assertEquals(savedReview.getReviewContent(), findReviews.get(0).getReviewContent());
    }

    @Test
    public void 리뷰_id전략_테스트(){
        //given
        Review review1 = new Review();
        Review review2 = new Review();

        //when
        Review savedReview1 = reviewRepository.save(review1);
        Review savedReview2 = reviewRepository.save(review2);

        //then
        Assertions.assertEquals(1, Math.abs(savedReview1.getId() - savedReview2.getId()));
    }

    @Test
    public void 최근_리뷰_조회(){
        //given
        Review review1 = new Review();
        Review review2 = new Review();
        Review review3 = new Review();
        Review review4 = new Review();
        Review review5 = new Review();

        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);
        reviewRepository.save(review4);
        reviewRepository.save(review5);

        //when
        List<Review> findReviews = reviewRepository.findTop3ByOrderByIdDesc();
        
        //then
        Assertions.assertEquals(3, findReviews.size());
        Assertions.assertEquals(review5.getId(), findReviews.get(0).getId());
        
    }
}