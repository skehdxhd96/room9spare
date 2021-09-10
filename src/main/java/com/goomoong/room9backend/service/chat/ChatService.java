package com.goomoong.room9backend.service.chat;

import com.goomoong.room9backend.domain.chat.ChatMessage;
import com.goomoong.room9backend.domain.chat.ChatRoom;
import com.goomoong.room9backend.domain.chat.dto.*;
import com.goomoong.room9backend.domain.reservation.roomReservation;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.exception.NoSuchRoomException;
import com.goomoong.room9backend.exception.NoSuchUserException;
import com.goomoong.room9backend.exception.chat.NoSuchChatMessageException;
import com.goomoong.room9backend.exception.chat.NoSuchChatRoomException;
import com.goomoong.room9backend.repository.chat.chatmessage.ChatMessageRepository;
import com.goomoong.room9backend.repository.chat.chatroom.ChatRoomRepository;
import com.goomoong.room9backend.repository.reservation.roomReservationRepository;
import com.goomoong.room9backend.repository.room.RoomRepository;
import com.goomoong.room9backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final roomReservationRepository roomReservationRepository;

    public ChatRoomsDto getChatRooms(User user) {
        User foundUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new NoSuchUserException("해당 id의 회원이 존재하지 않습니다."));
        List<ChatRoom> chatRooms = foundUser.getChatRooms();
        List<ChatRoomsDto.ChatRoomDto> chatRoomDtos = new ArrayList<>();
        chatRooms.stream().forEach(chatRoom -> {
            //메시지가 있는 채팅방만 보여줌
            if (!chatRoom.getChatMessages().isEmpty()) {
                roomReservation reservation;
                //게스트 정보와 방 정보로 예약 정보 찾기
                if (foundUser.isHost()) {
                    User guest = chatRoom.getGuestFromChatMembers();
                    reservation = roomReservationRepository.findByRoomAndUsers(chatRoom.getRoom(), guest);
                } else {
                    reservation = roomReservationRepository.findByRoomAndUsers(chatRoom.getRoom(), foundUser);
                }
                ChatRoomsDto.ChatRoomDto chatRoomDto = ChatRoomsDto.ChatRoomDto.builder()
                        .chatRoomId(chatRoom.getId())
                        .isHost(foundUser.isHost())
                        .roomTitle(chatRoom.getRoom().getTitle())
                        .checkinDate(reservation.getCheckinDate())
                        .checkoutDate(reservation.getCheckoutDate())
                        .build();
                chatRoomDtos.add(chatRoomDto);
            }
        });

        return ChatRoomsDto.builder().rooms(chatRoomDtos).build();
    }

    public IdResponseDto createChatRoom(ChatRoomRequestDto chatRoomRequestDto, User user) {
        Room room = roomRepository.findById(chatRoomRequestDto.getRoomId())
                .orElseThrow(() -> new NoSuchRoomException("해당 id의 방이 존재하지 않습니다."));
        User host = room.getUsers();
        //이전에 생성된 채팅방이 있는지 확인
        ChatRoom checkChatRoom = chatRoomRepository.findChatRoomByChatMembers(host, user);
        if (checkChatRoom != null) {
            return IdResponseDto.builder().id(checkChatRoom.getId()).build();
        }
        //채팅방이 없으면 새로 생성
        ChatRoom chatRoom = ChatRoom.createChatRoom(host, user, room);
        ChatRoom saved = chatRoomRepository.save(chatRoom);

        return IdResponseDto.builder().id(saved.getId()).build();
    }

    public IdResponseDto deleteChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NoSuchChatRoomException("해당 id의 채팅방이 존재하지 않습니다."));
        chatRoomRepository.delete(chatRoom);

        return IdResponseDto.builder().id(chatRoom.getId()).build();
    }

    public IdResponseDto createChatMessage(Long chatRoomId, User user, ChatMessageRequestDto chatMessageRequestDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NoSuchChatRoomException("해당 id의 채팅방이 존재하지 않습니다."));
        ChatMessage chatMessage = ChatMessage.createChatMessage(chatRoom, user, chatMessageRequestDto.getContent());
        chatRoom.addChatMessages(chatMessage);
        ChatMessage saved = chatMessageRepository.save(chatMessage);

        return IdResponseDto.builder().id(saved.getId()).build();
    }

    public ChatMessagesDto getChatMessages(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new NoSuchChatRoomException("해당 id의 채팅방이 존재하지 않습니다."));
        List<ChatMessage> chatMessages = chatRoom.getChatMessages().stream()
                //최근 메시지부터 보여주기
                .sorted((m1, m2) -> m2.getId().intValue() - m1.getId().intValue())
                .collect(Collectors.toList());
        List<ChatMessagesDto.ChatMessageDto> chatMessageDtos = new ArrayList<>();
        chatMessages.stream().forEach(chatMessage ->  {
            ChatMessagesDto.ChatMessageDto chatMessageDto = ChatMessagesDto.ChatMessageDto.builder()
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

    public IdResponseDto editChatMessage(Long chatMessageId, ChatMessageRequestDto chatMessageRequestDto) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageId).orElseThrow(() -> new NoSuchChatMessageException("해당 id의 메시지가 존재하지 않습니다."));
        chatMessage.editChatMessage(chatMessageRequestDto.getContent());

        return IdResponseDto.builder().id(chatMessage.getId()).build();
    }

    public IdResponseDto deleteChatMessage(Long chatMessageId) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageId).orElseThrow(() -> new NoSuchChatMessageException("해당 id의 메시지가 존재하지 않습니다."));
        chatMessageRepository.delete(chatMessage);

        return IdResponseDto.builder().id(chatMessage.getId()).build();
    }
}