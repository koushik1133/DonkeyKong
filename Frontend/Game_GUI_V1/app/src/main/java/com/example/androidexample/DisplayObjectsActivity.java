package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class DisplayObjectsActivity extends AppCompatActivity {
    //Define the home button
    private Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_objects);  //Link to activity_display_objects.xml

        //Initialize the home button
        homeBtn = findViewById(R.id.btnHome);

        //Set click listener for the Home button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to HomeActivity
                Intent homeIntent = new Intent(DisplayObjectsActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();  // Close the current DisplayObjectsActivity
            }
        });

        //Get the data from the Intent
        String objectsData = getIntent().getStringExtra("objectsData");

        //Display the data in a TextView (for now)
        TextView displayTextView = findViewById(R.id.tvDisplayObjects);
        displayTextView.setText(objectsData);
    }
}
