import * as api from "../utils/api";

jest.mock("axios", () => ({
  post: jest.fn(),
  get: jest.fn(),
  put: jest.fn(),
}));

const axios = require("axios");

describe("api integration", () => {
  afterEach(() => jest.clearAllMocks());

  it("createSession calls POST and returns session", async () => {
    axios.post.mockResolvedValueOnce({ data: { id: 1, customerId: "c1", agentId: "a1" } });
    const res = await api.createSession({ customerId: "c1", agentId: "a1" });
    expect(axios.post).toHaveBeenCalledWith(
      expect.stringContaining("/sessions"),
      { customerId: "c1", agentId: "a1" }
    );
    expect(res.customerId).toBe("c1");
  });

  it("getSessionById calls GET", async () => {
    axios.get.mockResolvedValueOnce({ data: { id: 2, customerId: "x" } });
    const s = await api.getSessionById(2);
    expect(axios.get).toHaveBeenCalledWith(expect.stringContaining("/sessions/2"));
    expect(s.id).toBe(2);
  });
  it("closeSession calls PUT", async () => {
    axios.put.mockResolvedValueOnce({ data: { id: 3, status: "CLOSED" } });
    const s = await api.closeSession(3);
    expect(axios.put).toHaveBeenCalledWith(expect.stringContaining("/sessions/3/close"));
    expect(s.status).toBe("CLOSED");
  });
  it("createMessage calls POST message", async () => {
    axios.post.mockResolvedValueOnce({ data: { content: "hello", sessionId: 4 } });
    const m = await api.createMessage({ content: "hello", sessionId: 4 });
    expect(axios.post).toHaveBeenCalledWith(
      expect.stringContaining("/messages"),
      { content: "hello", sessionId: 4 }
    );
    expect(m.content).toBe("hello");
  });
  it("getMessagesBySessionId calls GET", async () => {
    axios.get.mockResolvedValueOnce({ data: [{ content: "hi" }] });
    const m = await api.getMessagesBySessionId(98);
    expect(axios.get).toHaveBeenCalledWith(expect.stringContaining("/messages/98"));
    expect(m[0].content).toBe("hi");
  });
  it("markMessageAsRead works", async () => {
    axios.put.mockResolvedValueOnce({ data: { isRead: true } });
    const m = await api.markMessageAsRead(13);
    expect(axios.put).toHaveBeenCalledWith(expect.stringContaining("/messages/13/read"));
    expect(m.isRead).toBe(true);
  });
  it("handles error on createMessage", async () => {
    axios.post.mockRejectedValueOnce({ response: { data: { message: "fail" } } });
    await expect(api.createMessage({ content: "", sessionId: 0 })).rejects.toEqual(
      expect.objectContaining({ response: expect.any(Object) })
    );
  });
});
