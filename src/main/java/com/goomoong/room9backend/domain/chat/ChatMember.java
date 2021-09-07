package com.goomoong.room9backend.domain.chat;

import com.goomoong.room9backend.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatmember_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static ChatMember createChatMember(ChatRoom chatRoom, User user) {
        ChatMember chatMember = ChatMember.builder().chatRoom(chatRoom).user(user).build();

        return chatMember;
    }
}
