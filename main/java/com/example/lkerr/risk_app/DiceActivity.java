package com.example.lkerr.risk_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class DiceActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Firebase mRef;
    private DatabaseReference mDatabase;
    TextView currentGold, currentPlayer;
    Integer currentGoldValue;
    int win1 = 0;
    int win2 = 0;
    int win3 = 0;

    Button b_roll, next, play;

    RadioButton b1, b2, b3;

    ImageView iv_dice;

    Random r;

    int rollednum;

    public void selectOption(View view){
        b1 = (RadioButton) findViewById(R.id.r1);
        b2 = (RadioButton) findViewById(R.id.r2);
        b3 = (RadioButton) findViewById(R.id.r3);

        boolean checked1 = b1.isChecked();
        boolean checked2 = b2.isChecked();
        boolean checked3 = b3.isChecked();

        if(checked1 || checked2 || checked3){
            switch (view.getId()){
                case R.id.r1:
                    if(checked1){
                        win1 = 1;
                        win2 = 0;
                        win3 = 0;
                        b_roll.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.r2:
                    if(checked2){
                        win1 = 0;
                        win2 = 1;
                        win3 = 0;
                        b_roll.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.r3:
                    if(checked3){
                        win1 = 0;
                        win2 = 0;
                        win3 = 1;
                        b_roll.setVisibility(View.VISIBLE);
                    }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game4);

        currentGold = (TextView) findViewById(R.id.currentGoldOfUser);
        currentPlayer = (TextView) findViewById(R.id.currentPlayer);
        play = (Button) findViewById(R.id.play1);
        next = (Button) findViewById(R.id.nextGame1);

        play.setVisibility(View.GONE);
        next.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        String user_id = mAuth.getCurrentUser().getUid();
        final DatabaseReference current_user = mDatabase.child(user_id);

        current_user.child("name").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                currentPlayer.append(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        current_user.child("gold").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Integer gold = dataSnapshot.getValue(Integer.class);
                currentGoldValue = gold;
                currentGold.setText(currentGoldValue.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                current_user.child("gold").setValue(currentGoldValue-5);

                b_roll = (Button) findViewById(R.id.b_roll);

                iv_dice = (ImageView) findViewById(R.id.iv_dice);

                r = new Random();

                b_roll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        rollednum = r.nextInt(6) + 1;

                        if(rollednum == 1){
                            iv_dice.setImageResource(R.drawable.die1);
                        } else if(rollednum == 2){
                            iv_dice.setImageResource(R.drawable.die2);
                        } else if(rollednum == 3){
                            iv_dice.setImageResource(R.drawable.die3);
                        } else if(rollednum == 4){
                            iv_dice.setImageResource(R.drawable.die4);
                        } else if(rollednum == 5){
                            iv_dice.setImageResource(R.drawable.die5);
                        }else if(rollednum == 6){
                            iv_dice.setImageResource(R.drawable.die6);
                        }
                    }
                });

                if(rollednum == 1 || rollednum == 2 || rollednum == 3 || rollednum == 4){
                    if(win1 == 1 && win2 == 0 && win3 == 0) {
                        current_user.child("gold").setValue(currentGoldValue+10);
                    }
                    else if(win1 == 0 && win2 == 1 && win3 == 0) {
                        current_user.child("gold").setValue(currentGoldValue+20);
                    }
                    else if(win1 == 0 && win2 == 0 && win3 == 1) {
                        current_user.child("gold").setValue(currentGoldValue+20);
                    }
                }

                play.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHome = new Intent(DiceActivity.this, User_Area_Activity.class );
                goHome.setFlags(goHome.FLAG_ACTIVITY_NEW_TASK | goHome.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(goHome);
                finish();

            }
        });


    }
}
