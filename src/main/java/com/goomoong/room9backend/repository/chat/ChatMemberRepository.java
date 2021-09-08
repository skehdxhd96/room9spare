package com.goomoong.room9backend.repository.chat;

import com.goomoong.room9backend.domain.chat.ChatMember;
import com.goomoong.room9backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {
    List<ChatMember> findAllByUser(User user);
}
