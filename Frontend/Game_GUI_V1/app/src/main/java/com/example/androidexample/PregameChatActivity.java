package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class PregameChatActivity extends AppCompatActivity implements WebSocketListener{
    private Button connectBtn;
    private EditText serverEtx, usernameEtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_register);

        /* initialize UI elements */
        connectBtn = (Button) findViewById(R.id.connectBtn);
        serverEtx = (EditText) findViewById(R.id.serverEdt);
        usernameEtx = (EditText) findViewById(R.id.unameEdt);

        /* connect button listener */
        connectBtn.setOnClickListener(view -> {
            String serverUrl = serverEtx.getText().toString() + usernameEtx.getText().toString();

            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(PregameChatActivity.this);

            // got to chat activity
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void onWebSocketMessage(String message) {}

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {}

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}
}
