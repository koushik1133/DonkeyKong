package com.example.demo.websocket;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Represents a WebSocket chat server for handling real-time communication
 * between users. Each user connects to the server using their unique
 * username.
 *
 * This class is annotated with Spring's `@ServerEndpoint` and `@Component`
 * annotations, making it a WebSocket endpoint that can handle WebSocket
 * connections at the "/chat/{username}" endpoint.
 *
 * Example URL: ws://localhost:8080/chat/username
 *
 * The server provides functionality for broadcasting messages to all connected
 * users and sending messages to specific users.
 */
@ServerEndpoint("/chat/{username}")
@Component
public class ChatServer {

    // Store all socket session and their corresponding username
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    // server side logger
    private final Logger logger = LoggerFactory.getLogger(ChatServer.class);

    /**
     * This method is called when a new WebSocket connection is established.
     *
     * @param session represents the WebSocket session for the connected user.
     * @param username username specified in path parameter.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {

        logger.info("[onOpen] User '{}' has entered the chat arena. Roll out the red carpet!", username);

        // Handle the case of a duplicate username
        if (usernameSessionMap.containsKey(username)) {
            session.getBasicRemote().sendText("Oops! Username '" + username + "' is already taken. Try again!");
            session.close();
        } else {
            // map current session with username
            sessionUsernameMap.put(session, username);
            usernameSessionMap.put(username, session);

            // Send a warm, ASCII-art welcome message to the new user
            String welcomeMessage = """
                +---------------------------+
                |  Welcome to the Chat!     |
                |        Have fun, %s!      |
                +---------------------------+
                """.formatted(username);
            sendMessageToUser(username, welcomeMessage);

            // Send join notification to everyone
            broadcast("🚀 " + username + " has landed in the chat!");
        }
    }

    /**
     * Handles incoming WebSocket messages from a client.
     *
     * @param session The WebSocket session representing the client's connection.
     * @param message The message received from the client.
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        // get the username by session
        String username = sessionUsernameMap.get(session);

        // server side log
        logger.info("[onMessage] '{}' says: {}", username, message);

        // Direct message to a user using the format "@username <message>"
        if (message.startsWith("@")) {
            String[] splitMessage = message.split("\\s+", 2);
            String destUsername = splitMessage[0].substring(1);
            String actualMessage = (splitMessage.length > 1) ? splitMessage[1] : "(whispered silence)";
            sendMessageToUser(destUsername, "[Private] " + username + " whispers: " + actualMessage);
            sendMessageToUser(username, "[Private] You whispered to " + destUsername + ": " + actualMessage);
        } else {
            broadcast(username + ": " + message);
        }
    }

    /**
     * Handles the closure of a WebSocket connection.
     *
     * @param session The WebSocket session that is being closed.
     */
    @OnClose
    public void onClose(Session session) throws IOException {

        // get the username from session-username mapping
        String username = sessionUsernameMap.get(session);

        logger.info("[onClose] '{}' has left the chat. Curtains close...", username);

        // Remove user from maps
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        // Notify everyone in the chat
        broadcast("😢 " + username + " has exited the chat. Goodbye!");
    }

    /**
     * Handles WebSocket errors that occur during the connection.
     *
     * @param session   The WebSocket session where the error occurred.
     * @param throwable The Throwable representing the error condition.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {

        // get the username from session-username mapping
        String username = sessionUsernameMap.get(session);

        // log the error with a touch of humor
        logger.warn("[onError] Oops! Something went wrong for '{}': {}", username, throwable.getMessage());
    }

    /**
     * Sends a message to a specific user in the chat (DM).
     *
     * @param username The username of the recipient.
     * @param message  The message to be sent.
     */
    private void sendMessageToUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.warn("[DM Exception] Failed to send message to '{}': {}", username, e.getMessage());
        }
    }

    /**
     * Broadcasts a message to all users in the chat.
     *
     * @param message The message to be broadcasted to all users.
     */
    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.warn("[Broadcast Exception] Couldn't reach '{}': {}", username, e.getMessage());
            }
        });
    }
}
