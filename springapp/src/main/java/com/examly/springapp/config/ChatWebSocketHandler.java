package com.examly.springapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.Map;
import java.util.Set;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Set<WebSocketSession>> sessionRooms = new ConcurrentHashMap<>();
    private final Map<String, String> sessionToRoom = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection established: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = message.getPayload().toString();
        Map<String, Object> messageData = objectMapper.readValue(payload, Map.class);
        
        String type = (String) messageData.get("type");
        String sessionId = (String) messageData.get("sessionId");
        
        switch (type) {
            case "JOIN":
                joinRoom(session, sessionId);
                break;
            case "CHAT":
                broadcastMessage(sessionId, messageData);
                break;
            case "LEAVE":
                leaveRoom(session);
                break;
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("Transport error for session " + session.getId() + ": " + exception.getMessage());
        leaveRoom(session);
    }

@Override
public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
System.out.println("Connection closed: " + session.getId());
leaveRoom(session);
}

@Override
public boolean supportsPartialMessages() {
return false;
}

private void joinRoom(WebSocketSession session, String roomId) {
sessionRooms.computeIfAbsent(roomId, k -> new CopyOnWriteArraySet<>()).add(session);
sessionToRoom.put(session.getId(), roomId);
System.out.println("Session " + session.getId() + " joined room " + roomId);
}

private void leaveRoom(WebSocketSession session) {
String roomId = sessionToRoom.remove(session.getId());
if (roomId != null) {
Set<WebSocketSession> sessions = sessionRooms.get(roomId);
if (sessions != null) {
sessions.remove(session);
if (sessions.isEmpty()) {
sessionRooms.remove(roomId);
}
}
System.out.println("Session " + session.getId() + " left room " + roomId);
}
}

private void broadcastMessage(String roomId, Map<String, Object> messageData) {
Set<WebSocketSession> sessions = sessionRooms.get(roomId);
if (sessions != null) {
String messageJson;
try {
messageJson = objectMapper.writeValueAsString(messageData);
} catch (Exception e) {
System.err.println("Error serializing message: " + e.getMessage());
return;
}

sessions.removeIf(session -> {
try {
if (session.isOpen()) {
session.sendMessage(new TextMessage(messageJson));
return false;
} else {
return true;
}
} catch (IOException e) {
System.err.println("Error sending message to session " + session.getId() + ": " + e.getMessage());
return true;
}
});
}
}
}