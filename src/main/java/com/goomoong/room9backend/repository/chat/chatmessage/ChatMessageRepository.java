package com.goomoong.room9backend.repository.chat.chatmessage;

import com.goomoong.room9backend.domain.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
