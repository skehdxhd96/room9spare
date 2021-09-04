package com.goomoong.room9backend.domain.chat;

import com.goomoong.room9backend.domain.base.BaseEntity;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @ManyToMany
    @JoinTable(name = "chat_member",
            joinColumns = @JoinColumn(name = "chatroom_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> chatMembers = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public static ChatRoom createChatRoom(User other, User me) {
        List<User> chatMembers = new ArrayList<>();
        chatMembers.add(other);
        chatMembers.add(me);
        return ChatRoom.builder().chatMembers(chatMembers).build();
    }
}
