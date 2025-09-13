# Implementation Plan Checklist (REPLANNED)

## Original Question/Task

**Question:** <h1>Real-Time Chat Support System</h1>

<h2>Overview</h2>
<p>You are tasked with developing a real-time chat support system that allows customers to communicate with support agents in a one-to-one chat environment. The system will consist of a React frontend for the chat interface and a Spring Boot backend to handle message processing and storage.</p>

<h2>Question Requirements</h2>

<h3>Backend Requirements (Spring Boot)</h3>

<h4>1. Chat Message Model</h4>
<p>Create a <code>ChatMessage</code> entity with the following attributes:</p>
<ul>
    <li><code>id</code> (Long): Unique identifier for the message</li>
    <li><code>senderId</code> (String): ID of the message sender (customer or agent)</li>
    <li><code>receiverId</code> (String): ID of the message recipient</li>
    <li><code>content</code> (String): The actual message text</li>
    <li><code>timestamp</code> (LocalDateTime): When the message was sent</li>
    <li><code>isRead</code> (Boolean): Whether the message has been read</li>
</ul>

<h4>2. Chat Session Model</h4>
<p>Create a <code>ChatSession</code> entity with the following attributes:</p>
<ul>
    <li><code>id</code> (Long): Unique identifier for the chat session</li>
    <li><code>customerId</code> (String): ID of the customer</li>
    <li><code>agentId</code> (String): ID of the support agent</li>
    <li><code>startTime</code> (LocalDateTime): When the chat session started</li>
    <li><code>endTime</code> (LocalDateTime): When the chat session ended (null if active)</li>
    <li><code>status</code> (String): Status of the session (ACTIVE, CLOSED)</li>
</ul>

<h4>3. REST API Endpoints</h4>

<p><b>Chat Message Controller</b></p>
<ul>
    <li><code>POST /api/messages</code>: Create a new chat message
        <ul>
            <li>Request Body: ChatMessage object (without id)</li>
            <li>Response: Created ChatMessage with id and timestamp</li>
            <li>Status Codes:
                <ul>
                    <li>201 (Created): Message successfully created</li>
                    <li>400 (Bad Request): Invalid message data</li>
                </ul>
            </li>
        </ul>
    </li>
    <li><code>GET /api/messages/{sessionId}</code>: Get all messages for a specific chat session
        <ul>
            <li>Path Variable: sessionId (Long)</li>
            <li>Response: List of ChatMessage objects</li>
            <li>Status Codes:
                <ul>
                    <li>200 (OK): Messages retrieved successfully</li>
                    <li>404 (Not Found): Session not found</li>
                </ul>
            </li>
        </ul>
    </li>
    <li><code>PUT /api/messages/{messageId}/read</code>: Mark a message as read
        <ul>
            <li>Path Variable: messageId (Long)</li>
            <li>Response: Updated ChatMessage</li>
            <li>Status Codes:
                <ul>
                    <li>200 (OK): Message updated successfully</li>
                    <li>404 (Not Found): Message not found</li>
                </ul>
            </li>
        </ul>
    </li>
</ul>

<p><b>Chat Session Controller</b></p>
<ul>
    <li><code>POST /api/sessions</code>: Create a new chat session
        <ul>
            <li>Request Body: ChatSession object (without id)</li>
            <li>Response: Created ChatSession with id and startTime</li>
            <li>Status Codes:
                <ul>
                    <li>201 (Created): Session successfully created</li>
                    <li>400 (Bad Request): Invalid session data</li>
                </ul>
            </li>
        </ul>
    </li>
    <li><code>GET /api/sessions/{id}</code>: Get a specific chat session
        <ul>
            <li>Path Variable: id (Long)</li>
            <li>Response: ChatSession object</li>
            <li>Status Codes:
                <ul>
                    <li>200 (OK): Session retrieved successfully</li>
                    <li>404 (Not Found): Session not found</li>
                </ul>
            </li>
        </ul>
    </li>
    <li><code>PUT /api/sessions/{id}/close</code>: Close a chat session
        <ul>
            <li>Path Variable: id (Long)</li>
            <li>Response: Updated ChatSession with endTime and status=CLOSED</li>
            <li>Status Codes:
                <ul>
                    <li>200 (OK): Session closed successfully</li>
                    <li>404 (Not Found): Session not found</li>
                </ul>
            </li>
        </ul>
    </li>
</ul>

<h4>4. Service Layer</h4>
<p>Implement the following services:</p>
<ul>
    <li><code>ChatMessageService</code>: Handle message operations (create, retrieve, mark as read)</li>
    <li><code>ChatSessionService</code>: Handle session operations (create, retrieve, close)</li>
</ul>

<h4>5. Data Validation</h4>
<p>Implement the following validations:</p>
<ul>
    <li>Message content cannot be empty or exceed 500 characters</li>
    <li>Sender and receiver IDs must be provided</li>
    <li>Chat session must have both customer and agent IDs</li>
</ul>

<h3>Frontend Requirements (React)</h3>

<h4>1. Chat Interface Components</h4>

<p><b>ChatWindow Component</b></p>
<ul>
    <li>Display messages in a scrollable container</li>
    <li>Show sender name and timestamp for each message</li>
    <li>Visually distinguish between sent and received messages (different colors/alignment)</li>
    <li>Auto-scroll to the latest message when a new message arrives</li>
