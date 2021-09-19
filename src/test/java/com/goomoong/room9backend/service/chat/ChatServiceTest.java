package com.goomoong.room9backend.service.chat;

import com.goomoong.room9backend.domain.chat.ChatMessage;
import com.goomoong.room9backend.domain.chat.ChatRoom;
import com.goomoong.room9backend.domain.chat.dto.*;
import com.goomoong.room9backend.domain.reservation.roomReservation;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.repository.chat.chatmessage.ChatMessageRepository;
import com.goomoong.room9backend.repository.chat.chatroom.ChatRoomRepository;
import com.goomoong.room9backend.repository.reservation.roomReservationRepository;
import com.goomoong.room9backend.repository.room.RoomRepository;
import com.goomoong.room9backend.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private ChatMessageRepository chatMessageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    roomReservationRepository roomReservationRepository;

    @InjectMocks
    private ChatService chatService;

    private User guest, host;
    private Room room;
    private roomReservation reservation;

    @BeforeEach
    void setUp() {
        guest = User.builder().id(1L).name("guest").role(Role.GUEST).build();
        host = User.builder().id(2L).name("host").role(Role.HOST).build();
        room = Room.builder().id(1L).users(host).title("happy house").build();
        reservation = roomReservation.builder().Id(1L).room(room).users(guest)
                .startDate(LocalDateTime.of(2021, 9, 13, 12, 0, 0))
                .finalDate(LocalDateTime.of(2021, 9, 14, 12, 0, 0))
                .build();
    }

    @Test
    void getChatRooms() {
        //given
        //로직 검증을 위해 mock 객체 사용
        User user = mock(User.class);
        List<ChatMessage> chatMessages = new ArrayList<>();
        ChatRoom chatRoom = ChatRoom.builder().id(1L).room(room).chatMessages(chatMessages).build();
        ChatMessage chatMessage = ChatMessage.builder().id(1L).chatRoom(chatRoom).user(user).content("test").build();
        chatRoom.addChatMessages(chatMessage);
        List<ChatRoom> chatRooms = new ArrayList<>();
        chatRooms.add(chatRoom);

        //when
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(user.getChatRooms()).thenReturn(chatRooms);
        when(user.isHost()).thenReturn(false);
        when(roomReservationRepository.findByRoomAndUsers(room, user)).thenReturn(reservation);
        ChatRoomsDto chatRoomsDto = chatService.getChatRooms(user);

        //then
        ChatRoomsDto.ChatRoomDto roomDto = chatRoomsDto.getRooms().get(0);
        Assertions.assertEquals(roomDto.getChatRoomId(), room.getId());
        Assertions.assertEquals(roomDto.getRoomTitle(), room.getTitle());
    }

    @Test
    void createChatRoom() {
        //given
        ChatRoom chatRoom = ChatRoom.builder().id(1L).room(room).build();
        ChatRoomRequestDto chatRoomRequestDto = ChatRoomRequestDto.builder().roomId(1L).build();

        //when
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(chatRoomRepository.findChatRoomByChatMembers(host, guest)).thenReturn(null);
        when(chatRoomRepository.save(any(ChatRoom.class))).thenReturn(chatRoom);
        IdResponseDto responseDto = chatService.createChatRoom(chatRoomRequestDto, guest);

        //then
        Assertions.assertEquals(responseDto.getId(), chatRoom.getId());
    }

    @Test
    void deleteChatRoom() {
        //given
        ChatRoom chatRoom = ChatRoom.builder().id(1L).build();

        //when
        when(chatRoomRepository.findById(1L)).thenReturn(Optional.of(chatRoom));
        IdResponseDto responseDto = chatService.deleteChatRoom(1L);

        //then
        verify(chatRoomRepository, times(1)).delete(chatRoom);
        Assertions.assertEquals(responseDto.getId(), 1L);
    }

    @Test
    void createChatMessage() {
        //given
        List<ChatMessage> chatMessages = new ArrayList<>();
        ChatRoom chatRoom = ChatRoom.builder().id(1L).chatMessages(chatMessages).build();
        ChatMessageRequestDto requestDto = ChatMessageRequestDto.builder().content("test").build();
        ChatMessage chatMessage = ChatMessage.builder().id(1L).chatRoom(chatRoom).user(guest).content(requestDto.getContent()).build();

        //when
        when(chatRoomRepository.findById(1L)).thenReturn(Optional.of(chatRoom));
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(chatMessage);
        IdResponseDto responseDto = chatService.createChatMessage(1L, guest, requestDto);

        //then
        Assertions.assertEquals(chatMessage.getContent(), requestDto.getContent());
        Assertions.assertEquals(responseDto.getId(), 1L);
    }

    @Test
    void getChatMessages() {
        //given
        //로직 검증을 위해 mock 객체 사용
        ChatRoom chatRoom = mock(ChatRoom.class);
        List<ChatMessage> chatMessages = new ArrayList<>();
        ChatMessage guestMessage = ChatMessage.builder().id(1L).chatRoom(chatRoom).user(guest).content("hi").build();
        ChatMessage hostMessage = ChatMessage.builder().id(2L).chatRoom(chatRoom).user(host).content("hello").build();
        chatMessages.add(guestMessage);
        chatMessages.add(hostMessage);

        //when
        when(chatRoomRepository.findById(chatRoom.getId())).thenReturn(Optional.of(chatRoom));
        when(chatRoom.getChatMessages()).thenReturn(chatMessages);
        ChatMessagesDto chatMessagesDto = chatService.getChatMessages(chatRoom.getId());

        //then
        ChatMessagesDto.ChatMessageDto messageDto = chatMessagesDto.getMessages().get(0);
        Assertions.assertEquals(messageDto.getMessageId(), hostMessage.getId());
        Assertions.assertEquals(messageDto.getContent(), hostMessage.getContent());
    }

    @Test
    void editChatMessage() {
        //given
        ChatMessage chatMessage = ChatMessage.builder().id(1L).content("hi").build();
        ChatMessageRequestDto requestDto = ChatMessageRequestDto.builder().content("hello").build();

        //when
        when(chatMessageRepository.findById(1L)).thenReturn(Optional.of(chatMessage));
        IdResponseDto responseDto = chatService.editChatMessage(1L, requestDto);

        //then
        Assertions.assertEquals(chatMessage.getContent(), "hello");
        Assertions.assertEquals(responseDto.getId(), 1L);
    }

    @Test
    void deleteChatMessage() {
        //given
        ChatMessage chatMessage = ChatMessage.builder().id(1L).build();

        //when
        when(chatMessageRepository.findById(1L)).thenReturn(Optional.of(chatMessage));
        IdResponseDto responseDto = chatService.deleteChatMessage(1L);

        //then
        verify(chatMessageRepository, times(1)).delete(chatMessage);
        Assertions.assertEquals(responseDto.getId(), 1L);
    }
}