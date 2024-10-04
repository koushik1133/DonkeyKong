
package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn, signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.btnLogin);
        signupBtn = findViewById(R.id.btnSignup);

        // Setting up click listeners
        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnLogin) {
            // Login button clicked
            Toast.makeText(MainActivity.this, "Login button clicked", Toast.LENGTH_SHORT).show();
            // You can add code here to interact with a Postman mock server using a network request
        } else if (id == R.id.btnSignup) {
            // Signup button clicked
            Toast.makeText(MainActivity.this, "Signup button clicked", Toast.LENGTH_SHORT).show();
            // You can add code here to navigate to a Signup Activity or handle signup logic
        }
    }
}
