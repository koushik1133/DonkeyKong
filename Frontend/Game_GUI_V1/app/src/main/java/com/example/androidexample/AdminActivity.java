package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    // Define input fields and button
    private EditText etEmail, etPassword;
    private Button loginBtn, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);  //Link to the activity_admin.xml layout

        //Initialize the input fields and button
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        loginBtn = findViewById(R.id.btnLogin);
        homeBtn = findViewById(R.id.btnHome);

        //Set click listener for the login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Retrieve user input
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                //Validate email and password
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(AdminActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6 || password.length() > 10) {
                    Toast.makeText(AdminActivity.this, "Password must be 6-10 digits long", Toast.LENGTH_SHORT).show();
                } else {
                    //Implement signup logic here
                    Toast.makeText(AdminActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();


                    //Nick this is where you will replace joinLobbyIntent with
                    //adminPrefIntent = new Intent(AdminActivity.this, AdminPrefActivity.this)
                    //startActivity(adminPrefIntent);

                    //Navigate to Admin profile page upon successful signup
                    Intent adminPrefIntent = new Intent(AdminActivity.this, AdminPrefActivity.class);
                    startActivity(adminPrefIntent);
                }
            }
        });

        //Set click listener for the Home button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate back to the HomeActivity
                Intent homeIntent = new Intent(AdminActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();  //Close the current LoginActivity
            }
        });
    }
}
