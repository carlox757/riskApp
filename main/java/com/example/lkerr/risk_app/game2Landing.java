package com.example.lkerr.risk_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class game2Landing extends AppCompatActivity {

    private Button bStartGame2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2_landing);
        bStartGame2 = (Button) findViewById(R.id.bStartGame2);

        bStartGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent game2Start = new Intent(game2Landing.this, game2.class);
                game2Landing.this.startActivity(game2Start);

            }
        });
    }
}
