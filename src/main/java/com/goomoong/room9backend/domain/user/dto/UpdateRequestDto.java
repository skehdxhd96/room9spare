package com.goomoong.room9backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateRequestDto {
    private String nickname;
    private String thumbnailImgUrl;
    private String email;
    private String birthday;
    private String gender;
    private String intro;
}
