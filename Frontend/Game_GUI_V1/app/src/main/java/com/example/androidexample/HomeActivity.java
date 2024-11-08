package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class HomeActivity extends AppCompatActivity {
    private Button levelBtn;
    private WebSocketClient webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);  // Link to the activity_home.xml layout

        Button characterSelectionButton = findViewById(R.id.charSelectionBtn);
        Button proceedBtn = findViewById(R.id.btnProceed);
        Button diffSet = findViewById(R.id.btnDifSet);
        levelBtn = findViewById(R.id.levelBtn);

        // Set click listener for the button to navigate to the login/signup page (MainActivity)
        proceedBtn.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Set click listener for Difficulty Settings button
        diffSet.setOnClickListener(view -> {
            Intent diffIntent = new Intent(HomeActivity.this, DifficultySettingsActivity.class);
            startActivity(diffIntent);
        });

        // Set click listener for Character Selection button
        characterSelectionButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CharacterSelectionActivity.class);
            startActivity(intent);
        });

        // Set click listener for Game Level button
        levelBtn.setOnClickListener(view -> {
            connectWebSocket();  // Connect WebSocket when button is clicked

            // Navigate to LevelActivity
            Intent intent = new Intent(HomeActivity.this, LevelActivity.class);
            startActivity(intent);
        });
    }

    private void connectWebSocket() {
        try {
            URI uri = new URI("ws://coms-3090-031.class.las.iastate.edu:8080/countdown");
            webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("HomeActivity", "WebSocket Connection Opened");
                }

                @Override
                public void onMessage(String message) {
                    Log.d("HomeActivity", "WebSocket Message Received: " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("HomeActivity", "WebSocket Connection Closed: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    Log.e("HomeActivity", "WebSocket Error", ex);
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            Log.e("HomeActivity", "WebSocket URI Error", e);
        }
    }
}


////////////////////////////////
//package com.example.androidexample;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.handshake.ServerHandshake;
//
//
//public class HomeActivity extends AppCompatActivity {
//
//    private Button levelBtn;
//    private WebSocketClient webSocketClient;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);  // Link to the activity_home.xml layout
//
//        Button characterSelectionButton = findViewById(R.id.charSelectionBtn);
//
//        //Initialize the buttons
//        Button proceedBtn = findViewById(R.id.btnProceed);
//        Button diffSet = findViewById(R.id.btnDifSet);
//        // Initialize the button for Game Level
//        Button levelBtn = findViewById(R.id.levelBtn);
//
//        //Set click listener for the button to navigate to the login/signup page (MainActivity)
//        proceedBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Navigate to MainActivity (which will handle login and signup)
//                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //Set click listener for Difficulty Settings button
//        diffSet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Create an Intent to start SignupActivity
//                Intent diffIntent = new Intent(HomeActivity.this, DifficultySettingsActivity.class);
//                startActivity(diffIntent);  //Start DifficultySettingsActivity
//            }
//        });
//
//        //Set click listener for Character Selection button
//        characterSelectionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, CharacterSelectionActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //Set click listener for Game Level button
//        levelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Navigate to LevelActivity
//                Intent intent = new Intent(HomeActivity.this, LevelActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//}