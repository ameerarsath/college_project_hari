import React from "react";
import { render, screen } from "@testing-library/react";
import ChatWindow from "../components/ChatWindow";

const sampleMessages = [
  {
    senderId: "cust123",
    receiverId: "agent456",
    content: "Test message 1",
    timestamp: "2023-11-01T10:30:00Z",
    isRead: true,
  },
  {
    senderId: "agent456",
    receiverId: "cust123",
    content: "Reply message 2",
    timestamp: "2023-11-01T10:32:00Z",
    isRead: true,
  },
  {
    senderId: "cust123",
    receiverId: "agent456",
    content: "Follow-up",
    timestamp: "2023-11-01T10:33:00Z",
    isRead: false,
  },
];

describe("ChatWindow", () => {
  it("renders messages correctly with distinction of sent and received", () => {
    render(<ChatWindow messages={sampleMessages} myId="cust123" />);
    const youLabels = screen.getAllByText("You");
    expect(youLabels.length).toBe(2); // 2 sent
    expect(screen.getByText("Reply message 2")).toBeInTheDocument();
    expect(screen.getByText("Test message 1")).toBeInTheDocument();
    expect(screen.getByText("Follow-up")).toBeInTheDocument();
  });
  it("shows empty state when no messages", () => {
    render(<ChatWindow messages={[]} myId="cust123" />);
    expect(screen.getByText("No messages yet.")).toBeInTheDocument();
  });
  it("should include timestamps and scrollable area", () => {
    render(<ChatWindow messages={sampleMessages} myId="agent456" />);
    expect(screen.getByTestId("chat-window")).toBeInTheDocument();
    expect(screen.getByTestId("timestamp-0")).toBeInTheDocument();
  });
});
