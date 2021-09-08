package com.goomoong.room9backend.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    private Long messageId;
    private Long userId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
