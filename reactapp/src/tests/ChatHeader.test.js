import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import ChatHeader from "../components/ChatHeader";

describe("ChatHeader", () => {
  const mockRecipient = { name: "Support Agent" };
  it("shows recipient name and online dot", () => {
    render(<ChatHeader recipient={mockRecipient} online={true} onEndChat={() => {}} />);
    expect(screen.getByTestId("recipient-name").textContent).toBe("Support Agent");
    expect(screen.getByTestId("online-dot")).toBeInTheDocument();
    expect(screen.getByTestId("status-text").textContent).toBe("Online");
  });
  it("shows offline status correctly", () => {
    render(<ChatHeader recipient={mockRecipient} online={false} onEndChat={() => {}} />);
    expect(screen.getByTestId("status-text").textContent).toBe("Offline");
  });
  it("calls onEndChat when button clicked", () => {
    const fn = jest.fn();
    render(<ChatHeader recipient={mockRecipient} online={true} onEndChat={fn} />);
    fireEvent.click(screen.getByTestId("end-chat-btn"));
    expect(fn).toBeCalled();
  });
});
