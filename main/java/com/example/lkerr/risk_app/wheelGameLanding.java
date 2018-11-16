package com.example.lkerr.risk_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class wheelGameLanding extends AppCompatActivity {

    Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_game_landing);

        play = (Button) findViewById(R.id.bWheelGame);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playGame = new Intent (wheelGameLanding.this, WheelGame.class);
                playGame.setFlags(playGame.FLAG_ACTIVITY_NEW_TASK | playGame.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(playGame);
                finish();
            }
        });
    }
}
