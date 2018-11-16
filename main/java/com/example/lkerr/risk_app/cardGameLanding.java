package com.example.lkerr.risk_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class cardGameLanding extends AppCompatActivity {

    private Button bStartGame1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_game_landing);
        bStartGame1 = (Button) findViewById(R.id.bStartGame1);

        bStartGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent game1Start = new Intent(cardGameLanding.this, cardgamescreen1.class);
                cardGameLanding.this.startActivity(game1Start);

            }
        });
    }
}
