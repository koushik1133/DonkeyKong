package com.example.androidexample;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreboardActivity extends AppCompatActivity{
    private TextView timerText, player1Score, player2Score, player3Score;
    private int player1Points = 0, player2Points = 0, player3Points = 0;
    private boolean player1Out = false, player2Out = false, player3Out = false;
    private int timeLeft = 120; // 120 seconds timer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize UI elements
        timerText = findViewById(R.id.timerText);
        player1Score = findViewById(R.id.player1Score);
        player2Score = findViewById(R.id.player2Score);
        player3Score = findViewById(R.id.player3Score);

        // Start the countdown timer
        startGameTimer();
    }

    private void startGameTimer() {
        new CountDownTimer(timeLeft * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft--;
                timerText.setText("Time: " + timeLeft);

                // Increment the score for each player who is not out
                if (!player1Out) player1Points++;
                if (!player2Out) player2Points++;
                if (!player3Out) player3Points++;

                updateScores();
            }

            @Override
            public void onFinish() {
                // When the timer finishes, show the winner
                showWinner();
            }
        }.start();
    }

    private void updateScores() {
        // Update the score text views
        player1Score.setText("Player 1: " + player1Points);
        player2Score.setText("Player 2: " + player2Points);
        player3Score.setText("Player 3: " + player3Points);
    }

    private void showWinner() {
        // Determine the winner by comparing scores
        int maxScore = Math.max(player1Points, Math.max(player2Points, player3Points));
        String winner = "It's a tie!";

        if (maxScore == player1Points) winner = "Player 1 wins!";
        else if (maxScore == player2Points) winner = "Player 2 wins!";
        else if (maxScore == player3Points) winner = "Player 3 wins!";

        Toast.makeText(ScoreboardActivity.this, winner, Toast.LENGTH_LONG).show();
    }

    // Method to handle when a player is hit (out)
    public void onPlayerHit(int playerNumber) {
        switch (playerNumber) {
            case 1:
                player1Out = true;
                break;
            case 2:
                player2Out = true;
                break;
            case 3:
                player3Out = true;
                break;
        }
    }
}
