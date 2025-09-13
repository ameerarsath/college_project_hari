package com.examly.springapp.controller;

import com.examly.springapp.model.ChatSession;
import com.examly.springapp.service.ChatSessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatSessionControllerTest {
    @Mock
    private ChatSessionService chatSessionService;

    @InjectMocks
    private ChatSessionController chatSessionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSession_success() {
        ChatSession cs = new ChatSession();
        cs.setCustomerId("c");
        cs.setAgentId("a");
        when(chatSessionService.createSession(any())).thenReturn(cs);
        ResponseEntity<?> res = chatSessionController.createSession(cs);
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
        assertEquals(cs, res.getBody());
    }

    @Test
    void getSessionById_found() {
        ChatSession s = new ChatSession();
        s.setCustomerId("cust1");
        when(chatSessionService.getSessionById(1L)).thenReturn(Optional.of(s));
        ResponseEntity<?> res = chatSessionController.getSessionById(1L);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(s, res.getBody());
    }

    @Test
    void getSessionById_notFound() {
        when(chatSessionService.getSessionById(404L)).thenReturn(Optional.empty());
        ResponseEntity<?> res = chatSessionController.getSessionById(404L);
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertEquals("Session not found", ((java.util.Map<?,?>)res.getBody()).get("message"));
    }

    @Test
    void closeSession_success() {
        ChatSession s = new ChatSession();
        s.setStatus("CLOSED");
        when(chatSessionService.closeSession(2L)).thenReturn(s);
        ResponseEntity<?> res = chatSessionController.closeSession(2L);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(s, res.getBody());
    }

    @Test
    void closeSession_notFound() {
        when(chatSessionService.closeSession(7L)).thenThrow(new RuntimeException("Session not found"));
        ResponseEntity<?> res = chatSessionController.closeSession(7L);
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertEquals("Session not found", ((java.util.Map<?,?>)res.getBody()).get("message"));
    }
}
