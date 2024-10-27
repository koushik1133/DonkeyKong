package com.example.androidexample;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class LevelActivity extends AppCompatActivity {
    private ImageView player;
    private boolean isJumping = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        player = findViewById(R.id.player);

        //Set a jump action or button
        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isJumping) {
                    jumpPlayer();
                }
            }
        });
    }

    private void jumpPlayer() {
        isJumping = true;

        //Took this from google
        player.animate()
                .translationYBy(-200) // Jump up
                .setDuration(300)
                .withEndAction(() -> {
                    player.animate()
                            .translationYBy(200) // Come back down
                            .setDuration(300)
                            .withEndAction(() -> isJumping = false);
                });
    }
}
