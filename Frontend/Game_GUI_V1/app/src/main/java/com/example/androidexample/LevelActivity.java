//V4 multi player
package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;

public class LevelActivity extends AppCompatActivity {
    private List<ImageView> activePlayers; // List of active player ImageViews
    private ImageView player1, player2, player3, player4;
    private ImageView groundBlock, platformBlock1, platformBlock2, platformBlock3, platformBlock4, platformBlock5;
    private TextView countdownTimer, positionDebugger; // Debugger for real-time positions

    private float dX, dY; // Values for drag calculations
    private WebSocketClient countdownWebSocketClient;
    private WebSocketClient positionWebSocketClient;

    private static final String POSITION_SERVER_URL = "ws://coms-3090-031.class.las.iastate.edu:8080/position";
    private static final String COUNTDOWN_SERVER_URL = "ws://coms-3090-031.class.las.iastate.edu:8080/countdown";

    private static final int DEFAULT_PLAYER_COUNT = 2; // Default to 2 players for initial testing
    private static final int COUNTDOWN_DURATION = 15; // Countdown duration in seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        // Initialize player views
        player1 = findViewById(R.id.player1); // Local player
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);

        countdownTimer = findViewById(R.id.countdownTimer);
        positionDebugger = findViewById(R.id.positionDebugger);

        // Initialize platform and ground blocks
        groundBlock = findViewById(R.id.groundBlock);
        platformBlock1 = findViewById(R.id.platformBlock1);
        platformBlock2 = findViewById(R.id.platformBlock2);
        platformBlock3 = findViewById(R.id.platformBlock3);
        platformBlock4 = findViewById(R.id.platformBlock4);
        platformBlock5 = findViewById(R.id.platformBlock5);

        // Initialize active players list based on desired player count
        activePlayers = new ArrayList<>();
        setupActivePlayers(DEFAULT_PLAYER_COUNT);

        // Set up drag movement for the local player
        setupPlayerMovement();

        // Start the countdown timer
        startCountdown();

        // Connect to the position reporting WebSocket
        connectPositionWebSocket();
    }

    /**
     * Initializes the active players based on the specified count.
     */
    private void setupActivePlayers(int count) {
        activePlayers.clear();
        if (count >= 1) activePlayers.add(player1);
        if (count >= 2) activePlayers.add(player2);
        if (count >= 3) activePlayers.add(player3);
        if (count >= 4) activePlayers.add(player4);

        // Hide players beyond the count
        player1.setVisibility(count >= 1 ? View.VISIBLE : View.GONE);
        player2.setVisibility(count >= 2 ? View.VISIBLE : View.GONE);
        player3.setVisibility(count >= 3 ? View.VISIBLE : View.GONE);
        player4.setVisibility(count >= 4 ? View.VISIBLE : View.GONE);
    }

    /**
     * Sets up touch movement for the local player (player1).
     */
    private void setupPlayerMovement() {
        player1.setOnTouchListener((view, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - event.getRawX();
                    dY = view.getY() - event.getRawY();
                    return true;

                case MotionEvent.ACTION_MOVE:
                    float newX = event.getRawX() + dX;
                    float newY = event.getRawY() + dY;

                    if (isInsideBounds(newX, newY) && !isCollidingWithObstacles(newX, newY)) {
                        player1.setX(newX);
                        player1.setY(newY);

                        // Send position to backend and update debugger
                        sendPlayerPosition("player1", newX, newY);
                        updatePositionDebugger("player1", newX, newY);
                    }
                    return true;

                default:
                    return false;
            }
        });
    }

    /**
     * Starts the countdown timer using a POST request and a WebSocket connection.
     */
    private void startCountdown() {
        new Thread(() -> {
            try {
                URL url = new URL("http://coms-3090-031.class.las.iastate.edu:8080/countdown/start");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setDoOutput(true);

                String postData = "durationInSeconds=" + COUNTDOWN_DURATION;
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(postData.getBytes());
                    os.flush();
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d("LevelActivity", "Countdown started successfully");
                    runOnUiThread(this::connectCountdownWebSocket);
                } else {
                    Log.e("LevelActivity", "Failed to start countdown: " + responseCode);
                }
                connection.disconnect();
            } catch (Exception e) {
                Log.e("LevelActivity", "Error starting countdown", e);
            }
        }).start();
    }

    /**
     * Connects to the countdown WebSocket for real-time updates.
     */
    private void connectCountdownWebSocket() {
        try {
            URI uri = new URI(COUNTDOWN_SERVER_URL);
            countdownWebSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("LevelActivity", "Countdown WebSocket Connected");
                }

                @Override
                public void onMessage(String message) {
                    runOnUiThread(() -> {
                        countdownTimer.setText(message);
                        if ("0".equals(message)) {
                            // Navigate to the Game Over screen
                            Intent intent = new Intent(LevelActivity.this, GameOverActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("LevelActivity", "Countdown WebSocket Closed: " + reason);
                    String closedBy = remote ? "server" : "local";

                    runOnUiThread(() -> {
                        countdownTimer.setText("---\nconnection closed by " + closedBy + "\nreason: " + reason);

                        // Handle the WebSocket closure logic for game over
                        if (code == 1000 || "Countdown complete".equals(reason) || "0".equals(reason)) {
                            Log.d("LevelActivity", "Countdown complete. Transitioning to Game Over screen.");
                            Intent intent = new Intent(LevelActivity.this, GameOverActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }

                @Override
                public void onError(Exception ex) {
                    Log.e("LevelActivity", "Countdown WebSocket Error", ex);
                }
            };
            countdownWebSocketClient.connect();
        } catch (URISyntaxException e) {
            Log.e("LevelActivity", "Countdown WebSocket URI Error", e);
        }
    }

    /**
     * Establishes WebSocket connection to receive and update player positions.
     */
    private void connectPositionWebSocket() {
        try {
            URI uri = new URI(POSITION_SERVER_URL);
            positionWebSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("LevelActivity", "Position WebSocket Connected");
                }

                @Override
                public void onMessage(String message) {
                    runOnUiThread(() -> {
                        try {
                            // Parse the incoming JSON message
                            String[] parts = message.replace("{", "").replace("}", "").split(",");
                            String playerId = parts[0].split(":")[1].replace("\"", "").trim();
                            float x = Float.parseFloat(parts[1].split(":")[1].trim());
                            float y = Float.parseFloat(parts[2].split(":")[1].trim());

                            for (ImageView player : activePlayers) {
                                if (player.getTag().toString().equals(playerId)) {
                                    player.setX(x - player.getWidth() / 2);
                                    player.setY(y - player.getHeight() / 2);
                                    updatePositionDebugger(playerId, x, y);
                                }
                            }
                        } catch (Exception e) {
                            Log.e("LevelActivity", "Error parsing position update: " + message, e);
                        }
                    });
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("LevelActivity", "Position WebSocket Closed: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    Log.e("LevelActivity", "Position WebSocket Error", ex);
                }
            };
            positionWebSocketClient.connect();
        } catch (URISyntaxException e) {
            Log.e("LevelActivity", "Position WebSocket URI Error", e);
        }
    }

    /**
     * Sends the specified player's position to the backend WebSocket.
     */
    private void sendPlayerPosition(String playerId, float x, float y) {
        if (positionWebSocketClient != null && positionWebSocketClient.isOpen()) {
            String message = String.format("{\"playerId\":\"%s\",\"x\":%.2f,\"y\":%.2f}", playerId, x, y);
            positionWebSocketClient.send(message);
            Log.d("PlayerPosition", "Sent: " + message);
        } else {
            Log.e("PlayerPosition", "WebSocket is not open. Position not sent.");
        }
    }

    /**
     * Updates the position debugger with the specified player's current position.
     */
    private void updatePositionDebugger(String playerId, float x, float y) {
        String position = String.format("%s Position: x=%.2f, y=%.2f", playerId, x, y);
        positionDebugger.setText(position);
    }

    /**
     * Checks if the player's new position is inside the screen bounds.
     */
    private boolean isInsideBounds(float newX, float newY) {
        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        float screenHeight = getResources().getDisplayMetrics().heightPixels;
        float playerWidth = player1.getWidth();
        float playerHeight = player1.getHeight();

        return newX >= 0 && newX + playerWidth <= screenWidth &&
                newY >= 0 && newY + playerHeight <= screenHeight;
    }

    /**
     * Checks for collisions between the player and any obstacle.
     */
    private boolean isCollidingWithObstacles(float newX, float newY) {
        return isColliding(player1, groundBlock, newX, newY) ||
                isColliding(player1, platformBlock1, newX, newY) ||
                isColliding(player1, platformBlock2, newX, newY) ||
                isColliding(player1, platformBlock3, newX, newY) ||
                isColliding(player1, platformBlock4, newX, newY) ||
                isColliding(player1, platformBlock5, newX, newY);
    }

    private boolean isColliding(ImageView player, ImageView obstacle, float newX, float newY) {
        float playerWidth = player.getWidth();
        float playerHeight = player.getHeight();

        float padding = 10; // Adjust for proximity
        float obstacleX = obstacle.getX() - padding;
        float obstacleY = obstacle.getY() - padding;
        float obstacleWidth = obstacle.getWidth() + 2 * padding;
        float obstacleHeight = obstacle.getHeight() + 2 * padding;

        return newX < obstacleX + obstacleWidth &&
                newX + playerWidth > obstacleX &&
                newY < obstacleY + obstacleHeight &&
                newY + playerHeight > obstacleY;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countdownWebSocketClient != null) countdownWebSocketClient.close();
        if (positionWebSocketClient != null) positionWebSocketClient.close();
    }
}






//V3.6
//Instead of jump we avoid platforms and just use our finger to move also shows current player (x,y)
//package com.example.androidexample;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.handshake.ServerHandshake;
//
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//
//public class LevelActivity extends AppCompatActivity {
//    private ImageView player;
//    private ImageView groundBlock, platformBlock1, platformBlock2, platformBlock3, platformBlock4, platformBlock5;
//    private boolean isJumping = false;
//    private TextView countdownTimer, positionDebugger; // Added position debugger for real-time tracking
//    private float dX, dY; // Values for drag calculations
//    private float touchStartX, touchStartY; // Starting touch coordinates
//
//    private WebSocketClient countdownWebSocketClient;
//    private WebSocketClient positionWebSocketClient;
//
//    private static final String POSITION_SERVER_URL = "ws://coms-3090-031.class.las.iastate.edu:8080/position";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_level);
//
//        // Initialize UI elements
//        player = findViewById(R.id.player1);
//        countdownTimer = findViewById(R.id.countdownTimer);
//        positionDebugger = findViewById(R.id.positionDebugger); // Debugging view for real-time position
//
//        // Reference the ground and platform blocks
//        groundBlock = findViewById(R.id.groundBlock);
//        platformBlock1 = findViewById(R.id.platformBlock1);
//        platformBlock2 = findViewById(R.id.platformBlock2);
//        platformBlock3 = findViewById(R.id.platformBlock3);
//        platformBlock4 = findViewById(R.id.platformBlock4);
//        platformBlock5 = findViewById(R.id.platformBlock5);
//
//        // Set up drag movement for the player
//        player.setOnTouchListener((view, event) -> {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    // Calculate the offset between the player position and touch point
//                    dX = view.getX() - event.getRawX();
//                    dY = view.getY() - event.getRawY();
//                    touchStartX = event.getRawX();
//                    touchStartY = event.getRawY();
//                    return true;
//
//                case MotionEvent.ACTION_MOVE:
//                    // Calculate the new position based on touch movement
//                    float newX = event.getRawX() + dX;
//                    float newY = event.getRawY() + dY;
//
//                    // Check collisions and boundaries
//                    if (isInsideBounds(newX, newY) && !isCollidingWithObstacles(newX, newY)) {
//                        player.setX(newX);
//                        player.setY(newY);
//
//                        // Send updated position to the backend and update debugger
//                        sendPlayerPosition();
//                        updatePositionDebugger(newX, newY);
//                    }
//                    return true;
//
//                default:
//                    return false;
//            }
//        });
//
//        // Start the countdown timer and connect to WebSocket
//        startCountdown();
//
//        // Connect to the position reporting WebSocket
//        connectPositionWebSocket();
//    }
//
//    /**
//     * Starts the countdown timer using a POST request to the backend.
//     */
//    private void startCountdown() {
//        new Thread(() -> {
//            try {
//                URL url = new URL("http://coms-3090-031.class.las.iastate.edu:8080/countdown/start");
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                connection.setDoOutput(true);
//
//                // Specify countdown duration (in seconds)
//                String postData = "durationInSeconds=10";
//                try (OutputStream os = connection.getOutputStream()) {
//                    os.write(postData.getBytes());
//                    os.flush();
//                }
//
//                int responseCode = connection.getResponseCode();
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    Log.d("LevelActivity", "Countdown started successfully");
//                    runOnUiThread(this::startCountdownWebSocketConnection);
//                } else {
//                    Log.e("LevelActivity", "Failed to start countdown: " + responseCode);
//                }
//                connection.disconnect();
//            } catch (Exception e) {
//                Log.e("LevelActivity", "Error starting countdown", e);
//            }
//        }).start();
//    }
//
//    /**
//     * Establishes WebSocket connection for countdown updates.
//     */
//    private void startCountdownWebSocketConnection() {
//        try {
//            URI uri = new URI("ws://coms-3090-031.class.las.iastate.edu:8080/countdown");
//            countdownWebSocketClient = new WebSocketClient(uri) {
//                @Override
//                public void onOpen(ServerHandshake handshakedata) {
//                    Log.d("LevelActivity", "Countdown WebSocket Connected");
//                }
//
//                @Override
//                public void onMessage(String message) {
//                    runOnUiThread(() -> {
//                        countdownTimer.setText(message);
//                        if ("0".equals(message)) {
//                            // Navigate to the Game Over screen
//                            Intent intent = new Intent(LevelActivity.this, GameOverActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    });
//                }
//
//                @Override
//                public void onClose(int code, String reason, boolean remote) {
//                    Log.d("LevelActivity", "Countdown WebSocket Closed: " + reason);
//                    String closedBy = remote ? "server" : "local";
//
//                    runOnUiThread(() -> {
//                        countdownTimer.setText("---\nconnection closed by " + closedBy + "\nreason: " + reason);
//
//                        // Handle the WebSocket closure logic for game over
//                        if (code == 1000 || "Countdown complete".equals(reason) || "0".equals(reason)) {
//                            Log.d("LevelActivity", "Countdown complete. Transitioning to Game Over screen.");
//                            Intent intent = new Intent(LevelActivity.this, GameOverActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    });
//                }
//
//                @Override
//                public void onError(Exception ex) {
//                    Log.e("LevelActivity", "Countdown WebSocket Error", ex);
//                }
//            };
//            countdownWebSocketClient.connect();
//        } catch (URISyntaxException e) {
//            Log.e("LevelActivity", "Countdown WebSocket URI Error", e);
//        }
//    }
//
//    /**
//     * Establishes WebSocket connection to send player position updates.
//     */
//    private void connectPositionWebSocket() {
//        try {
//            URI uri = new URI(POSITION_SERVER_URL);
//            positionWebSocketClient = new WebSocketClient(uri) {
//                @Override
//                public void onOpen(ServerHandshake handshakedata) {
//                    Log.d("LevelActivity", "Position WebSocket Connected");
//                }
//
//                @Override
//                public void onMessage(String message) {
//                    Log.d("LevelActivity", "Position WebSocket Message: " + message);
//                }
//
//                @Override
//                public void onClose(int code, String reason, boolean remote) {
//                    Log.d("LevelActivity", "Position WebSocket Closed: " + reason);
//                }
//
//                @Override
//                public void onError(Exception ex) {
//                    Log.e("LevelActivity", "Position WebSocket Error", ex);
//                }
//            };
//            positionWebSocketClient.connect();
//        } catch (URISyntaxException e) {
//            Log.e("LevelActivity", "Position WebSocket URI Error", e);
//        }
//    }
//
//    /**
//     * Sends the player's current position to the backend WebSocket.
//     */
//    private void sendPlayerPosition() {
//        if (positionWebSocketClient != null && positionWebSocketClient.isOpen() && player != null) {
//            float x = player.getX() + player.getWidth() / 2;
//            float y = player.getY() + player.getHeight() / 2;
//
//            if (!Float.isNaN(x) && !Float.isNaN(y)) {
//                positionWebSocketClient.send(String.format("{\"x\":%.2f,\"y\":%.2f}", x, y));
//                Log.d("PlayerPosition", "Sent: x=" + x + ", y=" + y);
//            } else {
//                Log.e("PlayerPosition", "Position values are NaN. Not sent.");
//            }
//        } else {
//            Log.e("PlayerPosition", "WebSocket or player is null. Not sent.");
//        }
//    }
//
//    /**
//     * Updates the position debugger with the current (x, y) coordinates.
//     */
//    private void updatePositionDebugger(float x, float y) {
//        String position = String.format("Player Position: x=%.2f, y=%.2f", x, y);
//        positionDebugger.setText(position);
//    }
//
//    /**
//     * Checks if the player's new position is inside the screen bounds.
//     */
//    private boolean isInsideBounds(float newX, float newY) {
//        float screenWidth = getResources().getDisplayMetrics().widthPixels;
//        float screenHeight = getResources().getDisplayMetrics().heightPixels;
//        float playerWidth = player.getWidth();
//        float playerHeight = player.getHeight();
//
//        return newX >= 0 && newX + playerWidth <= screenWidth &&
//                newY >= 0 && newY + playerHeight <= screenHeight;
//    }
//
//    /**
//     * Checks for collisions between the player and any obstacle.
//     */
//    private boolean isCollidingWithObstacles(float newX, float newY) {
//        return isColliding(player, groundBlock, newX, newY) ||
//                isColliding(player, platformBlock1, newX, newY) ||
//                isColliding(player, platformBlock2, newX, newY) ||
//                isColliding(player, platformBlock3, newX, newY) ||
//                isColliding(player, platformBlock4, newX, newY) ||
//                isColliding(player, platformBlock5, newX, newY);
//    }
//
//    /**
//     * Checks if the player is colliding with a specific obstacle.
//     */
//    private boolean isColliding(ImageView player, ImageView obstacle, float newX, float newY) {
//        float playerWidth = player.getWidth();
//        float playerHeight = player.getHeight();
//
//        // Adjust padding for collision proximity
//        float padding = 10;
//
//        float obstacleX = obstacle.getX() - padding;
//        float obstacleY = obstacle.getY() - padding;
//        float obstacleWidth = obstacle.getWidth() + 2 * padding;
//        float obstacleHeight = obstacle.getHeight() + 2 * padding;
//
//        return newX < obstacleX + obstacleWidth &&
//                newX + playerWidth > obstacleX &&
//                newY < obstacleY + obstacleHeight &&
//                newY + playerHeight > obstacleY;
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (countdownWebSocketClient != null) {
//            countdownWebSocketClient.close();
//        }
//        if (positionWebSocketClient != null) {
//            positionWebSocketClient.close();
//        }
//    }
//}




//New V3 there is a jump capability (unpredictable)
//package com.example.androidexample;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MotionEvent; // This is for the drag motion
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.handshake.ServerHandshake;
//
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//
//public class LevelActivity extends AppCompatActivity {
//    private ImageView player;
//    private boolean isJumping = false;
//    private TextView countdownTimer;
//    private float dX, dY; // Values for drag calculations
//    private float touchStartX, touchStartY; // Starting touch coordinates
//    private final float dragThreshold = 10; // Min movement to detect drag (10 seems right?)
//
//    private WebSocketClient countdownWebSocketClient;
//    private WebSocketClient positionWebSocketClient;
//
//    private static final String POSITION_SERVER_URL = "ws://coms-3090-031.class.las.iastate.edu:8080/position";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_level);
//
//        player = findViewById(R.id.player);
//        countdownTimer = findViewById(R.id.countdownTimer);
//
//        // Touch listener for drag/jump
//        player.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:   // Put finger down
//                        // Get initial touch position
//                        dX = view.getX() - event.getRawX();
//                        dY = view.getY() - event.getRawY();
//                        touchStartX = event.getRawX();
//                        touchStartY = event.getRawY();
//                        return true;
//
////                    case MotionEvent.ACTION_MOVE:   // Move finger around
////                        // Detect if the movement exceeds the drag threshold
////                        float deltaX = Math.abs(event.getRawX() - touchStartX);
////                        float deltaY = Math.abs(event.getRawY() - touchStartY);
////
////                        if (deltaX > dragThreshold || deltaY > dragThreshold) {
////                            // Update the ImageView position as the user drags
////                            view.animate()
////                                    .x(event.getRawX() + dX)
////                                    .y(event.getRawY() + dY)
////                                    .setDuration(0) // Instant update for smooth dragging
////                                    .start();
////                            sendPlayerPosition(); // Send position updates
////                        }
////                        return true;
//
//                    case MotionEvent.ACTION_MOVE:
//                        // Move the sprite
//                        player.setX(event.getRawX() + dX);
//                        player.setY(event.getRawY() + dY);
//
//                        // Send the updated position
//                        sendPlayerPosition();
//                        return true;
//
//
////                    case MotionEvent.ACTION_UP: // When touch is not enough for move, jump
////                        float totalMoveX = Math.abs(event.getRawX() - touchStartX);
////                        float totalMoveY = Math.abs(event.getRawY() - touchStartY);
////
////                        if (totalMoveX < dragThreshold && totalMoveY < dragThreshold && !isJumping) {
////                            jumpPlayer();
////                        }
////                        return true;
//
//                    case MotionEvent.ACTION_UP:
//                        // Calculate horizontal momentum based on the difference in touch positions
//                        float horizontalMomentum = event.getRawX() - touchStartX;
//
//                        // Trigger a jump only if not already jumping
//                        if (!isJumping) {
//                            jumpPlayer(horizontalMomentum); // Pass the calculated momentum
//                        }
//                        return true;
//
//
//                    default:
//                        return false;
//                }
//            }
//        });
//
//        // Start the countdown timer and connect to the WebSocket
//        startCountdown();
//
//        // Connect to the position reporting WebSocket
//        connectPositionWebSocket();
//    }
//
////    private void jumpPlayer() {
////        isJumping = true;
////        player.animate()
////                .translationYBy(-200) // Jump up
////                .setDuration(300)
////                .withEndAction(() -> {
////                    player.animate()
////                            .translationYBy(200) // Come down
////                            .setDuration(300)
////                            .withEndAction(() -> isJumping = false);
////                });
////    }
//
//    private void jumpPlayer(float horizontalMomentum) {
//        isJumping = true;
//        player.animate()
//                .translationXBy(horizontalMomentum)
//                .translationYBy(-200) // Jump upward
//                .setDuration(300)
//                .withEndAction(() -> player.animate()
//                        .translationYBy(200) // Fall back down
//                        .setDuration(300)
//                        .withEndAction(() -> {
//                            isJumping = false;
//
//                            // Send the updated position after the jump
//                            sendPlayerPosition();
//                        }));
//    }
//
//
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
//                String postData = "durationInSeconds=10";  // Replace 10 with any int and that will be countdown value
//                OutputStream os = connection.getOutputStream();
//                os.write(postData.getBytes());
//                os.flush();
//                os.close();
//
//                int responseCode = connection.getResponseCode();
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    Log.d("LevelActivity", "Countdown started successfully");
//                    runOnUiThread(this::startCountdownWebSocketConnection);
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
////    private void startCountdownWebSocketConnection() {
////        try {
////            URI uri = new URI("ws://coms-3090-031.class.las.iastate.edu:8080/countdown");
////            countdownWebSocketClient = new WebSocketClient(uri) {
////                @Override
////                public void onOpen(ServerHandshake handshakedata) {
////                    Log.d("LevelActivity", "Countdown WebSocket Connected");
////                }
////
////                @Override
////                public void onMessage(String message) {
////                    runOnUiThread(() -> countdownTimer.setText(message));
////                }
////
////                @Override
////                public void onClose(int code, String reason, boolean remote) {
////                    Log.d("LevelActivity", "Countdown WebSocket Closed: " + reason);
////                }
////
////                @Override
////                public void onError(Exception ex) {
////                    Log.e("LevelActivity", "Countdown WebSocket Error", ex);
////                }
////            };
////            countdownWebSocketClient.connect();
////        } catch (URISyntaxException e) {
////            Log.e("LevelActivity", "Countdown WebSocket URI Error", e);
////        }
////    }
//
//    private void startCountdownWebSocketConnection() {
//        try {
//            URI uri = new URI("ws://coms-3090-031.class.las.iastate.edu:8080/countdown");
//            countdownWebSocketClient = new WebSocketClient(uri) {
//                @Override
//                public void onOpen(ServerHandshake handshakedata) {
//                    Log.d("LevelActivity", "Countdown WebSocket Connected");
//                }
//
//                @Override
//                public void onMessage(String message) {
//                    runOnUiThread(() -> {
//                        countdownTimer.setText(message);
//                        // Check if the countdown reached 0
//                        if ("0".equals(message)) {
//                            // Transition to the game over screen
//                            Intent intent = new Intent(LevelActivity.this, GameOverActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    });
//                }
//
//                @Override
//                public void onClose(int code, String reason, boolean remote) {
//                    Log.d("LevelActivity", "Countdown WebSocket Closed: " + reason);
//                    String closedBy = remote ? "server" : "local";
//                    runOnUiThread(() -> {
//                        countdownTimer.setText("---\nconnection closed by " + closedBy + "\nreason: " + reason);
//                        if (code == 1000 || "Countdown complete".equals(reason)) {
//                            // Transition to the game over screen
//                            Intent intent = new Intent(LevelActivity.this, GameOverActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    });
//                }
//
//                @Override
//                public void onError(Exception ex) {
//                    Log.e("LevelActivity", "Countdown WebSocket Error", ex);
//                }
//            };
//            countdownWebSocketClient.connect();
//        } catch (URISyntaxException e) {
//            Log.e("LevelActivity", "Countdown WebSocket URI Error", e);
//        }
//    }
//
//
//
//    private void connectPositionWebSocket() {
//        try {
//            URI uri = new URI(POSITION_SERVER_URL);
//            positionWebSocketClient = new WebSocketClient(uri) {
//                @Override
//                public void onOpen(ServerHandshake handshakedata) {
//                    Log.d("LevelActivity", "Position WebSocket Connected");
//                }
//
//                @Override
//                public void onMessage(String message) {
//                    Log.d("LevelActivity", "Position WebSocket Message: " + message);
//                    // Handle position updates for other players if needed
//                }
//
//                @Override
//                public void onClose(int code, String reason, boolean remote) {
//                    Log.d("LevelActivity", "Position WebSocket Closed: " + reason);
//                }
//
//                @Override
//                public void onError(Exception ex) {
//                    Log.e("LevelActivity", "Position WebSocket Error", ex);
//                }
//            };
//            positionWebSocketClient.connect();
//        } catch (URISyntaxException e) {
//            Log.e("LevelActivity", "Position WebSocket URI Error", e);
//        }
//    }
//
//private void sendPlayerPosition() {
//        // Ensures position is not null
//    if (positionWebSocketClient != null && positionWebSocketClient.isOpen()) {
//        // Get the current position of the player's sprite
//        float x = player.getX() + player.getWidth() / 2;
//        float y = player.getY() + player.getHeight() / 2;
//
//        // Send the position data as a JSON string
//        positionWebSocketClient.send(String.format("{\"x\":%.2f,\"y\":%.2f}", x, y));
//    } else {
//        Log.e("LevelActivity", "Position WebSocket is not open.");
//    }
//}
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (countdownWebSocketClient != null) {
//            countdownWebSocketClient.close();
//        }
//        if (positionWebSocketClient != null) {
//            positionWebSocketClient.close();
//        }
//    }
//}













//Orignal V1
//package com.example.androidexample;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.MotionEvent;    // This is for the drag motion
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.handshake.ServerHandshake;
//
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//
//public class LevelActivity extends AppCompatActivity {
//    private ImageView player;
//    private boolean isJumping = false;
//    private TextView countdownTimer;
//    private float dX, dY; // Values for drag calculations
//    private float touchStartX, touchStartY; // Starting touch coordinates
//    private final float dragThreshold = 10; // Min movement to detect drag (10 seems right?)
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
//        // Touch listener for drag/jump
//        player.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:   // Put finger down
//                        // Get initial touch position
//                        dX = view.getX() - event.getRawX();
//                        dY = view.getY() - event.getRawY();
//                        touchStartX = event.getRawX();
//                        touchStartY = event.getRawY();
//                        return true;
//
//                    case MotionEvent.ACTION_MOVE:   // Move finger around
//                        // Detect if the movement exceeds the drag threshold
//                        float deltaX = Math.abs(event.getRawX() - touchStartX);
//                        float deltaY = Math.abs(event.getRawY() - touchStartY);
//
//                        if (deltaX > dragThreshold || deltaY > dragThreshold) {
//                            // Update the ImageView position as the user drags
//                            view.animate()
//                                    .x(event.getRawX() + dX)
//                                    .y(event.getRawY() + dY)
//                                    .setDuration(0) // Instant update for smooth dragging
//                                    .start();
//                        }
//                        return true;
//
//                    case MotionEvent.ACTION_UP: // When touch is not enough for move, jump
//                        float totalMoveX = Math.abs(event.getRawX() - touchStartX);
//                        float totalMoveY = Math.abs(event.getRawY() - touchStartY);
//
//                        if (totalMoveX < dragThreshold && totalMoveY < dragThreshold && !isJumping) {
//                            jumpPlayer();
//                        }
//                        return true;
//
//                    default:
//                        return false;
//                }
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
//                String postData = "durationInSeconds=10";  // Replace 10 with any int and that will be countdown value
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
