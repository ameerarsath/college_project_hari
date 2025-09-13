package com.examly.springapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {
   
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
   
  @Column(name = "sender_id")
  private String senderId;
   
  @Column(name = "receiver_id")
  private String receiverId;
   
  @Column(name = "content", length = 500)
  private String content;
   
  @Column(name = "session_id")
  private Long sessionId;
   
  @Column(name = "timestamp")
  private LocalDateTime timestamp;
   
  @Column(name = "is_read")
  private Boolean isRead;
   
  // Default constructor
  public ChatMessage() {
    this.timestamp = LocalDateTime.now();
    this.isRead = false;
  }
   
  // Constructor with parameters
  public ChatMessage(String senderId, String receiverId, String content, Long sessionId) {
    this();
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.content = content;
    this.sessionId = sessionId;
  }
   
  // Getters and Setters
  public Long getId() {
    return id;
  }
   
  public void setId(Long id) {
    this.id = id;
  }
   
  public String getSenderId() {
    return senderId;
  }
   
  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }
   
  public String getReceiverId() {
    return receiverId;
  }
   
  public void setReceiverId(String receiverId) {
    this.receiverId = receiverId;
  }
   
  public String getContent() {
    return content;
  }
   
  public void setContent(String content) {
    this.content = content;
  }
   
  public Long getSessionId() {
    return sessionId;
  }
   
  public void setSessionId(Long sessionId) {
    this.sessionId = sessionId;
  }
   
  public LocalDateTime getTimestamp() {
    return timestamp;
  }
   
  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
   
  public Boolean getIsRead() {
    return isRead;
  }
   
  public void setIsRead(Boolean isRead) {
    this.isRead = isRead;
  }
}
