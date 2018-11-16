package com.example.lkerr.risk_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class goldScreen extends AppCompatActivity {

    Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_screen);

        play = (Button) findViewById(R.id.bGoldScreen);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playGame = new Intent(goldScreen.this, wheelGameLanding.class);
                startActivity(playGame);
                finish();
            }
        });
    }
}
