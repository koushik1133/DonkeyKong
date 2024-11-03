package com.example.androidexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreboardActivity extends AppCompatActivity{
    private TextView player1Lives;
    private TextView player2Lives;
    private TextView player3Lives;

    private int player1LifeCount = 3;
    private int player2LifeCount = 3;
    private int player3LifeCount = 3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1Lives = findViewById(R.id.player1Lives);
        player2Lives = findViewById(R.id.player2Lives);
        player3Lives = findViewById(R.id.player3Lives);
    }

    // Call this method when a player gets hit
    public void playerHit(int player) {
        switch (player) {
            case 1:
                if (player1LifeCount > 0) {
                    player1LifeCount--;
                    updateLivesDisplay(player1Lives, player1LifeCount);
                }
                break;
            case 2:
                if (player2LifeCount > 0) {
                    player2LifeCount--;
                    updateLivesDisplay(player2Lives, player2LifeCount);
                }
                break;
            case 3:
                if (player3LifeCount > 0) {
                    player3LifeCount--;
                    updateLivesDisplay(player3Lives, player3LifeCount);
                }
                break;
        }
    }

    private void updateLivesDisplay(TextView textView, int lifeCount) {
        if (lifeCount == 0) {
            textView.setText(textView.getText().toString().split(":")[0] + ": Out");
        } else {
            textView.setText(textView.getText().toString().split(":")[0] + ": " + lifeCount + " Lives");
        }
    }

    // Simulate player hits (you can replace this with actual game logic)
    public void simulateHit() {
        playerHit(1); // Simulating a hit on Player 1
        playerHit(2); // Simulating a hit on Player 2
        playerHit(3); // Simulating a hit on Player 3
    }
}
