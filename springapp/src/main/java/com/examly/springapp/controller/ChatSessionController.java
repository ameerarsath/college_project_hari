package com.examly.springapp.controller;

import com.examly.springapp.model.ChatSession;
import com.examly.springapp.service.ChatSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ChatSessionController {
 
 @Autowired
 private ChatSessionService chatSessionService;
 
 @PostMapping("/sessions")
 public ResponseEntity<?> createSession(@RequestBody ChatSession session) {
 try {
 ChatSession createdSession = chatSessionService.createSession(session);
 return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
 } catch (RuntimeException e) {
 // Handle validation errors (like missing agent)
 Map<String, String> error = new HashMap<>();
 error.put("message", e.getMessage());
 return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
 } catch (Exception e) {
 Map<String, String> error = new HashMap<>();
 error.put("message", "Internal server error");
 return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
 }
 }
 
 @GetMapping("/sessions/{id}")
 public ResponseEntity<?> getSessionById(@PathVariable Long id) {
 try {
 Optional<ChatSession> session = chatSessionService.getSessionById(id);
 if (session.isPresent()) {
 return new ResponseEntity<>(session.get(), HttpStatus.OK);
 } else {
 Map<String, String> error = new HashMap<>();
 error.put("message", "Session not found");
 return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
 }
 } catch (Exception e) {
 Map<String, String> error = new HashMap<>();
 error.put("message", "Internal server error");
 return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
 }
 }
 
 @PutMapping("/sessions/{id}/close")
 public ResponseEntity<?> closeSession(@PathVariable Long id) {
 try {
 ChatSession updatedSession = chatSessionService.closeSession(id);
 return new ResponseEntity<>(updatedSession, HttpStatus.OK);
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
 
 @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
 public ResponseEntity<?> handleOptions() {
 return ResponseEntity.ok().build();
 }
}