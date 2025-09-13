import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import MessageInput from "../components/MessageInput";

describe("MessageInput", () => {
  it("renders input and button, disables send on empty", () => {
    render(<MessageInput onSend={() => {}} />);
    expect(screen.getByTestId("message-input")).toBeInTheDocument();
    expect(screen.getByTestId("send-btn")).toBeDisabled();
  });
  it("enables send when input is filled, clears after send", () => {
    const fn = jest.fn();
    render(<MessageInput onSend={fn} />);
    const input = screen.getByTestId("message-input");
    fireEvent.change(input, { target: { value: "hello" } });
    expect(screen.getByTestId("send-btn")).not.toBeDisabled();
    fireEvent.click(screen.getByTestId("send-btn"));
    expect(fn).toHaveBeenCalledWith("hello");
    expect(input.value).toBe("");
  });
  it("handles enter key to send", () => {
    const fn = jest.fn();
    render(<MessageInput onSend={fn} />);
    const input = screen.getByTestId("message-input");
    fireEvent.change(input, { target: { value: "one more" } });
    fireEvent.keyDown(input, { key: "Enter", code: "Enter" });
    expect(fn).toHaveBeenCalledWith("one more");
  });
  it("does not send when disabled", () => {
    const fn = jest.fn();
    render(<MessageInput onSend={fn} disabled={true} />);
    const input = screen.getByTestId("message-input");
    expect(input).toBeDisabled();
    expect(screen.getByTestId("send-btn")).toBeDisabled();
  });
});
