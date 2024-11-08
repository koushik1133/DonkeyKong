package backend.Countdown;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class CountdownHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private LocalDateTime endTime;

    // Start the countdown
    public void startCountdown(int durationInSeconds) {
        this.endTime = LocalDateTime.now().plusSeconds(durationInSeconds);
        new Thread(this::broadcastCountdown).start();
    }

    // Broadcast the countdown to all connected clients
    private void broadcastCountdown() {
        while (endTime != null && LocalDateTime.now().isBefore(endTime)) {
            Duration remaining = Duration.between(LocalDateTime.now(), endTime);
            long secondsRemaining = remaining.getSeconds();

            // Create JSON message
            String message = String.format("{\"seconds\": %d}", secondsRemaining);

            // Send the remaining time to all sessions
            for (WebSocketSession session : sessions) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(1000); // Wait for 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // After the countdown is over, notify all clients
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage("{\"message\": \"Countdown complete\"}"));
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Clear the endTime to indicate the countdown has finished
        endTime = null;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }
}
