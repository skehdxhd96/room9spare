package com.goomoong.room9backend.domain.chat;

import com.goomoong.room9backend.domain.base.BaseEntity;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.user.User;
import lombok.*;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Room_id")
    private Room roomId;

    @ManyToMany
    @JoinTable(name = "users",
            joinColumns = @JoinColumn(name = "chatroom_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessages;
}
