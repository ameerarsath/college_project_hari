// API Configuration
export const API_ENDPOINTS = {
  SESSIONS: '/sessions',
  MESSAGES: '/messages',
  USERS: '/users',
  AGENTS: '/agents'
};

// Chat Status Constants
export const CHAT_STATUS = {
  ACTIVE: 'ACTIVE',
  CLOSED: 'CLOSED',
  PENDING: 'PENDING',
  WAITING: 'WAITING'
};

// User Types
export const USER_TYPES = {
  CUSTOMER: 'CUSTOMER',
  AGENT: 'AGENT',
  ADMIN: 'ADMIN'
};

// Message Types
export const MESSAGE_TYPES = {
  TEXT: 'TEXT',
  IMAGE: 'IMAGE',
  FILE: 'FILE',
  SYSTEM: 'SYSTEM'
};

// Connection Status
export const CONNECTION_STATUS = {
  ONLINE: 'ONLINE',
  OFFLINE: 'OFFLINE',
  AWAY: 'AWAY',
  BUSY: 'BUSY'
};

// Event Types for WebSocket
export const WS_EVENTS = {
  MESSAGE_RECEIVED: 'message_received',
  MESSAGE_SENT: 'message_sent',
  USER_TYPING: 'user_typing',
  USER_STOPPED_TYPING: 'user_stopped_typing',
  USER_JOINED: 'user_joined',
  USER_LEFT: 'user_left',
  SESSION_ENDED: 'session_ended'
};

// UI Constants
export const UI_CONSTANTS = {
  MAX_MESSAGE_LENGTH: 1000,
  TYPING_INDICATOR_TIMEOUT: 3000,
  AUTO_SCROLL_THRESHOLD: 100,
  CHAT_WINDOW_HEIGHT: 400
};

// Error Messages
export const ERROR_MESSAGES = {
  NETWORK_ERROR: 'Network error occurred. Please try again.',
  SESSION_NOT_FOUND: 'Chat session not found.',
  MESSAGE_SEND_FAILED: 'Failed to send message. Please try again.',
  CONNECTION_LOST: 'Connection lost. Attempting to reconnect...',
  UNAUTHORIZED: 'You are not authorized to perform this action.'
};