package com.example.androidexample;

import android.content.Intent;
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
                // Ensure WebSocketClient is initialized before sending message
                if (mWebSocketClient != null && mWebSocketClient.isOpen()) {
                    sendMessageToLobby(message);
                    messageEditText.setText(""); // Clear the input
                } else {
                    Toast.makeText(this, "Connection not established. Try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Start Game Button Action
        startGameButton.setOnClickListener(v -> {
            // Logic to start the game (e.g., transitioning to a new screen)
            Toast.makeText(this, "Game Starting!", Toast.LENGTH_SHORT).show();
            Intent levelIntent = new Intent(PregameChatActivity.this, LevelActivity.class);
            startActivity(levelIntent);
        });
    }

    private void initializeWebSocket() {
        try {
            URI serverURI = new URI("http://coms-3090-031.class.las.iastate.edu:8080/chat");  // Change this URL to your server's WebSocket URI
            mWebSocketClient = new WebSocketClient(serverURI) {

                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    // Connection opened
                    sendJoinRequest();
                }

                @Override
                public void onMessage(String message) {
                    // Ensure we are on the UI thread when updating UI
                    runOnUiThread(() -> {
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
                    });
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    // Connection closed
                }

                @Override
                public void onError(Exception ex) {
                    // Handle error
                    runOnUiThread(() -> {
                        Toast.makeText(PregameChatActivity.this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            };
            mWebSocketClient.connect();

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> {
                Toast.makeText(PregameChatActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void sendJoinRequest() {
        try {
            JSONObject joinRequest = new JSONObject();
            joinRequest.put("type", "joinLobby");
            joinRequest.put("user", userName);
            if (mWebSocketClient != null && mWebSocketClient.isOpen()) {
                mWebSocketClient.send(joinRequest.toString());
            }
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
            if (mWebSocketClient != null && mWebSocketClient.isOpen()) {
                mWebSocketClient.send(chatMessage.toString());
            }
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

        // Remove the conditional check and enable the button always
        startGameButton.setEnabled(true);  // Always enable Start Game button
    }

    private void updateChat(String sender, String message) {
        String currentText = lobbyTextView.getText().toString();
        String newText = currentText + "\n" + sender + ": " + message;
        runOnUiThread(() -> lobbyTextView.setText(newText));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebSocketClient != null) {
            mWebSocketClient.close();
        }
    }
}
