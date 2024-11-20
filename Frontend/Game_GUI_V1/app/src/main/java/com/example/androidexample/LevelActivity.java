//package com.example.androidexample;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Handler;
//import android.util.Log;
//import android.widget.TextView;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.io.OutputStream;
//import org.json.JSONObject;
//import android.content.Intent;
//
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.handshake.ServerHandshake;
//
//
//public class LevelActivity extends AppCompatActivity {
//    private ImageView player;
//    private boolean isJumping = false;
//    private TextView countdownTimer;
//    private Handler handler = new Handler();
//
//
//    private WebSocketClient webSocketClient;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_level);
//
//        player = findViewById(R.id.player);
//        countdownTimer = findViewById(R.id.countdownTimer);
//
//        player.setOnClickListener(v -> {
//            if (!isJumping) {
//                jumpPlayer();
//            }
//        });
//
//        // Start the countdown and then connect to the WebSocket
//        startCountdown();
//    }
//
//    private void jumpPlayer() {
//        isJumping = true;
//        player.animate()
//                .translationYBy(-200) // Jump up
//                .setDuration(300)
//                .withEndAction(() -> {
//                    player.animate()
//                            .translationYBy(200) // Come down
//                            .setDuration(300)
//                            .withEndAction(() -> isJumping = false);
//                });
//    }
//
//    private void startCountdown() {
//        new Thread(() -> {
//            try {
//                URL url = new URL("http://coms-3090-031.class.las.iastate.edu:8080/countdown/start");
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                connection.setDoOutput(true);
//
//                String postData = "durationInSeconds=100";
//                OutputStream os = connection.getOutputStream();
//                os.write(postData.getBytes());
//                os.flush();
//                os.close();
//
//                int responseCode = connection.getResponseCode();
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    Log.d("LevelActivity", "Countdown started successfully");
//                    runOnUiThread(this::startWebSocketConnection);
//                } else {
//                    Log.e("LevelActivity", "Failed to start countdown: " + responseCode);
//                }
//
//                connection.disconnect();
//            } catch (Exception e) {
//                Log.e("LevelActivity", "Error starting countdown", e);
//            }
//        }).start();
//    }
//
//    private void startWebSocketConnection() {
//        try {
//            URI uri = new URI("ws://coms-3090-031.class.las.iastate.edu:8080/countdown");
//            webSocketClient = new WebSocketClient(uri) {
//                @Override
//                public void onOpen(ServerHandshake handshakedata) {
//                    Log.d("LevelActivity", "WebSocket Connection Opened");
//                }
//
//                @Override
//                public void onMessage(String message) {
//                    runOnUiThread(() -> countdownTimer.setText(message));
//                }
//
//
//                @Override
//                public void onClose(int code, String reason, boolean remote) {
//                    String closedBy = remote ? "server" : "local";
//                    runOnUiThread(() -> {
//                        countdownTimer.setText("---\nconnection closed by " + closedBy + "\nreason: " + reason);
//                        if (code == 1000 || "Countdown complete".equals(reason)) {
//                            // Open the Game Over screen when the WebSocket closes due to countdown completion
//                            Intent intent = new Intent(LevelActivity.this, GameOverActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    });
//                    Log.d("LevelActivity", "WebSocket Connection Closed: " + reason);
//                }
//
//                @Override
//                public void onError(Exception ex) {
//                    Log.e("LevelActivity", "WebSocket Error", ex);
//                }
//            };
//            webSocketClient.connect();
//        } catch (URISyntaxException e) {
//            Log.e("LevelActivity", "WebSocket URI Error", e);
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (webSocketClient != null) {
//            webSocketClient.close();
//        }
//    }
//}

package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class LevelActivity extends AppCompatActivity {
    private ImageView player;
    private boolean isJumping = false;
    private TextView countdownTimer;
    private float dX, dY; // Delta values for drag calculations
    private float touchStartX, touchStartY; // Starting touch coordinates
    private final float dragThreshold = 10; // Minimum movement to detect as a drag

    private WebSocketClient webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        player = findViewById(R.id.player);
        countdownTimer = findViewById(R.id.countdownTimer);

        // Set touch listener for drag and jump functionality
        player.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Capture the initial touch position
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        touchStartX = event.getRawX();
                        touchStartY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        // Detect if the movement exceeds the drag threshold
                        float deltaX = Math.abs(event.getRawX() - touchStartX);
                        float deltaY = Math.abs(event.getRawY() - touchStartY);

                        if (deltaX > dragThreshold || deltaY > dragThreshold) {
                            // Update the ImageView position as the user drags
                            view.animate()
                                    .x(event.getRawX() + dX)
                                    .y(event.getRawY() + dY)
                                    .setDuration(0) // Instant update for smooth dragging
                                    .start();
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        // If the movement was minimal, treat it as a jump
                        float totalMoveX = Math.abs(event.getRawX() - touchStartX);
                        float totalMoveY = Math.abs(event.getRawY() - touchStartY);

                        if (totalMoveX < dragThreshold && totalMoveY < dragThreshold && !isJumping) {
                            jumpPlayer();
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });

        // Start the countdown and then connect to the WebSocket
        startCountdown();
    }

    private void jumpPlayer() {
        isJumping = true;
        player.animate()
                .translationYBy(-200) // Jump up
                .setDuration(300)
                .withEndAction(() -> {
                    player.animate()
                            .translationYBy(200) // Come down
                            .setDuration(300)
                            .withEndAction(() -> isJumping = false);
                });
    }

    private void startCountdown() {
        new Thread(() -> {
            try {
                URL url = new URL("http://coms-3090-031.class.las.iastate.edu:8080/countdown/start");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setDoOutput(true);

                String postData = "durationInSeconds=100";
                OutputStream os = connection.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d("LevelActivity", "Countdown started successfully");
                    runOnUiThread(this::startWebSocketConnection);
                } else {
                    Log.e("LevelActivity", "Failed to start countdown: " + responseCode);
                }

                connection.disconnect();
            } catch (Exception e) {
                Log.e("LevelActivity", "Error starting countdown", e);
            }
        }).start();
    }

    private void startWebSocketConnection() {
        try {
            URI uri = new URI("ws://coms-3090-031.class.las.iastate.edu:8080/countdown");
            webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("LevelActivity", "WebSocket Connection Opened");
                }

                @Override
                public void onMessage(String message) {
                    runOnUiThread(() -> countdownTimer.setText(message));
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    String closedBy = remote ? "server" : "local";
                    runOnUiThread(() -> {
                        countdownTimer.setText("---\nconnection closed by " + closedBy + "\nreason: " + reason);
                        if (code == 1000 || "Countdown complete".equals(reason)) {
                            // Open the Game Over screen when the WebSocket closes due to countdown completion
                            Intent intent = new Intent(LevelActivity.this, GameOverActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    Log.d("LevelActivity", "WebSocket Connection Closed: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    Log.e("LevelActivity", "WebSocket Error", ex);
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            Log.e("LevelActivity", "WebSocket URI Error", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }
}
