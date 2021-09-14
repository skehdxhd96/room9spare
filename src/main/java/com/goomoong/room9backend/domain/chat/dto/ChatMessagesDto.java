package com.goomoong.room9backend.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessagesDto {

    private List<ChatMessageDto> messages;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatMessageDto {

        private Long messageId;
        private Long userId;
        private String content;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
    }
}
