package com.goomoong.room9backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Review {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String reviewContent;

    private LocalDateTime reviewCreated;

    private int reviewScore;

    public void update(String reviewContent, int reviewScore) {
        this.reviewContent = reviewContent;
        this.reviewScore = reviewScore;
        this.reviewCreated = LocalDateTime.now();
    }

}
