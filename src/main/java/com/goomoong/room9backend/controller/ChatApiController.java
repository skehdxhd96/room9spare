package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.domain.chat.ChatRoom;
import com.goomoong.room9backend.domain.chat.dto.CreateChatRoomRequestDto;
import com.goomoong.room9backend.domain.chat.dto.CreateChatRoomResponseDto;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class ChatApiController {

    private final ChatService chatService;

    @PostMapping("/chatroom")
    public ResponseEntity<CreateChatRoomResponseDto> createChatRoomApi(
            @RequestBody CreateChatRoomRequestDto chatRoomRequestDto,
            @AuthenticationPrincipal User user
            ) {
        ChatRoom chatRoom = chatService.createChatRoom(chatRoomRequestDto, user);
        CreateChatRoomResponseDto chatRoomResponseDto = CreateChatRoomResponseDto.builder().chatRoomId(chatRoom.getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(chatRoomResponseDto);
    }
}
