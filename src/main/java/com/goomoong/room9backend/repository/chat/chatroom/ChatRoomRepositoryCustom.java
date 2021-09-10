package com.goomoong.room9backend.repository.chat.chatroom;

import com.goomoong.room9backend.domain.chat.ChatRoom;
import com.goomoong.room9backend.domain.user.User;

public interface ChatRoomRepositoryCustom {
    ChatRoom findChatRoomByChatMembers(User other, User me);
}
