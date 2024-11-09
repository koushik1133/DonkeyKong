package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CharacterSelectionActivity extends AppCompatActivity {

    private static final String SERVER_BASE_URL = "http://coms-3090-031.class.las.iastate.edu:8080/Player";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);

        // Set up click listeners for each character
        findViewById(R.id.character1).setOnClickListener(view -> selectCharacter("player_bald"));
        findViewById(R.id.character2).setOnClickListener(view -> selectCharacter("player_black"));
        findViewById(R.id.character3).setOnClickListener(view -> selectCharacter("player_orange"));
        findViewById(R.id.character4).setOnClickListener(view -> selectCharacter("player_green"));
        findViewById(R.id.character5).setOnClickListener(view -> selectCharacter("player_red"));
        findViewById(R.id.character6).setOnClickListener(view -> selectCharacter("kong_1"));
    }

    private void selectCharacter(String spriteName) {
        updatePlayerSprite(spriteName);

        // Navigate to the next screen with the selected character
        Intent intent = new Intent(this, DisplayCharacterActivity.class);
        intent.putExtra("selectedCharacter", spriteName);
        startActivity(intent);
    }

    private void updatePlayerSprite(String spriteName) {
        new Thread(() -> {
            try {
                //This is will be dynamic based on log in the future
                Long userId = 361L;
                // Construct the URL for the PUT request with the ID in the path
                URL url = new URL(SERVER_BASE_URL + "/" + userId);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json"); // Add this line
                connection.setDoOutput(true);

                // Create the JSON payload
                JSONObject jsonRequestBody = new JSONObject();
                jsonRequestBody.put("id", userId);
                jsonRequestBody.put("sprite", spriteName);

                // Log the JSON payload
                Log.d("CharacterSelection", "Sending JSON: " + jsonRequestBody.toString());

                // Send the JSON payload to the server
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonRequestBody.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                Log.d("CharacterSelection", "Response Code: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                    Log.d("CharacterSelection", "Sprite updated successfully on the server.");
                    runOnUiThread(() -> Toast.makeText(CharacterSelectionActivity.this, "Sprite updated successfully", Toast.LENGTH_SHORT).show());
                } else {
                    Log.e("CharacterSelection", "Failed to update sprite: " + responseCode);
                    runOnUiThread(() -> Toast.makeText(CharacterSelectionActivity.this, "Failed to update sprite. Code: " + responseCode, Toast.LENGTH_SHORT).show());
                }

                connection.disconnect();
            } catch (Exception e) {
                Log.e("CharacterSelection", "Error updating sprite", e);
                runOnUiThread(() -> Toast.makeText(CharacterSelectionActivity.this, "Error updating sprite", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}



//package com.example.androidexample;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.json.JSONObject;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//
//public class CharacterSelectionActivity extends AppCompatActivity {
//
//    private static final String SERVER_BASE_URL = "http://coms-3090-031.class.las.iastate.edu:8080/Player";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_character_selection);
//
//        // Set up click listeners for each character
//        findViewById(R.id.character1).setOnClickListener(view -> selectCharacter(340L, "player_bald"));
//        findViewById(R.id.character2).setOnClickListener(view -> selectCharacter(341L, "player_black"));
//        findViewById(R.id.character3).setOnClickListener(view -> selectCharacter(342L, "player_orange"));
//        findViewById(R.id.character4).setOnClickListener(view -> selectCharacter(343L, "player_green"));
//        findViewById(R.id.character5).setOnClickListener(view -> selectCharacter(344L, "player_red"));
//        findViewById(R.id.character6).setOnClickListener(view -> selectCharacter(345L, "kong_1"));
//    }
//
//    private void selectCharacter(Long userId, String spriteName) {
//        updatePlayerSprite(userId, spriteName);
//
//        // Navigate to the next screen with the selected character
//        Intent intent = new Intent(this, DisplayCharacterActivity.class);
//        intent.putExtra("selectedCharacter", spriteName);
//        startActivity(intent);
//    }
//
//    private void updatePlayerSprite(Long userId, String spriteName) {
//        new Thread(() -> {
//            try {
//                // Construct the URL for the PUT request with the ID in the path
//                URL url = new URL(SERVER_BASE_URL + "/" + userId);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("PUT");
//                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setDoOutput(true);
//
//                // Create the JSON payload
//                JSONObject jsonRequestBody = new JSONObject();
//                jsonRequestBody.put("id", userId);
//                jsonRequestBody.put("sprite", spriteName);
//
//                // Log the JSON payload
//                Log.d("CharacterSelection", "Sending JSON: " + jsonRequestBody.toString());
//
//                // Send the JSON payload to the server
//                try (OutputStream os = connection.getOutputStream()) {
//                    byte[] input = jsonRequestBody.toString().getBytes(StandardCharsets.UTF_8);
//                    os.write(input, 0, input.length);
//                }
//
//                int responseCode = connection.getResponseCode();
//                Log.d("CharacterSelection", "Response Code: " + responseCode);
//                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
//                    Log.d("CharacterSelection", "Sprite updated successfully on the server.");
//                    runOnUiThread(() -> Toast.makeText(CharacterSelectionActivity.this, "Sprite updated successfully", Toast.LENGTH_SHORT).show());
//                } else {
//                    Log.e("CharacterSelection", "Failed to update sprite: " + responseCode);
//                    runOnUiThread(() -> Toast.makeText(CharacterSelectionActivity.this, "Failed to update sprite. Code: " + responseCode, Toast.LENGTH_SHORT).show());
//                }
//
//                connection.disconnect();
//            } catch (Exception e) {
//                Log.e("CharacterSelection", "Error updating sprite", e);
//                runOnUiThread(() -> Toast.makeText(CharacterSelectionActivity.this, "Error updating sprite", Toast.LENGTH_SHORT).show());
//            }
//        }).start();
//    }
//}





//package com.example.androidexample;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.util.Log;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//
//import org.json.JSONObject;
//import java.io.BufferedReader;
//import android.os.StrictMode;
//import android.widget.Toast;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONArray;
//
//public class CharacterSelectionActivity extends AppCompatActivity {
//
//    private static final String SERVER_URL = "http://coms-3090-031.class.las.iastate.edu:8080/player";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_character_selection);
//
//        // Set up click listeners for each character
//        findViewById(R.id.character1).setOnClickListener(view -> selectCharacter("player_bald"));
//        findViewById(R.id.character2).setOnClickListener(view -> selectCharacter("player_black"));
//        findViewById(R.id.character3).setOnClickListener(view -> selectCharacter("player_orange"));
//        findViewById(R.id.character4).setOnClickListener(view -> selectCharacter("player_green"));
//        findViewById(R.id.character5).setOnClickListener(view -> selectCharacter("player_red"));
//        findViewById(R.id.character6).setOnClickListener(view -> selectCharacter("kong_1"));
//    }
//
//    private void selectCharacter(String spriteName) {
//        // Send the sprite name to the server
//        updatePlayerSprite(spriteName);
//
//        // Navigate to the next screen with the selected character
//        Intent intent = new Intent(this, DisplayCharacterActivity.class);
//        intent.putExtra("selectedCharacter", spriteName);
//        startActivity(intent);
//    }
//
////    private void updatePlayerSprite(String spriteName) {
////    new Thread(() -> {
////        try {
////            URL url = new URL(SERVER_URL);
////            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
////            connection.setRequestMethod("PUT");
////            connection.setRequestProperty("Content-Type", "application/json");
////            connection.setDoOutput(true);
////
////            // Replace with actual user ID or username as needed
////            Long userId = 361L; // Replace with dynamic user ID
////
////            // JSON payload
////            String jsonInputString = String.format("{\"id\": %d, \"sprite\": \"%s\"}", userId, spriteName);
////
////            try (OutputStream os = connection.getOutputStream()) {
////                byte[] input = jsonInputString.getBytes("utf-8");
////                os.write(input, 0, input.length);
////            }
////
////            int responseCode = connection.getResponseCode();
////            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
////                Log.d("CharacterSelection", "Sprite updated successfully on the server.");
////            } else {
////                Log.e("CharacterSelection", "Failed to update sprite: " + responseCode);
////            }
////
////            connection.disconnect();
////        } catch (Exception e) {
////            Log.e("CharacterSelection", "Error updating sprite", e);
////        }
////    }).start();
////}
//
//    private void updatePlayerSprite(String spriteName) {
//        new Thread(() -> {
//            try {
//                // Construct the URL for the PUT request
//                URL url = new URL(SERVER_URL);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("PUT");
//                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setDoOutput(true);
//
//                Long userId = 361L; // Replace with dynamic user ID if necessary
//
//                // Create the JSON payload
//                JSONObject jsonRequestBody = new JSONObject();
//                jsonRequestBody.put("id", userId);
//                jsonRequestBody.put("sprite", spriteName);
//
//                // Log the JSON payload
//                Log.d("CharacterSelection", "Sending JSON: " + jsonRequestBody.toString());
//
//                // Send the JSON payload to the server
//                try (OutputStream os = connection.getOutputStream()) {
//                    byte[] input = jsonRequestBody.toString().getBytes(StandardCharsets.UTF_8);
//                    os.write(input, 0, input.length);
//                }
//
//                int responseCode = connection.getResponseCode();
//                Log.d("CharacterSelection", "Response Code: " + responseCode);
//                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
//                    Log.d("CharacterSelection", "Sprite updated successfully on the server.");
//                } else {
//                    Log.e("CharacterSelection", "Failed to update sprite: " + responseCode);
//                    try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
//                        StringBuilder response = new StringBuilder();
//                        String responseLine;
//                        while ((responseLine = br.readLine()) != null) {
//                            response.append(responseLine.trim());
//                        }
//                        Log.e("CharacterSelection", "Error response: " + response.toString());
//                    }
//                }
//
//                connection.disconnect();
//            } catch (Exception e) {
//                Log.e("CharacterSelection", "Error updating sprite", e);
//            }
//        }).start();
//    }
//
//}


////////////////////////////////////
