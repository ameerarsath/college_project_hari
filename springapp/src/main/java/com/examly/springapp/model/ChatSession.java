package com.examly.springapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_sessions")
public class ChatSession {
 
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 
 @Column(name = "customer_id")
 private String customerId;
 
 @Column(name = "agent_id")
 private String agentId;
 
 @Column(name = "start_time")
 private LocalDateTime startTime;
 
 @Column(name = "end_time")
 private LocalDateTime endTime;
 
 @Column(name = "status")
 private String status;
 
 // Default constructor
 public ChatSession() {
 this.startTime = LocalDateTime.now();
 this.status = "ACTIVE";
 }
 
 // Constructor with parameters
 public ChatSession(String customerId, String agentId) {
 this();
 this.customerId = customerId;
 this.agentId = agentId;
 }
 
 // Getters and Setters
 public Long getId() {
 return id;
 }
 
 public void setId(Long id) {
 this.id = id;
 }
 
 public String getCustomerId() {
 return customerId;
 }
 
 public void setCustomerId(String customerId) {
 this.customerId = customerId;
 }
 
 public String getAgentId() {
 return agentId;
 }
 
 public void setAgentId(String agentId) {
 this.agentId = agentId;
 }
 
 public LocalDateTime getStartTime() {
 return startTime;
 }
 
 public void setStartTime(LocalDateTime startTime) {
 this.startTime = startTime;
 }
 
 public LocalDateTime getEndTime() {
 return endTime;
 }
 
 public void setEndTime(LocalDateTime endTime) {
 this.endTime = endTime;
 }
 
 public String getStatus() {
 return status;
 }
 
 public void setStatus(String status) {
 this.status = status;
 }
}
