package com.goomoong.room9backend.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequestDto {

    @NotBlank(message = "메세지 내용은 빈칸일 수 없습니다.")
    private String content;
}
