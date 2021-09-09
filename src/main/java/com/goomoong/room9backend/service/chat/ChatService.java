package com.goomoong.room9backend.service.chat;

import com.goomoong.room9backend.domain.chat.ChatMessage;
import com.goomoong.room9backend.domain.chat.ChatRoom;
import com.goomoong.room9backend.domain.chat.dto.*;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.exception.NoSuchUserException;
import com.goomoong.room9backend.exception.chat.NoSuchChatMessageException;
import com.goomoong.room9backend.exception.chat.NoSuchChatRoomException;
import com.goomoong.room9backend.repository.chat.ChatMessageRepository;
import com.goomoong.room9backend.repository.chat.ChatRoomRepository;
import com.goomoong.room9backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    //TODO: 변경된 ui에 맞게 수정
    public List<ChatRoomIdResponseDto> getChatRooms(User user) {
        List<ChatRoom> chatRooms = user.getChatRooms();
        List<ChatRoomIdResponseDto> responseDtos = new ArrayList<>();
        chatRooms.stream().forEach(chatRoom -> {
            ChatRoomIdResponseDto responseDto = ChatRoomIdResponseDto.builder().chatRoomId(chatRoom.getId()).build();
            responseDtos.add(responseDto);
        });

        return responseDtos;
    }

    public ChatRoomIdResponseDto createChatRoom(CreateChatRoomRequestDto createChatRoomRequestDto, User user) {
        User foundUser = userRepository.findById(createChatRoomRequestDto.getUserId())
                .orElseThrow(() -> new NoSuchUserException("해당 id의 회원이 존재하지 않습니다."));
        ChatRoom chatRoom = ChatRoom.createChatRoom(foundUser, user);
        ChatRoom saved = chatRoomRepository.save(chatRoom);

        return ChatRoomIdResponseDto.builder().chatRoomId(saved.getId()).build();
    }

    public ChatRoomIdResponseDto deleteChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NoSuchChatRoomException("해당 id의 채팅방이 존재하지 않습니다."));
        chatRoomRepository.delete(chatRoom);

        return ChatRoomIdResponseDto.builder().chatRoomId(chatRoom.getId()).build();
    }

    public ChatMessageIdResponseDto createChatMessage(Long chatRoomId, User user, CreateChatMessageRequestDto createChatMessageRequestDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NoSuchChatRoomException("해당 id의 채팅방이 존재하지 않습니다."));
        ChatMessage chatMessage = ChatMessage.createChatMessage(chatRoom, user, createChatMessageRequestDto.getContent());
        chatRoom.addChatMessages(chatMessage);
        ChatMessage saved = chatMessageRepository.save(chatMessage);
        //채팅이 시작되면 사용자에게 채팅방 정보가 저장됨
        List<User> chatMembers = chatRoom.getChatMembers();
        chatMembers.stream().forEach(member -> member.addChatRoom(chatRoom));

        return ChatMessageIdResponseDto.builder().chatMessageId(saved.getId()).build();
    }

    public ChatMessagesDto getChatMessages(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new NoSuchChatRoomException("해당 id의 채팅방이 존재하지 않습니다."));
        List<ChatMessage> chatMessages = chatRoom.getChatMessages();
        List<ChatMessageDto> chatMessageDtos = new ArrayList<>();
        chatMessages.stream().forEach(chatMessage ->  {
            ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                    .messageId(chatMessage.getId())
                    .userId(chatMessage.getUser().getId())
                    .content(chatMessage.getContent())
                    .createdDate(chatMessage.getCreatedDate())
                    .updatedDate(chatMessage.getUpdatedDate())
                    .build();
            chatMessageDtos.add(chatMessageDto);
        });

        return ChatMessagesDto.builder().messages(chatMessageDtos).build();
    }

    public ChatMessageIdResponseDto editChatMessage(Long chatMessageId, EditChatMessageRequestDto editChatMessageRequestDto) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageId).orElseThrow(() -> new NoSuchChatMessageException("해당 id의 메시지가 존재하지 않습니다."));
        chatMessage.editChatMessage(editChatMessageRequestDto.getContent());

        return ChatMessageIdResponseDto.builder().chatMessageId(chatMessage.getId()).build();
    }

    public ChatMessageIdResponseDto deleteChatMessage(Long chatMessageId) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageId).orElseThrow(() -> new NoSuchChatMessageException("해당 id의 메시지가 존재하지 않습니다."));
        chatMessageRepository.delete(chatMessage);

        return ChatMessageIdResponseDto.builder().chatMessageId(chatMessage.getId()).build();
    }
}