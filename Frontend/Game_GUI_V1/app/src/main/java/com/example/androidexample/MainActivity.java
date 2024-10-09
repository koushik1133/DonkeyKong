
package com.example.androidexample;

import android.content.Intent;  // For Intent class
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //All the buttons on screen need initialized
    private Button loginBtn, signupBtn, adminBtn, homeBtn;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    loginBtn = findViewById(R.id.btnLogin);
    signupBtn = findViewById(R.id.btnSignup);
    adminBtn = findViewById(R.id.btnAdmin);
    homeBtn = findViewById(R.id.btnHome);

    // Set click listeners
    //Set click listener for Login button
    loginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Create an Intent to start LoginActivity
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);  // Start LoginActivity
        }
    });

    //Set click lsitener for Signup button
    signupBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Create an Intent to start SignupActivity
            Intent signupIntent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(signupIntent);  // Start SignupActivity
        }
    });

    //Set click listener for Admin button
    adminBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Create an Intent to start AdminActivity
            Intent adminIntent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(adminIntent);  // Start AdminActivity
        }
    });

    //Set click listener for the Home button
    homeBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Navigate back to the HomeActivity
            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();  //Close the current LoginActivity
        }
    });
    }
}