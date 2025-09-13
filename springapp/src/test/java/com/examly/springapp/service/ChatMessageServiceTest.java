package com.examly.springapp.service;

import com.examly.springapp.model.ChatMessage;
import com.examly.springapp.model.ChatSession;
import com.examly.springapp.repository.ChatMessageRepository;
import com.examly.springapp.repository.ChatSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatMessageServiceTest {
    @Mock
    private ChatMessageRepository chatMessageRepository;

    @Mock
    private ChatSessionRepository chatSessionRepository;

    @InjectMocks
    private ChatMessageService chatMessageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMessage_success() {
        ChatMessage msg = new ChatMessage();
        msg.setSenderId("customer");
        msg.setReceiverId("agent");
        msg.setContent("Hello");
        msg.setSessionId(1L);

        when(chatSessionRepository.existsById(1L)).thenReturn(true);
        when(chatMessageRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        ChatMessage saved = chatMessageService.createMessage(msg);
        assertEquals("Hello", saved.getContent());
        assertNotNull(saved.getTimestamp());
        assertFalse(saved.getIsRead());
    }

    @Test
    void createMessage_invalidContent() {
        ChatMessage msg = new ChatMessage();
        msg.setSenderId("c");
        msg.setReceiverId("a");
        msg.setSessionId(1L);
        msg.setContent("");
        Exception ex = assertThrows(ValidationException.class, () -> chatMessageService.createMessage(msg));
        assertEquals("Message content cannot be empty or exceed 500 characters", ex.getMessage());
    }

    @Test
    void createMessage_missingSender() {
        ChatMessage msg = new ChatMessage();
        msg.setContent("hi");
        msg.setReceiverId("agg");
        msg.setSessionId(2L);
        Exception ex = assertThrows(ValidationException.class, () -> chatMessageService.createMessage(msg));
        assertEquals("Sender ID must be provided", ex.getMessage());
    }

    @Test
    void createMessage_invalidSession() {
        ChatMessage msg = new ChatMessage();
        msg.setSenderId("id");
        msg.setReceiverId("b");
        msg.setContent("abc");
        msg.setSessionId(5L);
        when(chatSessionRepository.existsById(5L)).thenReturn(false);
        Exception ex = assertThrows(ValidationException.class, () -> chatMessageService.createMessage(msg));
        assertEquals("Session ID is invalid", ex.getMessage());
    }

    @Test
    void getMessagesBySessionId() {
        ChatMessage m1 = new ChatMessage();
        m1.setSessionId(110L);
        m1.setContent("test session msg");
        when(chatMessageRepository.findBySessionIdOrderByTimestampAsc(110L)).thenReturn(List.of(m1));
        List<ChatMessage> messages = chatMessageService.getMessagesBySessionId(110L);
        assertEquals(1, messages.size());
        assertEquals("test session msg", messages.get(0).getContent());
    }

    @Test
    void markAsRead_success() {
        ChatMessage m = new ChatMessage();
        m.setIsRead(false);
        m.setSenderId("abc");
        m.setReceiverId("def");
        m.setContent("msg");
        m.setSessionId(1L);
        m.setId(55L);
        when(chatMessageRepository.findById(55L)).thenReturn(Optional.of(m));
        when(chatMessageRepository.save(any())).thenReturn(m);
        ChatMessage updated = chatMessageService.markAsRead(55L);
        assertTrue(updated.getIsRead());
    }

    @Test
    void markAsRead_notFound() {
        when(chatMessageRepository.findById(123L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(RuntimeException.class, () -> chatMessageService.markAsRead(123L));
        assertEquals("Message not found", ex.getMessage());
    }
}