</ul>

<p><b>MessageInput Component</b></p>
<ul>
    <li>Text input field for typing messages</li>
    <li>Send button to submit messages</li>
    <li>Disable the send button when the input is empty</li>
    <li>Clear the input field after sending a message</li>
</ul>

<p><b>ChatHeader Component</b></p>
<ul>
    <li>Display the name of the person you're chatting with</li>
    <li>Show online/offline status</li>
    <li>Include an "End Chat" button that closes the session</li>
</ul>

<h4>2. API Integration</h4>
<ul>
    <li>Implement functions to call the backend API endpoints</li>
    <li>Use fetch or axios for API calls</li>
    <li>Handle loading states and errors appropriately</li>
</ul>

<h4>3. State Management</h4>
<ul>
    <li>Manage chat messages and session state using React hooks</li>
    <li>Implement proper error handling for API calls</li>
    <li>Update UI in response to state changes</li>
</ul>

<h4>4. Mock Data for Testing</h4>
<p>Use the following mock data for development and testing:</p>

<p><b>Sample Users:</b></p>
<ul>
    <li>Customer: { id: "cust123", name: "John Doe" }</li>
    <li>Agent: { id: "agent456", name: "Support Agent" }</li>
</ul>

<p><b>Sample Messages:</b></p>
<ul>
    <li>{ senderId: "cust123", receiverId: "agent456", content: "Hello, I need help with my order", timestamp: "2023-11-01T10:30:00", isRead: true }</li>
    <li>{ senderId: "agent456", receiverId: "cust123", content: "Hi John, I'd be happy to help. What's your order number?", timestamp: "2023-11-01T10:32:00", isRead: true }</li>
    <li>{ senderId: "cust123", receiverId: "agent456", content: "It's #ORD-12345", timestamp: "2023-11-01T10:33:00", isRead: false }</li>
</ul>

<h3>Implementation Notes</h3>
<ul>
    <li>Use MySQL as the backend database</li>
    <li>Implement proper error handling for all API endpoints</li>
    <li>Use appropriate HTTP status codes for API responses</li>
    <li>Ensure the UI is user-friendly and responsive</li>
    <li>Follow React best practices for component structure and state management</li>
</ul>

<h3>Example Scenarios</h3>

<h4>Scenario 1: Starting a New Chat</h4>
<p>When a customer initiates a chat, the system should:</p>
<ol>
    <li>Create a new chat session with status "ACTIVE"</li>
    <li>Display the chat interface with an empty message list</li>
    <li>Allow the customer to send the first message</li>
</ol>

<h4>Scenario 2: Sending and Receiving Messages</h4>
<p>When a user sends a message:</p>
<ol>
    <li>The message should be saved to the database</li>
    <li>The message should appear in the chat window immediately</li>
    <li>The message should be marked as "unread" for the recipient</li>
</ol>

<h4>Scenario 3: Ending a Chat</h4>
<p>When a user clicks the "End Chat" button:</p>
<ol>
    <li>The chat session status should be updated to "CLOSED"</li>
    <li>The endTime should be set to the current time</li>
    <li>The chat interface should display a message indicating the chat has ended</li>
</ol>

**Created:** 2025-07-28 05:52:00 (Replan #2)
**Total Steps:** 2
**Previous Execution:** 0 steps completed before replanning

## Replanning Context
- **Replanning Attempt:** #2
- **Trigger:** V2 execution error encountered

## NEW Implementation Plan Checklist

### Step 1: Break Down Fix for Jest/Axios ESM Parsing Error in App.test.js Integration Test Into Sequential Micro-Steps
- [ ] **Status:** ⏳ Not Started
- **Files to create:**
  - /home/coder/project/workspace/question_generation_service/solutions/93e21f42-0b50-45e0-ba1c-8844ae07fbbe/reactapp/jest.config.js
- **Files to modify:**
  - /home/coder/project/workspace/question_generation_service/solutions/93e21f42-0b50-45e0-ba1c-8844ae07fbbe/reactapp/package.json
  - /home/coder/project/workspace/question_generation_service/solutions/93e21f42-0b50-45e0-ba1c-8844ae07fbbe/reactapp/src/App.test.js
- **Description:** Executes granular, stepwise fixes for the ESM/Babel parsing error with axios in Jest, ensuring Jest properly transforms axios and test files, or falls back to fetch mocking if necessary.

### Step 2: Build, Lint, and Test the Front-End React Application
- [ ] **Status:** ⏳ Not Started
- **Description:** Verifies integrity of the entire React+Jest UI implementation and ensures all requirements and tests have been met for frontend deliverables.

## NEW Plan Completion Status

| Step | Status | Completion Time |
|------|--------|----------------|
| Step 1 | ⏳ Not Started | - |
| Step 2 | ⏳ Not Started | - |

## Notes & Issues

### Replanning History
- Replan #2: V2 execution error encountered

### Errors Encountered
- None yet in new plan

### Important Decisions
- Plan was updated due to execution issues

### Next Actions
- Resume implementation following the NEW checklist
- Use `update_plan_checklist_tool` to mark steps as completed
- Use `read_plan_checklist_tool` to check current status

---
*This checklist was updated due to replanning. Previous progress is preserved above.*