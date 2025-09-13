package com.examly.springapp.service;

import com.examly.springapp.model.ChatMessage;
import com.examly.springapp.repository.ChatMessageRepository;
import com.examly.springapp.repository.ChatSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageService {
 
 @Autowired
 private ChatMessageRepository chatMessageRepository;
 
 @Autowired
 private ChatSessionRepository chatSessionRepository;
 
 public ChatMessage createMessage(ChatMessage message) {
 // Validate sender ID
 if (message.getSenderId() == null || message.getSenderId().trim().isEmpty()) {
 throw new ValidationException("Sender ID must be provided");
 }
 

 
 // Validate message content
 if (message.getContent() == null || message.getContent().trim().isEmpty() || message.getContent().length() > 500) {
 throw new ValidationException("Message content cannot be empty or exceed 500 characters");
 }
 
 // Validate session ID
 if (message.getSessionId() == null || !chatSessionRepository.existsById(message.getSessionId())) {
 throw new ValidationException("Session ID is invalid");
 }
 
 message.setTimestamp(LocalDateTime.now());
 message.setIsRead(false);
 
 return chatMessageRepository.save(message);
 }
 
 public List<ChatMessage> getMessagesBySessionId(Long sessionId) {
 return chatMessageRepository.findBySessionIdOrderByTimestampAsc(sessionId);
 }
 
 public ChatMessage markAsRead(Long messageId) {
 Optional<ChatMessage> messageOpt = chatMessageRepository.findById(messageId);
 
 if (messageOpt.isEmpty()) {
 throw new RuntimeException("Message not found");
 }
 
 ChatMessage message = messageOpt.get();
 message.setIsRead(true);
 
 return chatMessageRepository.save(message);
 }
}
