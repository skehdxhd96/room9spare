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
    @Column(name = "user_id")
    private Long id;

    @Column
    private String accountId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    //TODO: 이미지 테이블 조인
    //Image: 110 * 110
    @Column(nullable = false)
    private String thumbnailImgUrl;

    @Column
    private String email;

    @Column
    private String birthday;

    @Column
    private String gender;

    @Column
    private String phoneNumber;

    @Column
    private String intro;

    @Builder
    public User(Long id, String accountId, String name, String nickname, Role role, String thumbnailImgUrl, String email, String birthday, String gender) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
    }

    public void update(String nickname, String thumbnailImgUrl, String email, String birthday, String gender, String phoneNumber, String intro) {
        this.nickname = nickname;
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.intro = intro;
    }

    public void changeRole() {
        this.role = (this.role == Role.GUEST) ? Role.HOST : Role.GUEST;
    }

    public static User toEntity(String accountId, String name, String nickname, Role role, String thumbnailImgUrl, String email, String birthday, String gender) {
        return User.builder()
                .accountId(accountId)
                .name(name)
                .nickname(nickname)
                .role(role)
                .thumbnailImgUrl(thumbnailImgUrl)
                .email(email)
                .birthday(birthday)
                .gender(gender)
                .build();
    }
}
