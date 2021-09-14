package com.goomoong.room9backend.repository.chat.chatroom;

import com.goomoong.room9backend.domain.chat.ChatRoom;
import com.goomoong.room9backend.domain.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.goomoong.room9backend.domain.chat.QChatRoom.chatRoom;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom{

    private final JPAQueryFactory query;

    @Override
    public ChatRoom findChatRoomByChatMembers(User other, User me) {
        return query
                .select(chatRoom)
                .from(chatRoom)
                .where(chatRoom.chatMembers.contains(other), chatRoom.chatMembers.contains(me))
                .fetchOne();
    }
}
