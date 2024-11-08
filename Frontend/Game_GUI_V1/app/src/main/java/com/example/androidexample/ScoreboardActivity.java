package com.example.androidexample;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreboardActivity extends AppCompatActivity{
    // Views
    private TextView timerText;
    private TextView player1ScoreText;
    private TextView player2ScoreText;
    private TextView player3ScoreText;
    private Button endGameButton;

    // Scores
    private int player1Score = 0;
    private int player2Score = 0;
    private int player3Score = 0;

    // Game state
    private boolean player1Active = true;
    private boolean player2Active = true;
    private boolean player3Active = true;

    // Timer
    private CountDownTimer gameTimer;
    private long timeRemaining = 120 * 1000; // 120 seconds in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        // Initialize views
        timerText = findViewById(R.id.timerText);
        player1ScoreText = findViewById(R.id.player1Score);
        player2ScoreText = findViewById(R.id.player2Score);
        player3ScoreText = findViewById(R.id.player3Score);
        endGameButton = findViewById(R.id.endGameButton);

        // Set up end game button click listener
        endGameButton.setOnClickListener(v -> {
            // End the game early when clicked
            endGame();
        });

        // Start the game timer
        startGameTimer();
    }

    private void startGameTimer() {
        gameTimer = new CountDownTimer(timeRemaining, 1000) { // 1 second interval
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer text
                timeRemaining = millisUntilFinished;
                int secondsLeft = (int) (millisUntilFinished / 1000);
                timerText.setText("Time Left: " + secondsLeft + "s");

                // Update scores for active players
                if (player1Active) player1Score++;
                if (player2Active) player2Score++;
                if (player3Active) player3Score++;

                // Update score displays
                player1ScoreText.setText("Player 1: " + player1Score);
                player2ScoreText.setText("Player 2: " + player2Score);
                player3ScoreText.setText("Player 3: " + player3Score);
            }

            @Override
            public void onFinish() {
                // Game is over when timer finishes
                endGame();
            }
        }.start();
    }

    private void endGame() {
        // Stop the timer
        if (gameTimer != null) {
            gameTimer.cancel();
        }

        // Show the winner
        String winnerMessage = "Game Over! ";

        if (player1Score > player2Score && player1Score > player3Score) {
            winnerMessage += "Player 1 wins!";
        } else if (player2Score > player1Score && player2Score > player3Score) {
            winnerMessage += "Player 2 wins!";
        } else if (player3Score > player1Score && player3Score > player2Score) {
            winnerMessage += "Player 3 wins!";
        } else {
            winnerMessage += "It's a tie!";
        }

        // Display winner message
        timerText.setText(winnerMessage);
    }

    // Simulate a player being hit and eliminated from the game
    public void playerHit(int playerNumber) {
        switch (playerNumber) {
            case 1:
                player1Active = false;
                break;
            case 2:
                player2Active = false;
                break;
            case 3:
                player3Active = false;
                break;
        }
    }
}
