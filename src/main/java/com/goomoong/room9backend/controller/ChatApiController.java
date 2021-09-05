package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.domain.chat.ChatMessage;
import com.goomoong.room9backend.domain.chat.ChatRoom;
import com.goomoong.room9backend.domain.chat.dto.*;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.security.userdetails.CustomUserDetails;
import com.goomoong.room9backend.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class ChatApiController {

    private final ChatService chatService;

    @PostMapping("/chatroom")
    public ResponseEntity<ChatRoomIdResponseDto> createChatRoomApi(
            @RequestBody CreateChatRoomRequestDto chatRoomRequestDto,
            @AuthenticationPrincipal CustomUserDetails currentUser
            ) {
        ChatRoom chatRoom = chatService.createChatRoom(chatRoomRequestDto, currentUser.getUser());
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
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @RequestBody @Valid CreateChatMessageRequestDto chatMessageRequestDto
            ) {
        ChatMessage  chatMessage = chatService.createChatMessage(chatRoomId, currentUser.getUser(), chatMessageRequestDto);
        ChatMessageIdResponseDto idResponseDto = ChatMessageIdResponseDto.builder().chatMessageId(chatMessage.getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(idResponseDto);
    }

    @PutMapping("/chatroom/{chatroomid}/message/{chatmessageid}")
    public ResponseEntity<ChatMessageIdResponseDto> editChatMessageApi(
            @PathVariable(name = "chatroomid") Long chatRoomId,
            @PathVariable(name = "chatmessageid") Long chatMessageId,
            @RequestBody @Valid EditChatMessageRequestDto chatMessageRequestDto
            ) {
        ChatMessage chatMessage = chatService.editChatMessage(chatMessageId, chatMessageRequestDto);
        ChatMessageIdResponseDto idResponseDto = ChatMessageIdResponseDto.builder().chatMessageId(chatMessage.getId()).build();

        return ResponseEntity.ok(idResponseDto);
    }

    @DeleteMapping("/chatroom/{chatroomid}/message/{chatmessageid}")
    public ResponseEntity<ChatMessageIdResponseDto> deleteChatMessageApi(
            @PathVariable(name = "chatroomid") Long chatRoomId,
            @PathVariable(name = "chatmessageid") Long chatMessageId
    ) {
        Long deletedChatMessageId = chatService.deleteChatMessage(chatMessageId);
        ChatMessageIdResponseDto idResponseDto = ChatMessageIdResponseDto.builder().chatMessageId(deletedChatMessageId).build();

        return ResponseEntity.ok(idResponseDto);
    }
}
