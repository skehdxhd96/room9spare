package com.goomoong.room9backend.service.chat;

import com.goomoong.room9backend.domain.chat.ChatMember;
import com.goomoong.room9backend.domain.chat.ChatMessage;
import com.goomoong.room9backend.domain.chat.ChatRoom;
import com.goomoong.room9backend.domain.chat.dto.CreateChatMessageRequestDto;
import com.goomoong.room9backend.domain.chat.dto.CreateChatRoomRequestDto;
import com.goomoong.room9backend.domain.chat.dto.EditChatMessageRequestDto;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.exception.NoSuchUserException;
import com.goomoong.room9backend.exception.chat.NoSuchChatMessageException;
import com.goomoong.room9backend.exception.chat.NoSuchChatRoomException;
import com.goomoong.room9backend.repository.chat.ChatMemberRepository;
import com.goomoong.room9backend.repository.chat.ChatMessageRepository;
import com.goomoong.room9backend.repository.chat.ChatRoomRepository;
import com.goomoong.room9backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    public List<ChatRoom> getChatRooms(User user) {
        List<ChatRoom> chatRooms = new ArrayList<>();
        List<ChatMember> chatMembers = chatMemberRepository.findAllByUser(user);
        chatMembers.stream().forEach(chatMember -> {
            Hibernate.initialize(chatMember.getChatRoom());
            chatRooms.add(chatMember.getChatRoom());
        });

        return chatRooms;
    }

    public ChatRoom createChatRoom(CreateChatRoomRequestDto createChatRoomRequestDto, User user) {
        User foundUser = userRepository.findById(createChatRoomRequestDto.getUserId())
                .orElseThrow(() -> new NoSuchUserException("해당 id의 회원이 존재하지 않습니다."));
        ChatRoom chatRoom = ChatRoom.createChatRoom();
        ChatMember other = ChatMember.createChatMember(chatRoom, foundUser);
        ChatMember me = ChatMember.createChatMember(chatRoom, user);
        chatMemberRepository.save(other);
        chatMemberRepository.save(me);

        return chatRoomRepository.save(chatRoom);
    }

    public Long deleteChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NoSuchChatRoomException("해당 id의 채팅방이 존재하지 않습니다."));
        chatRoomRepository.delete(chatRoom);

        return chatRoom.getId();
    }

    public ChatMessage createChatMessage(Long chatRoomId, User user, CreateChatMessageRequestDto createChatMessageRequestDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NoSuchChatRoomException("해당 id의 채팅방이 존재하지 않습니다."));
        ChatMessage chatMessage = ChatMessage.createChatMessage(chatRoom, user, createChatMessageRequestDto.getContent());
        chatRoom.addChatMessages(chatMessage);

        return chatMessageRepository.save(chatMessage);
    }

    public ChatMessage editChatMessage(Long chatMessageId, EditChatMessageRequestDto editChatMessageRequestDto) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageId).orElseThrow(() -> new NoSuchChatMessageException("해당 id의 메시지가 존재하지 않습니다."));
        chatMessage.editChatMessage(editChatMessageRequestDto.getContent());

        return chatMessage;
    }

    public Long deleteChatMessage(Long chatMessageId) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageId).orElseThrow(() -> new NoSuchChatMessageException("해당 id의 메시지가 존재하지 않습니다."));
        chatMessageRepository.delete(chatMessage);

        return chatMessage.getId();
    }
}