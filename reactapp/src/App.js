import React, { useState, useEffect, useCallback } from 'react';
import ChatHeader from './components/ChatHeader';
import ChatWindow from './components/ChatWindow';
import MessageInput from './components/MessageInput';
import * as api from './utils/api';
import { CHAT_STATUS } from './utils/constants';
import './App.css';

function App() {
  const [session, setSession] = useState(null);
  const [messages, setMessages] = useState([]);
  const [recipient] = useState({ name: 'Support Agent' });
  const [isOnline] = useState(true);
  const [isLoading, setIsLoading] = useState(false);
  const [myId] = useState('customer123'); // In real app, get from auth

  const initializeChat = useCallback(async () => {
    try {
      setIsLoading(true);
      const newSession = await api.createSession({
        customerId: myId,
        agentId: 'agent456'
      });
      setSession(newSession);
      
      // Load existing messages if any
      const sessionMessages = await api.getMessagesBySessionId(newSession.id);
      setMessages(sessionMessages || []);
    } catch (error) {
      console.error('Failed to initialize chat:', error);
    } finally {
      setIsLoading(false);
    }
  }, [myId]);

  useEffect(() => {
    // Initialize chat session
    initializeChat();
  }, [initializeChat]);


const handleSendMessage = async (content) => {
if (!session || !content.trim()) return;

try {
const messageData = {
content,
sessionId: session.id,
senderId: myId,
receiverId: session.agentId
};

// Optimistically add message to UI
const tempMessage = {
...messageData,
timestamp: new Date().toISOString(),
isRead: false
};
setMessages(prev => [...prev, tempMessage]);

// Send to server
const sentMessage = await api.createMessage(messageData);

// Update with server response
setMessages(prev =>
prev.map((msg, index) =>
index === prev.length - 1 ? sentMessage : msg
)
);
} catch (error) {
console.error('Failed to send message:', error);
// Remove failed message from UI
setMessages(prev => prev.slice(0, -1));
}
};

const handleEndChat = async () => {
if (!session) return;

try {
await api.closeSession(session.id);
setSession(null);
setMessages([]);
// Could redirect to feedback page or show confirmation
alert('Chat session ended successfully');
} catch (error) {
console.error('Failed to end chat:', error);
}
};

if (isLoading) {
return (
<div className="app-loading">
<div>Loading chat...</div>
</div>
);
}

return (
<div className="App">
<div className="chat-container">
<ChatHeader
recipient={recipient}
online={isOnline}
onEndChat={handleEndChat}
/>

<ChatWindow
messages={messages}
myId={myId}
/>

<MessageInput
onSend={handleSendMessage}
disabled={!session || session.status === CHAT_STATUS.CLOSED}
/>
</div>
</div>
);
}

export default App;