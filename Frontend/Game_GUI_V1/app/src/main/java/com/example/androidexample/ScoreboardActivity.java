package com.example.androidexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreboardActivity {
    private int player1Score = 3;
    private int player2Score = 3;
    private int player3Score = 3;

    private TextView player1ScoreText;
    private TextView player2ScoreText;
    private TextView player3ScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1ScoreText = findViewById(R.id.player1Score);
        player2ScoreText = findViewById(R.id.player2Score);
        player3ScoreText = findViewById(R.id.player3Score);

        Button hitButton1 = findViewById(R.id.hitButton1);
        Button hitButton2 = findViewById(R.id.hitButton2);
        Button hitButton3 = findViewById(R.id.hitButton3);

        hitButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitPlayer(1);
            }
        });

        hitButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitPlayer(2);
            }
        });

        hitButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitPlayer(3);
            }
        });
    }

    private void hitPlayer(int player) {
        switch (player) {
            case 1:
                if (player1Score > 0) player1Score--;
                player1ScoreText.setText("Player 1: " + player1Score);
                break;
            case 2:
                if (player2Score > 0) player2Score--;
                player2ScoreText.setText("Player 2: " + player2Score);
                break;
            case 3:
                if (player3Score > 0) player3Score--;
                player3ScoreText.setText("Player 3: " + player3Score);
                break;
        }
    }
}
