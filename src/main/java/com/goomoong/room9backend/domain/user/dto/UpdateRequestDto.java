package com.goomoong.room9backend.domain.user.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateRequestDto {
    private String nickname;
    private MultipartFile file;
    private String email;
    private String birthday;
    private String gender;
    private String intro;
}
