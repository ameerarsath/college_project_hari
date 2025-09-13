import React from 'react';

const ChatHeader = ({ recipient, online, onEndChat }) => {
  return (
    <div className="chat-header">
      <div className="recipient-info">
        <div className="recipient-name">
          <span data-testid="recipient-name">{recipient?.name || 'Unknown'}</span>
        </div>
        <div className="status-container">
          {online && (
            <div 
              className="online-dot" 
              data-testid="online-dot"
              style={{
                width: '8px',
                height: '8px',
                backgroundColor: '#4CAF50',
                borderRadius: '50%',
                display: 'inline-block',
                marginRight: '5px'
              }}
            />
          )}
          <span data-testid="status-text" className="status-text">
            {online ? 'Online' : 'Offline'}
          </span>
        </div>
      </div>
      
      <div className="header-actions">
        <button 
          data-testid="end-chat-btn"
          className="end-chat-btn"
          onClick={onEndChat}
          style={{
            padding: '8px 16px',
            backgroundColor: '#f44336',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer'
          }}
        >
          End Chat
        </button>
      </div>
    </div>
  );
};

export default ChatHeader;