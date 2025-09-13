import axios from 'axios';

const BASE_URL = '/api';

// Session management
export const createSession = async (sessionData) => {
  try {
    const response = await axios.post(`${BASE_URL}/sessions`, sessionData);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getSessionById = async (sessionId) => {
  try {
    const response = await axios.get(`${BASE_URL}/sessions/${sessionId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const closeSession = async (sessionId) => {
  try {
    const response = await axios.put(`${BASE_URL}/sessions/${sessionId}/close`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

// Message management
export const createMessage = async (messageData) => {
  try {
    const response = await axios.post(`${BASE_URL}/messages`, messageData);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getMessagesBySessionId = async (sessionId) => {
  try {
    const response = await axios.get(`${BASE_URL}/messages/${sessionId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const markMessageAsRead = async (messageId) => {
  try {
    const response = await axios.put(`${BASE_URL}/messages/${messageId}/read`);
    return response.data;
  } catch (error) {
    throw error;
  }
};