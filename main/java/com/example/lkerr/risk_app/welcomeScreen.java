package com.example.lkerr.risk_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class welcomeScreen extends AppCompatActivity {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        next = (Button) findViewById(R.id.bwelcome);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goldScreen = new Intent(welcomeScreen.this, goldScreen.class);
                goldScreen.setFlags(goldScreen.FLAG_ACTIVITY_NEW_TASK | goldScreen.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(goldScreen);
                finish();
            }
        });
    }
}
