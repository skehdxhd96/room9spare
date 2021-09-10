package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.domain.chat.dto.*;
import com.goomoong.room9backend.security.userdetails.CustomUserDetails;
import com.goomoong.room9backend.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class ChatApiController {

    private final ChatService chatService;

    //TODO: 변경된 ui에 맞게 수정
    @GetMapping("/chatroom")
    public ResponseEntity<List<IdResponseDto>> getChatRoomsApi(@AuthenticationPrincipal CustomUserDetails currentUser) {
        List<IdResponseDto> responseDtos = chatService.getChatRooms(currentUser.getUser());

        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping("/chatroom")
    public ResponseEntity<IdResponseDto> createChatRoomApi(
            @RequestBody ChatRoomRequestDto chatRoomRequestDto,
            @AuthenticationPrincipal CustomUserDetails currentUser
            ) {
        IdResponseDto idResponseDto = chatService.createChatRoom(chatRoomRequestDto, currentUser.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(idResponseDto);
    }

    @DeleteMapping("/chatroom/{id}")
    public ResponseEntity<IdResponseDto> deleteChatRoomApi(@PathVariable(name = "id") Long chatRoomId) {
        IdResponseDto idResponseDto = chatService.deleteChatRoom(chatRoomId);

        return ResponseEntity.ok(idResponseDto);
    }

    @PostMapping("/chatroom/{id}/message")
    public ResponseEntity<IdResponseDto> createChatMessageApi(
            @PathVariable(name = "id") Long chatRoomId,
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @RequestBody @Valid ChatMessageRequestDto chatMessageRequestDto
            ) {
        IdResponseDto idResponseDto = chatService.createChatMessage(chatRoomId, currentUser.getUser(), chatMessageRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(idResponseDto);
    }

    @GetMapping("/chatroom/{id}/message")
    public ResponseEntity<ChatMessagesDto> getChatMessagesApi(@PathVariable(name = "id") Long chatRoomId) {
        ChatMessagesDto chatMessagesDto = chatService.getChatMessages(chatRoomId);

        return ResponseEntity.ok(chatMessagesDto);
    }

    @PutMapping("/chatroom/{roomid}/message/{messageid}")
    public ResponseEntity<IdResponseDto> editChatMessageApi(
            @PathVariable(name = "roomid") Long chatRoomId,
            @PathVariable(name = "messageid") Long chatMessageId,
            @RequestBody @Valid ChatMessageRequestDto chatMessageRequestDto
            ) {
        IdResponseDto idResponseDto = chatService.editChatMessage(chatMessageId, chatMessageRequestDto);

        return ResponseEntity.ok(idResponseDto);
    }

    @DeleteMapping("/chatroom/{roomid}/message/{messageid}")
    public ResponseEntity<IdResponseDto> deleteChatMessageApi(
            @PathVariable(name = "roomid") Long chatRoomId,
            @PathVariable(name = "messageid") Long chatMessageId
    ) {
        IdResponseDto idResponseDto = chatService.deleteChatMessage(chatMessageId);

        return ResponseEntity.ok(idResponseDto);
    }
}
