package com.example.lkerr.risk_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class game4Landing extends AppCompatActivity {

    private Button bStartGame4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game4_landing);
        bStartGame4 = (Button) findViewById(R.id.bStartGame4);

        bStartGame4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent game4Start = new Intent(game4Landing.this,DiceActivity.class);
                game4Landing.this.startActivity(game4Start);

            }
        });
    }
}
