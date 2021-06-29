package com.goomoong.room9backend.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String accountId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String name;

    //TODO: 이미지 테이블 조인
    @Column(nullable = false)
    private String thumbnail_url;

    @Column
    private String jwtToken;

    @Column
    private String birthday;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Column
    private String intro;

    @Builder
    public User(String accountId, Role role, String name, String thumbnail_url) {
        this.accountId = accountId;
        this.role = role;
        this.name = name;
        this.thumbnail_url = thumbnail_url;
    }

    public User saveJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;

        return this;
    }

    public User updateThumbnail(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;

        return this;
    }

    public User updateIntro(String intro) {
        this.intro = intro;

        return this;
    }
}
