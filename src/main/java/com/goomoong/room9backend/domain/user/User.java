package com.goomoong.room9backend.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String token;

    //TODO: 이미지 테이블 조인
    //private Long imageId;

    @Column(nullable = false)
    private String name;

    @Column
    private String birthday;

    @Column
    private Gender gender;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column
    private String intro;
}
