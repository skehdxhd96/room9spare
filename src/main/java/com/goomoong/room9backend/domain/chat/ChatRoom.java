package com.goomoong.room9backend.domain.chat;

import com.goomoong.room9backend.domain.base.BaseEntity;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.user.Role;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Room_Id")
    private Room room;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public static ChatRoom createChatRoom(User other, User me, Room room) {
        List<User> chatMembers = new ArrayList<>();
        chatMembers.add(other);
        chatMembers.add(me);

        return ChatRoom.builder().chatMembers(chatMembers).room(room).build();
    }

    public void addChatMessages(ChatMessage chatMessage) {
        this.chatMessages.add(chatMessage);
    }

    public User getGuestFromChatMembers() {
        if (chatMembers.get(0).getRole() == Role.GUEST) {
            return chatMembers.get(0);
        } else {
            return chatMembers.get(1);
        }
    }
}
