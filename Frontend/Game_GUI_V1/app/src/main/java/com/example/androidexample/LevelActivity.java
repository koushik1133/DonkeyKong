package com.example.androidexample;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class LevelActivity extends AppCompatActivity {
    private ImageView player;
    private boolean isJumping = false;


    private TextView countdownTimer;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        player = findViewById(R.id.player);
        countdownTimer = findViewById(R.id.countdownTimer);

        //Set a jump action/button
        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isJumping) {
                    jumpPlayer();
                }
            }
        });

        //Start the countdown timer from the server
        fetchCountdownTimer();
    }


    private void jumpPlayer() {
        isJumping = true;
        //Generic jump from Google
        player.animate()
                .translationYBy(-200) //Jump up
                .setDuration(300)
                .withEndAction(() -> {
                    player.animate()
                            .translationYBy(200) //Come down
                            .setDuration(300)
                            .withEndAction(() -> isJumping = false);
                });
    }

    private void fetchCountdownTimer() {
        new Thread(() -> {
            try {
                //Replace this with server URL
                String urlString = "ws://coms-3090-031.class.las.iastate.edu:8080/start";
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                String countdown = jsonResponse.getString("countdown");

                runOnUiThread(() -> countdownTimer.setText(countdown));
            } catch (Exception e) {
                Log.e("LevelActivity", "Error fetching countdown timer", e);
            }
        }).start();
    }
}

