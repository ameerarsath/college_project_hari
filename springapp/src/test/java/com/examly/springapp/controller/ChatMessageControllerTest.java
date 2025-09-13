package com.examly.springapp.controller;

import com.examly.springapp.model.ChatMessage;
import com.examly.springapp.service.ChatMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatMessageControllerTest {
    @Mock
    private ChatMessageService chatMessageService;

    @InjectMocks
    private ChatMessageController chatMessageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMessage_success() {
        ChatMessage msg = new ChatMessage();
        msg.setSenderId("c");
        msg.setReceiverId("a");
        msg.setContent("hello");
        msg.setSessionId(1L);
        when(chatMessageService.createMessage(any())).thenReturn(msg);
        ResponseEntity<?> response = chatMessageController.createMessage(msg);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(msg, response.getBody());
    }

    @Test
    void getMessagesBySessionId_found() {
        ChatMessage msg1 = new ChatMessage();
        msg1.setContent("abc");
        when(chatMessageService.getMessagesBySessionId(5L)).thenReturn(List.of(msg1));
        ResponseEntity<?> response = chatMessageController.getMessagesBySessionId(5L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ChatMessage> list = (List<ChatMessage>) response.getBody();
        assertEquals(1, list.size());
    }

    @Test
    void getMessagesBySessionId_notFound() {
        when(chatMessageService.getMessagesBySessionId(8L)).thenReturn(List.of());
        ResponseEntity<?> response = chatMessageController.getMessagesBySessionId(8L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(((java.util.Map<?,?>) response.getBody()).get("message").toString().contains("Session not found"));
    }

    @Test
    void markAsRead_success() {
        ChatMessage msg = new ChatMessage();
        msg.setIsRead(true);
        when(chatMessageService.markAsRead(9L)).thenReturn(msg);
        ResponseEntity<?> response = chatMessageController.markAsRead(9L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(msg, response.getBody());
    }

    @Test
    void markAsRead_notFound() {
        when(chatMessageService.markAsRead(1L)).thenThrow(new RuntimeException("Message not found"));
        ResponseEntity<?> response = chatMessageController.markAsRead(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Message not found", ((java.util.Map<?,?>) response.getBody()).get("message"));
    }
}
