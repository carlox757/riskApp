package com.example.lkerr.risk_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Carlito on 13/03/2017.
 */



public class ratingBar3Option1 extends AppCompatActivity {

    public SeekBar seek;
    public Button bDecision;
    TextView currentGold, currentPlayer, seekValue;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Integer currentGoldValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar3option1);

        seek = (SeekBar) findViewById(R.id.seekBar);
        bDecision = (Button) findViewById(R.id.bDecision1);
        currentGold = (TextView) findViewById(R.id.currentGoldOfUser);
        currentPlayer = (TextView) findViewById(R.id.currentPlayer);
        seekValue = (TextView)findViewById(R.id.seekValue);

        seek.setMax(90);


        seek.setProgress(50);
        seekValue.setText("5");

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


        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int valueToUse = (progress+10)/10;
                seekValue.setText(String.valueOf(valueToUse));
                current_user.child("Difficulty of choice 4 Option 1").setValue(valueToUse);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bDecision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rating1Intent = new Intent(ratingBar3Option1.this, cardGameScreen6.class);
                rating1Intent.setFlags(rating1Intent.FLAG_ACTIVITY_NEW_TASK | rating1Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(rating1Intent);
                finish();
            }
        });
    }
}
