package com.goomoong.room9backend.domain.user.dto;

import com.goomoong.room9backend.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String nickname;
    private Role role;
    private String thumbnailImgUrl;
    private String email;
    private String birthday;
    private String gender;
    private String intro;
}
