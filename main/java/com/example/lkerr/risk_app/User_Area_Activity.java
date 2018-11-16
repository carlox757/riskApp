package com.example.lkerr.risk_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class User_Area_Activity extends AppCompatActivity {

    private Button bLogout, bGame1, bGame2, bGame3, bGame4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__area_);
        Firebase.setAndroidContext(this);

        bLogout = (Button) findViewById(R.id.bLogout);
        bGame1 = (Button) findViewById(R.id.bGame1);
        bGame2 = (Button) findViewById(R.id.bGame2);
        bGame3 = (Button) findViewById(R.id.bGame3);
        bGame4 = (Button) findViewById(R.id.bGame4);

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent logoutIntent = new Intent(User_Area_Activity.this, LogIn.class);
                logoutIntent.setFlags(logoutIntent.FLAG_ACTIVITY_NEW_TASK | logoutIntent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);
                finish();
            }
        });

        bGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game1Intent = new Intent(User_Area_Activity.this, cardGameLanding.class);
                User_Area_Activity.this.startActivity(game1Intent);
            }
        });

        bGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game2Intent = new Intent(User_Area_Activity.this, game2Landing.class);
                User_Area_Activity.this.startActivity(game2Intent);
            }
        });

        bGame3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent game3Intent = new Intent(User_Area_Activity.this, game3Landing.class);
                User_Area_Activity.this.startActivity(game3Intent);

            }
        });

        bGame4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent game4Intent = new Intent(User_Area_Activity.this, game4Landing.class);
                User_Area_Activity.this.startActivity(game4Intent);
                
            }
        });


    }

}
