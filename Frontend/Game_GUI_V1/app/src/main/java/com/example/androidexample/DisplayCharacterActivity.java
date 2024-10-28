package com.example.androidexample;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayCharacterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_character);

        // Retrieve the selected character resource ID
        int characterResId = getIntent().getIntExtra("selectedCharacter", -1);

        // Set the ImageView to display the selected character
        ImageView characterImage = findViewById(R.id.selectedCharacterImage);
        if (characterResId != -1) {
            characterImage.setImageResource(characterResId);
        }
    }
}
