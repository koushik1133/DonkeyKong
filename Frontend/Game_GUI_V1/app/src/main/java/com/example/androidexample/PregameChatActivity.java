package com.example.androidexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PregameChatActivity extends AppCompatActivity{

    private WebSocketClient mWebSocketClient;
    private TextView lobbyTextView;
    private EditText messageEditText;
    private Button sendButton, startGameButton;
    private List<String> playersInLobby = new ArrayList<>();
    private String userName = "User_" + (int) (Math.random() * 1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregamechat);

        lobbyTextView = findViewById(R.id.lobbyTextView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        startGameButton = findViewById(R.id.startGameButton);

        // Initialize WebSocket connection
        initializeWebSocket();

        // Send button action
        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString();
            if (!message.isEmpty()) {
                sendMessageToLobby(message);
                messageEditText.setText(""); // Clear the input
            }
        });

        // Start Game Button Action
        startGameButton.setOnClickListener(v -> {
            // Logic to start the game (e.g., transitioning to a new screen)
            Toast.makeText(this, "Game Starting!", Toast.LENGTH_SHORT).show();
        });
    }

    private void initializeWebSocket() {
        try {
            URI serverURI = new URI("ws://your.websocket.server.url");  // Change this URL to your server's WebSocket URI
            mWebSocketClient = new WebSocketClient(serverURI) {

                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    // Connection opened
                    sendJoinRequest();
                }

                @Override
                public void onMessage(String message) {
                    try {
                        JSONObject jsonMessage = new JSONObject(message);
                        String type = jsonMessage.getString("type");

                        if ("lobbyUpdate".equals(type)) {
                            JSONArray players = jsonMessage.getJSONArray("players");
                            updateLobby(players);
                        } else if ("message".equals(type)) {
                            String sender = jsonMessage.getString("sender");
                            String text = jsonMessage.getString("text");
                            updateChat(sender, text);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    // Connection closed
                }

                @Override
                public void onError(Exception ex) {
                    // Handle error
                }
            };
            mWebSocketClient.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendJoinRequest() {
        try {
            JSONObject joinRequest = new JSONObject();
            joinRequest.put("type", "joinLobby");
            joinRequest.put("user", userName);
            mWebSocketClient.send(joinRequest.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToLobby(String message) {
        try {
            JSONObject chatMessage = new JSONObject();
            chatMessage.put("type", "message");
            chatMessage.put("sender", userName);
            chatMessage.put("text", message);
            mWebSocketClient.send(chatMessage.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateLobby(JSONArray players) {
        playersInLobby.clear();
        for (int i = 0; i < players.length(); i++) {
            try {
                playersInLobby.add(players.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String lobbyText = "Lobby: " + String.join(", ", playersInLobby);
        lobbyTextView.setText(lobbyText);

        if (playersInLobby.size() == 3) {
            startGameButton.setEnabled(true);  // Enable Start Game button once 3 players are in the lobby
        } else {
            startGameButton.setEnabled(false); // Disable until lobby is full
        }
    }

    private void updateChat(String sender, String message) {
        String currentText = lobbyTextView.getText().toString();
        String newText = currentText + "\n" + sender + ": " + message;
        lobbyTextView.setText(newText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebSocketClient != null) {
            mWebSocketClient.close();
        }
    }
}
