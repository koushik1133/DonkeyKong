
package com.example.androidexample;

import android.content.Intent;  // For Intent class
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn, signupBtn, adminBtn;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    loginBtn = findViewById(R.id.btnLogin);
    signupBtn = findViewById(R.id.btnSignup);
    adminBtn = findViewById(R.id.btnAdmin);

    // Set click listeners
    loginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Create an Intent to start LoginActivity
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);  // Start LoginActivity
        }
    });

    signupBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Create an Intent to start SignupActivity
            Intent signupIntent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(signupIntent);  // Start SignupActivity
        }
    });

    adminBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Create an Intent to start AdminActivity
            Intent adminIntent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(adminIntent);  // Start AdminActivity
        }
    });
    }
}