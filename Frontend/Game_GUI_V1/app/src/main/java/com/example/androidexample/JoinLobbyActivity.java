package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class JoinLobbyActivity extends AppCompatActivity {

    //Define input fields and buttons
    private Button joinLobbyBtn, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);  //Link to the activity_join_lobby.xml layout

        //Initialize buttons
        joinLobbyBtn = findViewById(R.id.btnJoinLobby);
        homeBtn = findViewById(R.id.btnHome);

        //Set click listener for the Join Lobby button
        joinLobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Implement join lobby logic here
                Toast.makeText(JoinLobbyActivity.this, "Joined the lobby successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        //Set click listener for the Home button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate back to the HomeActivity
                Intent homeIntent = new Intent(JoinLobbyActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();  //Close the current LoginActivity
            }
        });
    }
}
