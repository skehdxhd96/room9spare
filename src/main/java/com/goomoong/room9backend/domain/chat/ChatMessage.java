package com.goomoong.room9backend.domain.chat;

import com.goomoong.room9backend.domain.base.BaseEntity;
import com.goomoong.room9backend.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;

    public static ChatMessage createChatMessage(ChatRoom chatRoom, User user, String content) {
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .user(user)
                .content(content)
                .build();
    }

    public void editChatMessage(String content) {
        this.content = content;
    }
}
