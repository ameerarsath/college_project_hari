package com.examly.springapp.controller;

import com.examly.springapp.model.ChatMessage;
import com.examly.springapp.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatMessageController {
 
 @Autowired
 private ChatMessageService chatMessageService;
 
 @PostMapping("/messages")
 public ResponseEntity<?> createMessage(@RequestBody ChatMessage message) {
 try {
 ChatMessage createdMessage = chatMessageService.createMessage(message);
 return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
 } catch (Exception e) {
 Map<String, String> error = new HashMap<>();
 error.put("message", "Internal server error");
 return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
 }
 }
 
 @GetMapping("/messages/{sessionId}")
 public ResponseEntity<?> getMessagesBySessionId(@PathVariable Long sessionId) {
 try {
 List<ChatMessage> messages = chatMessageService.getMessagesBySessionId(sessionId);
 if (messages.isEmpty()) {
 Map<String, String> error = new HashMap<>();
 error.put("message", "Session not found");
 return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
 }
 return new ResponseEntity<>(messages, HttpStatus.OK);
 } catch (Exception e) {
 Map<String, String> error = new HashMap<>();
 error.put("message", "Internal server error");
 return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
 }
 }
 
 @PutMapping("/messages/{messageId}/read")
 public ResponseEntity<?> markAsRead(@PathVariable Long messageId) {
 try {
 ChatMessage updatedMessage = chatMessageService.markAsRead(messageId);
 return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
 } catch (RuntimeException e) {
 Map<String, String> error = new HashMap<>();
 error.put("message", e.getMessage());
 return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
 } catch (Exception e) {
 Map<String, String> error = new HashMap<>();
 error.put("message", "Internal server error");
 return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
 }
 }
}


