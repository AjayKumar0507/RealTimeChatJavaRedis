let ws = null;

function connectWebSocket() {
    const username = document.getElementById('username').value;
    if (!username) {
        alert('Please enter a username');
        return;
    }

    ws = new WebSocket(`ws://${window.location.host}/chat?username=${username}`);

    ws.onopen = () => {
        console.log('Connected to WebSocket');
    };

    ws.onmessage = (event) => {
        const message = JSON.parse(event.data);
        displayMessage(message);
    };

    ws.onclose = () => {
        console.log('Disconnected from WebSocket');
    };
}

function sendMessage() {
    const username = document.getElementById('username').value;
    const content = document.getElementById('message').value;

    if (!username || !content) {
        alert('Please enter both username and message');
        return;
    }

    if (!ws || ws.readyState !== WebSocket.OPEN) {
        connectWebSocket();
    }

    const message = {
        sender: username,
        content: content
    };

    ws.send(JSON.stringify(message));
    document.getElementById('message').value = '';
}

function displayMessage(message) {
    const messagesDiv = document.getElementById('chat-messages');
    const messageDiv = document.createElement('div');
    messageDiv.className = message.sender === 'System' ? 'message system-message' : 'message';
    messageDiv.textContent = `${message.sender}: ${message.content}`;
    messagesDiv.appendChild(messageDiv);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}

document.getElementById('username').addEventListener('change', () => {
    if (ws && ws.readyState === WebSocket.OPEN) {
        ws.close();
    }
    connectWebSocket();
});