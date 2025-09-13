import React, { useState } from 'react';

const MessageInput = ({ onSend, disabled = false }) => {
  const [message, setMessage] = useState('');

  const handleSend = () => {
    if (message.trim() && !disabled) {
      onSend(message.trim());
      setMessage('');
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSend();
    }
  };

  const canSend = message.trim().length > 0 && !disabled;

  return (
    <div className="message-input-container" style={{ display: 'flex', gap: '8px', padding: '16px' }}>
      <input
        type="text"
        data-testid="message-input"
        className="message-input"
        placeholder="Type your message..."
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        onKeyDown={handleKeyDown}
        disabled={disabled}
        style={{
          flex: 1,
          padding: '10px',
          border: '1px solid #ddd',
          borderRadius: '20px',
          outline: 'none',
          fontSize: '14px'
        }}
      />
      <button
        data-testid="send-btn"
        className="send-btn"
        onClick={handleSend}
        disabled={!canSend}
        style={{
          padding: '10px 20px',
          backgroundColor: canSend ? '#007bff' : '#ccc',
          color: 'white',
          border: 'none',
          borderRadius: '20px',
          cursor: canSend ? 'pointer' : 'not-allowed',
          fontSize: '14px'
        }}
      >
        Send
      </button>
    </div>
  );
};

export default MessageInput;