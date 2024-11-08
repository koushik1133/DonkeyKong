package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

//import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;


public class MainActivity extends AppCompatActivity implements WebSocketListener{

    private Button connectBtn;
    private EditText serverEtx, usernameEtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initialize UI elements */
        connectBtn = (Button) findViewById(R.id.connectBtn);
        serverEtx = (EditText) findViewById(R.id.serverEdt);
        usernameEtx = (EditText) findViewById(R.id.unameEdt);

        /* connect button listener */
        connectBtn.setOnClickListener(view -> {
            String serverUrl = serverEtx.getText().toString() + usernameEtx.getText().toString();

            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(MainActivity.this);

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
    public void onWebSocketError(Exception ex) {
        System.out.println(ex);
    }
}

//package com.example.androidexample;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.handshake.ServerHandshake;
//import java.net.URI;
//
//public class MainActivity extends AppCompatActivity {
//    private WebSocketClient webSocketClient;
//    private Button connectBtn;
//    private EditText serverEtx, usernameEtx;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Initialize UI components
//        connectBtn = findViewById(R.id.connectBtn);
//        serverEtx = findViewById(R.id.serverEdt);
//        usernameEtx = findViewById(R.id.unameEdt);
//
//        // Set up connect button to initiate WebSocket connection
//        connectBtn.setOnClickListener(view -> {
//            String serverUrl = serverEtx.getText().toString().trim();
//            if (serverUrl.isEmpty()) {
//                Toast.makeText(this, "Please enter a server URL", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // Initialize WebSocket connection
//            setupWebSocket(serverUrl);
//        });
//    }
//
//    private void setupWebSocket(String serverUrl) {
//        URI uri = URI.create(serverUrl);
//
//        webSocketClient = new WebSocketClient(uri) {
//            @Override
//            public void onOpen(ServerHandshake handshakedata) {
//                Log.d("WebSocket", "Connected to server");
//                Toast.makeText(getApplicationContext(), "Connected to server", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onMessage(String message) {
//                // We’re not handling messages yet; focus is on connection logs
//            }
//
//            @Override
//            public void onClose(int code, String reason, boolean remote) {
//                Log.d("WebSocket", "Disconnected from server: " + reason);
//                Toast.makeText(getApplicationContext(), "Disconnected: " + reason, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(Exception ex) {
//                Log.e("WebSocket", "Connection error: " + ex.getMessage());
//                Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_SHORT).show();
//            }
//        };
//
//        webSocketClient.connect(); // Connect to WebSocket server
//    }
//}
