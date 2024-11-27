//New V2 this is where the websocket url needs to be called before we go to game screen
//package com.example.androidexample;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.handshake.ServerHandshake;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//public class JoinLobbyActivity extends AppCompatActivity {
//    private WebSocketClient webSocketClient;
//    private static final String SERVER_URL = "ws://coms-3090-031.class.las.iastate.edu:8080/lobby";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_join_lobby);
//
//        // Connect to the WebSocket server for the lobby
//        connectWebSocket();
//    }
//
//    private void connectWebSocket() {
//        try {
//            URI uri = new URI(SERVER_URL);
//            webSocketClient = new WebSocketClient(uri) {
//                @Override
//                public void onOpen(ServerHandshake handshakedata) {
//                    Log.d("JoinLobbyActivity", "WebSocket Connected to Lobby");
//                    // Notify the backend that this player has joined the lobby
//                    webSocketClient.send("JOIN_LOBBY");
//                }
//
//                @Override
//                public void onMessage(String message) {
//                    runOnUiThread(() -> {
//                        Log.d("JoinLobbyActivity", "Server Message: " + message);
//
//                        // Start the game when the server sends a "GAME_START" message
//                        if (message.equals("GAME_START")) {
//                            Toast.makeText(JoinLobbyActivity.this, "Game Starting!", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(JoinLobbyActivity.this, LevelActivity.class);
//                            startActivity(intent);
//                            finish(); // Close the lobby screen
//                        }
//                    });
//                }
//
//                @Override
//                public void onClose(int code, String reason, boolean remote) {
//                    Log.d("JoinLobbyActivity", "WebSocket Closed: " + reason);
//                }
//
//                @Override
//                public void onError(Exception ex) {
//                    Log.e("JoinLobbyActivity", "WebSocket Error", ex);
//                }
//            };
//            webSocketClient.connect();
//        } catch (URISyntaxException e) {
//            Log.e("JoinLobbyActivity", "WebSocket URI Error", e);
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (webSocketClient != null) {
//            webSocketClient.close(); // Close the WebSocket connection when leaving the lobby
//        }
//    }
//}










//Original V1
package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class JoinLobbyActivity extends AppCompatActivity {

    //Define input fields and buttons
    private Button joinLobbyBtn, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);  //Link to the activity_join_lobby.xml layout

        //Initialize buttons
        joinLobbyBtn = findViewById(R.id.btnJoinLobby);
        homeBtn = findViewById(R.id.btnHome);

        //Set click listener for the Join Lobby button
        joinLobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Implement join lobby logic here
                Toast.makeText(JoinLobbyActivity.this, "Joined the lobby successfully!", Toast.LENGTH_SHORT).show();
                Intent pregameChatIntent = new Intent(JoinLobbyActivity.this, PregameChatActivity.class);
                startActivity(pregameChatIntent);
            }
        });

        //Set click listener for the Home button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate back to the HomeActivity
                Intent homeIntent = new Intent(JoinLobbyActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();  //Close the current LoginActivity
            }
        });
    }
}
