package com.example.lkerr.risk_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class cardgamescreen1 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Firebase mRef;
    private DatabaseReference mDatabase;
    TextView currentGold, currentPlayer;
    Integer currentGoldValue;
    ImageView card1, card2, card3, card4, card5;
    Button play, next;
    int win1 = 0;
    int win2 = 0;

    RadioButton b1, b2;


    List<Integer> cards;

    public void selectOption(View view){
        b1 = (RadioButton) findViewById(R.id.option1);
        b2 = (RadioButton) findViewById(R.id.option2);
        boolean checked1 = b1.isChecked();
        boolean checked2 = b2.isChecked();

        String user_id = mAuth.getCurrentUser().getUid();
        final DatabaseReference current_user = mDatabase.child(user_id);

        if(checked1 || checked2){
            switch (view.getId())
            {
                case R.id.option1:
                    if(checked1){
                        win1 = 1;
                        win2 = 0;
                        play.setVisibility(View.VISIBLE);
                        play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent option1Intent = new Intent(cardgamescreen1.this, cardGameScreen3.class);
                                current_user.child("Card First Choice").setValue("Option 1");

                                startActivity(option1Intent);
                            }
                        });
                    }
                    break;
                case R.id.option2:
                    if(checked2){
                        win2 = 1;
                        win1 = 0;
                        play.setVisibility(View.VISIBLE);
                        play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent option2Intent = new Intent(cardgamescreen1.this, cardgamescreen2.class);
                                current_user.child("Card First Choice").setValue("Option 2");
                                startActivity(option2Intent);
                            }
                        });
                    }
                    break;
            }
        }
    }

    private void flipCard1()
    {
        View rootLayout = findViewById(R.id.card1);
        View cardFace = findViewById(R.id.card1);
        View cardBack = findViewById(R.id.card1);

        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE)
        {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    private void flipCard2()
    {
        View rootLayout = findViewById(R.id.card2);
        View cardFace = findViewById(R.id.card2);
        View cardBack = findViewById(R.id.card2);

        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE)
        {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    private void flipCard3()
    {
        View rootLayout = findViewById(R.id.card3);
        View cardFace = findViewById(R.id.card3);
        View cardBack = findViewById(R.id.card3);

        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE)
        {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    private void flipCard4()
    {
        View rootLayout = findViewById(R.id.card4);
        View cardFace = findViewById(R.id.card4);
        View cardBack = findViewById(R.id.card4);

        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE)
        {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    private void flipCard5()
    {
        View rootLayout = findViewById(R.id.card5);
        View cardFace = findViewById(R.id.card5);
        View cardBack = findViewById(R.id.card5);

        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE)
        {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cardgamescreen1);
        super.onCreate(savedInstanceState);



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



        card1 = (ImageView) findViewById(R.id.card1);
        card2 = (ImageView) findViewById(R.id.card2);
        card3 = (ImageView) findViewById(R.id.card3);
        card4 = (ImageView) findViewById(R.id.card4);
        card5 = (ImageView) findViewById(R.id.card5);

        cards = new ArrayList<>();
        cards.add(1);//ace
        cards.add(2);//10
        cards.add(3);//11
        cards.add(4);//12
        cards.add(5);//13


        //Shuffle cards
        Collections.shuffle(cards);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flipCard1();
                flipCard2();
                flipCard3();
                flipCard4();
                flipCard5();

                //person bets
                current_user.child("gold").setValue(currentGoldValue-5);


                //card1
                if (cards.get(0) == 1 && (win1 == 1 || win2 == 1)) {
                    card1.setImageResource(R.drawable.ace);
                    if(win1 == 1 && win2 == 0) {
                        current_user.child("gold").setValue(currentGoldValue+10);
                    }
                    else if(win1 == 0 && win2 == 1){
                        current_user.child("gold").setValue(currentGoldValue+20);
                    }
                } else if (cards.get(0) == 2) {
                    card1.setImageResource(R.drawable.heart10);
                } else if (cards.get(0) == 3) {
                    card1.setImageResource(R.drawable.clover11);
                } else if (cards.get(0) == 4) {
                    card1.setImageResource(R.drawable.heart12);
                } else if (cards.get(0) == 5) {
                    card1.setImageResource(R.drawable.spade13);
                }

                //card2
                if (cards.get(1) == 1 && (win1 == 1 || win2 == 1)) {
                    card2.setImageResource(R.drawable.ace);
                    if(win1 == 1 && win2 == 0) {
                        current_user.child("gold").setValue(currentGoldValue+10);
                    }
                    else if(win1 == 0 && win2 == 1){
                        current_user.child("gold").setValue(currentGoldValue+20);
                    }
                } else if (cards.get(1) == 2) {
                    card2.setImageResource(R.drawable.heart10);
                } else if (cards.get(1) == 3) {
                    card2.setImageResource(R.drawable.clover11);
                } else if (cards.get(1) == 4) {
                    card2.setImageResource(R.drawable.heart12);
                } else if (cards.get(1) == 5) {
                    card2.setImageResource(R.drawable.spade13);
                }
                //card3
                if (cards.get(2) == 1 && (win1 == 1 || win2 == 0)) {
                    card3.setImageResource(R.drawable.ace);
                    if(win1 == 1 && win2 == 0) {
                        current_user.child("gold").setValue(currentGoldValue+10);
                    }
                } else if (cards.get(2) == 2) {
                    card3.setImageResource(R.drawable.heart10);
                } else if (cards.get(2) == 3) {
                    card3.setImageResource(R.drawable.clover11);
                } else if (cards.get(2) == 4) {
                    card3.setImageResource(R.drawable.heart12);
                } else if (cards.get(2) == 5) {
                    card3.setImageResource(R.drawable.spade13);
                }

                //card4
                if (cards.get(3) == 1) {
                    card4.setImageResource(R.drawable.ace);
                } else if (cards.get(3) == 2) {
                    card4.setImageResource(R.drawable.heart10);
                } else if (cards.get(3) == 3) {
                    card4.setImageResource(R.drawable.clover11);
                } else if (cards.get(3) == 4) {
                    card4.setImageResource(R.drawable.heart12);
                } else if (cards.get(3) == 5) {
                    card4.setImageResource(R.drawable.spade13);
                }

                //card5
                if (cards.get(4) == 1) {
                    card5.setImageResource(R.drawable.ace);
                } else if (cards.get(4) == 2) {
                    card5.setImageResource(R.drawable.heart10);
                } else if (cards.get(4) == 3) {
                    card5.setImageResource(R.drawable.clover11);
                } else if (cards.get(4) == 4) {
                    card5.setImageResource(R.drawable.heart12);
                } else if (cards.get(4) == 5) {
                    card5.setImageResource(R.drawable.spade13);
                }

                play.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDecoy = new Intent(cardgamescreen1.this, cardgamescreen2.class );
                goDecoy.setFlags(goDecoy.FLAG_ACTIVITY_NEW_TASK | goDecoy.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(goDecoy);
                finish();
            }
        });

    }

}
