package com.goomoong.room9backend.service.chat;

import com.goomoong.room9backend.domain.chat.ChatMessage;
import com.goomoong.room9backend.domain.chat.ChatRoom;
import com.goomoong.room9backend.domain.chat.dto.CreateChatMessageRequestDto;
import com.goomoong.room9backend.domain.chat.dto.CreateChatRoomRequestDto;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.exception.NoSuchUserException;
import com.goomoong.room9backend.exception.chat.NoSuchChatRoomException;
import com.goomoong.room9backend.repository.chat.ChatMessageRepository;
import com.goomoong.room9backend.repository.chat.ChatRoomRepository;
import com.goomoong.room9backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatRoom createChatRoom(CreateChatRoomRequestDto createChatRoomRequestDto, User user) {
        User foundUser = userRepository.findById(createChatRoomRequestDto.getUserId())
                .orElseThrow(() -> new NoSuchUserException("해당 id의 회원이 존재하지 않습니다."));
        ChatRoom chatRoom = ChatRoom.createChatRoom(foundUser, user);

        return chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public Long deleteChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NoSuchChatRoomException("해당 id의 채팅방이 존재하지 않습니다."));
        chatRoomRepository.delete(chatRoom);

        return chatRoom.getId();
    }

    @Transactional
    public ChatMessage createChatMessage(Long chatRoomId, User user, CreateChatMessageRequestDto createChatMessageRequestDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NoSuchChatRoomException("해당 id의 채팅방이 존재하지 않습니다."));
        ChatMessage chatMessage = ChatMessage.createChatMessage(chatRoom, user, createChatMessageRequestDto.getContent());

        return chatMessageRepository.save(chatMessage);
    }
}
