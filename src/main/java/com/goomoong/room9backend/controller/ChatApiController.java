package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.domain.chat.ChatMessage;
import com.goomoong.room9backend.domain.chat.ChatRoom;
import com.goomoong.room9backend.domain.chat.dto.ChatMessageIdResponseDto;
import com.goomoong.room9backend.domain.chat.dto.CreateChatMessageRequestDto;
import com.goomoong.room9backend.domain.chat.dto.CreateChatRoomRequestDto;
import com.goomoong.room9backend.domain.chat.dto.ChatRoomIdResponseDto;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class ChatApiController {

    private final ChatService chatService;

    @PostMapping("/chatroom")
    public ResponseEntity<ChatRoomIdResponseDto> createChatRoomApi(
            @RequestBody CreateChatRoomRequestDto chatRoomRequestDto,
            @AuthenticationPrincipal User user
            ) {
        ChatRoom chatRoom = chatService.createChatRoom(chatRoomRequestDto, user);
        ChatRoomIdResponseDto idResponseDto = ChatRoomIdResponseDto.builder().chatRoomId(chatRoom.getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(idResponseDto);
    }

    @DeleteMapping("/chatroom/{id}")
    public ResponseEntity<ChatRoomIdResponseDto> deleteChatRoomApi(@PathVariable(name = "id") Long chatRoomId) {
        Long deletedChatRoomId = chatService.deleteChatRoom(chatRoomId);
        ChatRoomIdResponseDto idResponseDto = ChatRoomIdResponseDto.builder().chatRoomId(deletedChatRoomId).build();

        return ResponseEntity.ok(idResponseDto);
    }

    @PostMapping("/chatroom/{id}/message")
    public ResponseEntity<ChatMessageIdResponseDto> createChatMessageApi(
            @PathVariable(name = "id") Long chatRoomId,
            @AuthenticationPrincipal User user,
            @RequestBody CreateChatMessageRequestDto chatMessageRequestDto
            ) {
        ChatMessage  chatMessage = chatService.createChatMessage(chatRoomId, user, chatMessageRequestDto);
        ChatMessageIdResponseDto idResponseDto = ChatMessageIdResponseDto.builder().chatMessageId(chatMessage.getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(idResponseDto);
    }
}
