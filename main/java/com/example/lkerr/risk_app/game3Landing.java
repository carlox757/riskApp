package com.example.lkerr.risk_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class game3Landing extends AppCompatActivity {

    private Button bStartGame3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3_landing);
        bStartGame3 = (Button) findViewById(R.id.bStartGame3);

        bStartGame3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent game3Start = new Intent(game3Landing.this, WheelGameScreen1.class);
                game3Landing.this.startActivity(game3Start);

            }
        });
    }
}
