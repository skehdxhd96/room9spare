package com.goomoong.room9backend.service;

import com.goomoong.room9backend.domain.review.Review;
import com.goomoong.room9backend.domain.review.dto.ReviewSearchDto;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.repository.review.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void 리뷰_저장(){
        //given
        Review review = new Review();

        given(reviewRepository.save(review)).willReturn(review);

        //when
        Review savedReview = reviewService.save(review);

        //then
        Assertions.assertNotNull(savedReview);
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    public void 리뷰_갱신(){
        //given
        Review review = new Review();
        review.setId(1L);
        review.setReviewScore(1);
        review.setReviewContent("test1");

        given(reviewRepository.findById(review.getId())).willReturn(Optional.of(review));

        //when
        reviewService.update(1L,"test2", 2);

        //then
        Assertions.assertNotEquals("test1",review.getReviewContent());
        Assertions.assertNotEquals(1,review.getReviewScore());
        verify(reviewRepository).findById(any(Long.class));
    }

    @Test
    public void 리뷰_삭제(){
        //given
        Review review = new Review();
        review.setId(1L);
        reviewService.save(review);

        given(reviewRepository.findById(review.getId())).willReturn(Optional.of(review));

        //when
        reviewService.delete(review.getId());

        //then
        verify(reviewRepository).findById(any(Long.class));
        verify(reviewRepository).delete(any(Review.class));
    }

    @Test
    public void 리뷰_조회_by유저_by방(){
        //given
        Review review = new Review();
        User user = User.builder().id(1L).accountId("1").name("mock").nickname("mock").role(Role.GUEST).thumbnailImgUrl("mock.jpg").email("mock@abc").birthday("0101").gender("male").intro("test").build();
        Room room = new Room();

//        room.setId(1L);

        review.setUser(user);
        review.setRoom(room);

        List<Review> reviews1 = new ArrayList<>();
        reviews1.add(review);

        ReviewSearchDto reviewSearchDto = new ReviewSearchDto(user, room);

        given(reviewRepository.findByUserAndRoom(reviewSearchDto)).willReturn(reviews1);

        //when
        List<Review> reviews2 = reviewService.findByUserAndRoom(reviewSearchDto);

        //then
        Assertions.assertEquals(reviews1.get(0).getUser(), reviews2.get(0).getUser());
        Assertions.assertEquals(reviews1.get(0).getRoom(), reviews2.get(0).getRoom());
        verify(reviewRepository).findByUserAndRoom(any(ReviewSearchDto.class));
    }
}