package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class CharacterSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);

        // Set up click listeners for each character
        findViewById(R.id.character1).setOnClickListener(view -> selectCharacter(R.drawable.side_idle_1));
        findViewById(R.id.character2).setOnClickListener(view -> selectCharacter(R.drawable.side_idle_walk_1));
        findViewById(R.id.character3).setOnClickListener(view -> selectCharacter(R.drawable.kong_1));
    }

    private void selectCharacter(int characterResId) {
        Intent intent = new Intent(this, DisplayCharacterActivity.class);
        intent.putExtra("selectedCharacter", characterResId);
        startActivity(intent);
    }
}
