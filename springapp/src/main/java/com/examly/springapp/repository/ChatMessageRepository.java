package com.examly.springapp.repository;

import com.examly.springapp.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
 List<ChatMessage> findBySenderIdOrReceiverId(String senderId, String receiverId);

 List<ChatMessage> findBySessionIdOrderByTimestampAsc(Long sessionId);
}
