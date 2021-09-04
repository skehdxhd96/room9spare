package com.goomoong.room9backend.service.chat;

import com.goomoong.room9backend.domain.chat.ChatRoom;
import com.goomoong.room9backend.domain.chat.dto.CreateChatRoomRequestDto;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.exception.NoSuchRoomException;
import com.goomoong.room9backend.exception.NoSuchUserException;
import com.goomoong.room9backend.repository.chat.ChatMessageRepository;
import com.goomoong.room9backend.repository.chat.ChatRoomRepository;
import com.goomoong.room9backend.repository.room.RoomRepository;
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
    public ChatRoom createChatRoom(CreateChatRoomRequestDto chatRoomRequestDto, User user) {
        User foundUser = userRepository.findById(chatRoomRequestDto.getUserId()).orElseThrow(() -> new NoSuchUserException("해당 id의 회원이 존재하지 않습니다."));
        ChatRoom chatRoom = ChatRoom.createChatRoom(foundUser, user);

        return chatRoomRepository.save(chatRoom);
    }
}
