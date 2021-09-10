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
public class ChatRoomsDto {

    private List<ChatRoomDto> rooms;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatRoomDto {

        private Long chatRoomId;
        private Boolean isHost;
        private String roomTitle;
        private Integer personnel;
        private LocalDateTime checkinDate;
        private LocalDateTime checkoutDate;
    }
}
