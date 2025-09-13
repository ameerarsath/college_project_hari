package com.examly.springapp.service;
import com.examly.springapp.model.ChatSession;
import com.examly.springapp.repository.ChatSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ChatSessionService {
 
 @Autowired
 private ChatSessionRepository chatSessionRepository;
 
 public ChatSession createSession(ChatSession session) {
 // Validate customer ID
 if (session.getCustomerId() == null || session.getCustomerId().trim().isEmpty()) {
 throw new ValidationException("Customer ID must be provided");
 }
 
 // Validate agent ID
 if (session.getAgentId() == null || session.getAgentId().trim().isEmpty()) {
 throw new ValidationException("Agent ID must be provided");
 }
 
 session.setStartTime(LocalDateTime.now());
 session.setStatus("ACTIVE");
 
 return chatSessionRepository.save(session);
 }
 
 public Optional<ChatSession> getSessionById(Long id) {
 return chatSessionRepository.findById(id);
 }
 
 public ChatSession closeSession(Long sessionId) {
 Optional<ChatSession> sessionOpt = chatSessionRepository.findById(sessionId);
 
 if (sessionOpt.isEmpty()) {
 throw new RuntimeException("Session not found");
 }
 
 ChatSession session = sessionOpt.get();
 session.setEndTime(LocalDateTime.now());
 session.setStatus("CLOSED");
 
 return chatSessionRepository.save(session);
 }
}
