package com.goomoong.room9backend.repository.chat;

import com.goomoong.room9backend.domain.chat.ChatMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {
}
