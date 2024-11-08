package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class GameOverActivity extends AppCompatActivity {

    private Button exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        exitBtn = findViewById(R.id.exitButton);

        //Set click listener for Exit button
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start ExitActivity
                Intent exitIntent = new Intent(GameOverActivity.this, HomeActivity.class);
                startActivity(exitIntent);  // Go to HomeActivity
            }
        });
    }
}
