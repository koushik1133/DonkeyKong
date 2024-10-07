package com.example.androidexample;

import android.content.Intent;  //Import for using Intents
import android.os.Bundle;  //Import for onCreate and Bundle class
import android.util.Patterns;  //Import for email pattern validation
import android.view.View;  //Import for View class
import android.widget.Button;  //Import for Button class
import android.widget.EditText;  //Import for EditText class
import android.widget.Toast;  //Import for Toast class
import androidx.appcompat.app.AppCompatActivity;  //Import for AppCompatActivity

public class SignupActivity extends AppCompatActivity {

    //Define input fields and buttons
    private EditText etEmail, etPassword;
    private Button signupBtn, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);  //Link to the activity_signup.xml layout

        //Initialize the input fields and buttons
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        signupBtn = findViewById(R.id.btnSignup);
        homeBtn = findViewById(R.id.btnHome);

        //Set click listener for the signup button
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Retrieve user input
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                //Validate email and password
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(SignupActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6 || password.length() > 10) {
                    Toast.makeText(SignupActivity.this, "Password must be 6-10 digits long", Toast.LENGTH_SHORT).show();
                } else {
                    //Implement signup logic here
                    Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    //Navigate to Join Lobby page upon successful signup
                    Intent joinLobbyIntent = new Intent(SignupActivity.this, JoinLobbyActivity.class);
                    startActivity(joinLobbyIntent);
                }
            }
        });

        //Set click listener for the Home button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate back to the HomeActivity
                Intent homeIntent = new Intent(SignupActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();  //Close the current LoginActivity
            }
        });
    }
}
