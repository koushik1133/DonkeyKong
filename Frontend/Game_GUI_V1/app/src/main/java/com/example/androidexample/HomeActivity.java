package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);  // Link to the activity_home.xml layout

        Button characterSelectionButton = findViewById(R.id.charSelectionBtn);

        //Initialize the buttons
        Button proceedBtn = findViewById(R.id.btnProceed);
        Button diffSet = findViewById(R.id.btnDifSet);
        // Initialize the button for Game Level
        Button levelBtn = findViewById(R.id.btnLevel);

        //Set click listener for the button to navigate to the login/signup page (MainActivity)
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to MainActivity (which will handle login and signup)
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Set click listener for Difficulty Settings button
        diffSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an Intent to start SignupActivity
                Intent diffIntent = new Intent(HomeActivity.this, DifficultySettingsActivity.class);
                startActivity(diffIntent);  //Start DifficultySettingsActivity
            }
        });

        //Set click listener for Character Selection button
        characterSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CharacterSelectionActivity.class);
                startActivity(intent);
            }
        });

        //Set click listener for Game Level button
        levelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to LevelActivity
                Intent intent = new Intent(HomeActivity.this, LevelActivity.class);
                startActivity(intent);
            }
        });
    }
}