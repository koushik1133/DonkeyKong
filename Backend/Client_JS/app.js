const socket = new WebSocket("ws://localhost:8080/game/position");

let lastPosition = { x: 0, y: 0 };

// Log when connection is established
socket.onopen = () => {
    console.log("WebSocket connection established!");
};

// Log messages received from the server
socket.onmessage = (message) => {
    console.log("Received message from server: ", message.data);
};

// Handle WebSocket errors
socket.onerror = (error) => {
    console.error("WebSocket Error: ", error);
};

// Handle connection close
socket.onclose = () => {
    console.log("WebSocket connection closed.");
};

// Listen to mousemove events to simulate "player movement"
document.addEventListener("mousemove", (event) => {
    const currentPosition = { x: event.clientX, y: event.clientY };

    // Only send if the position has changed
    if (currentPosition.x !== lastPosition.x || currentPosition.y !== lastPosition.y) {
        lastPosition = currentPosition;
        const positionData = JSON.stringify({
            x: currentPosition.x,
            y: currentPosition.y,
        });
        console.log("Sending new position: ", positionData);
        socket.send(positionData);
    }
});


/*
const socket = new WebSocket("ws://localhost:8080/game/position");

let lastPosition = { x: 0, y: 0 };

// Log when connection is established
socket.onopen = () => {
    console.log("WebSocket connection established!");
};

// Log messages received from the server
socket.onmessage = (message) => {
    console.log("Received message from server: ", message.data);
};

// Handle WebSocket errors
socket.onerror = (error) => {
    console.error("WebSocket Error: ", error);
};

// Handle connection close
socket.onclose = () => {
    console.log("WebSocket connection closed.");
};

// Listen to mousemove events to simulate "player movement"
document.addEventListener("mousemove", (event) => {
    const currentPosition = { x: event.clientX, y: event.clientY };

    // Only send if the position has changed
    if (currentPosition.x !== lastPosition.x || currentPosition.y !== lastPosition.y) {
        lastPosition = currentPosition;
        const positionData = JSON.stringify({
            x: currentPosition.x,
            y: currentPosition.y,
        });
        console.log("Sending new position: ", positionData);
        socket.send(positionData);
    }
});
*/
