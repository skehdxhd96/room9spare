package com.goomoong.room9backend.callback.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoOAuth2ResponseDto {

    private String id;
    private KakaoAccount kakaoAccount;

    @ToString
    @Getter
    @NoArgsConstructor
    public static class KakaoAccount {

        private Profile profile;
        private String email;
        private String birthday;
        private String gender;

        @ToString
        @Getter
        @NoArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class Profile {

            private String nickname;
            private String thumbnailImageUrl;
        }
    }
}