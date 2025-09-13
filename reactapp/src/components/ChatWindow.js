import React from 'react';

const ChatWindow = ({ messages, myId }) => {
  if (!messages || messages.length === 0) {
    return (
      <div className="chat-window" data-testid="chat-window" style={{ padding: '20px', textAlign: 'center' }}>
        <p>No messages yet.</p>
      </div>
    );
  }

  return (
    <div 
      className="chat-window" 
      data-testid="chat-window"
      style={{
        height: '400px',
        overflowY: 'auto',
        padding: '16px',
        border: '1px solid #ddd',
        backgroundColor: '#f9f9f9'
      }}
    >
      {messages.map((message, index) => {
        const isSentByMe = message.senderId === myId;
        
        return (
          <div 
            key={index}
            className={`message ${isSentByMe ? 'sent' : 'received'}`}
            style={{
              marginBottom: '12px',
              display: 'flex',
              flexDirection: 'column',
              alignItems: isSentByMe ? 'flex-end' : 'flex-start'
            }}
          >
            <div 
              style={{
                maxWidth: '70%',
                padding: '8px 12px',
                borderRadius: '12px',
                backgroundColor: isSentByMe ? '#007bff' : '#e9ecef',
                color: isSentByMe ? 'white' : 'black'
              }}
            >
              <div className="message-sender" style={{ fontSize: '12px', marginBottom: '4px', opacity: 0.8 }}>
                {isSentByMe ? 'You' : 'Agent'}
              </div>
              <div className="message-content">
                {message.content}
              </div>
            </div>
            <div 
              className="message-timestamp"
              data-testid={`timestamp-${index}`}
              style={{
                fontSize: '11px',
                color: '#666',
                marginTop: '4px'
              }}
            >
              {new Date(message.timestamp).toLocaleTimeString()}
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default ChatWindow;