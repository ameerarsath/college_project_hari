package com.examly.springapp.service;

import com.examly.springapp.model.ChatSession;
import com.examly.springapp.repository.ChatSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.validation.ValidationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatSessionServiceTest {
    @Mock
    private ChatSessionRepository chatSessionRepository;

    @InjectMocks
    private ChatSessionService chatSessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSession_success() {
        ChatSession session = new ChatSession();
        session.setCustomerId("cust123");
        session.setAgentId("agent456");
        when(chatSessionRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        ChatSession res = chatSessionService.createSession(session);
        assertEquals("ACTIVE", res.getStatus());
        assertNotNull(res.getStartTime());
    }

    @Test
    void createSession_missingAgent() {
        ChatSession session = new ChatSession();
        session.setCustomerId("c1");
        session.setAgentId("");
        Exception ex = assertThrows(ValidationException.class, () -> chatSessionService.createSession(session));
        assertEquals("Agent ID must be provided", ex.getMessage());
    }

    @Test
    void getSessionById_found() {
        ChatSession session = new ChatSession();
        session.setCustomerId("c2");
        session.setAgentId("a2");
        when(chatSessionRepository.findById(111L)).thenReturn(Optional.of(session));
        Optional<ChatSession> res = chatSessionService.getSessionById(111L);
        assertTrue(res.isPresent());
        assertEquals("c2", res.get().getCustomerId());
    }

    @Test
    void getSessionById_notFound() {
        when(chatSessionRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<ChatSession> res = chatSessionService.getSessionById(99L);
        assertFalse(res.isPresent());
    }

    @Test
    void closeSession_success() {
        ChatSession session = new ChatSession();
        session.setStatus("ACTIVE");
        when(chatSessionRepository.findById(898L)).thenReturn(Optional.of(session));
        when(chatSessionRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        ChatSession res = chatSessionService.closeSession(898L);
        assertEquals("CLOSED", res.getStatus());
        assertNotNull(res.getEndTime());
    }

    @Test
    void closeSession_notFound() {
        when(chatSessionRepository.findById(67L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(RuntimeException.class, () -> chatSessionService.closeSession(67L));
        assertEquals("Session not found", ex.getMessage());
    }
}
