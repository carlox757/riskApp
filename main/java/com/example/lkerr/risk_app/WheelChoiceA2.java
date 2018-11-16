package com.example.lkerr.risk_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Carlito on 10/03/2017.
 */

public class WheelChoiceA2 extends AppCompatActivity {

    private static Bitmap imageOriginal, imageScaled;
    private static Matrix matrix;
    String result = "";
    RadioButton b1, b2, b3;
    private ImageView dialer, pointer;
    private int dialerHeight, dialerWidth;
    private GestureDetector detector;
    private DatabaseReference mDatabase;
    private Double pointerLocation;
    private FirebaseAuth mAuth;
    TextView currentGold, currentPlayer, wheelOutcome, choice;
    Integer currentGoldValue;
    Double currentAngle = 0.0;
    int win1 = 0;
    int win2 = 0;
    int win3 = 0;
    Button play, next;

    // needed for detecting the inversed rotations
    private boolean[] quadrantTouched;


    private boolean allowRotating;

    public void selectOptionA2(View view){
        b1 = (RadioButton) findViewById(R.id.woption1a3);
        b2 = (RadioButton) findViewById(R.id.woption2a3);
        b3 = (RadioButton) findViewById(R.id.wdecoy1a3);
        boolean checked1 = b1.isChecked();
        boolean checked2 = b2.isChecked();
        boolean checked3 = b3.isChecked();


        if(checked1 || checked2 || checked3){

            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

            String user_id = mAuth.getCurrentUser().getUid();
            final DatabaseReference current_user = mDatabase.child(user_id);
            switch (view.getId())
            {
                case R.id.woption1a3:
                    if(checked1){
                        win1 = 1;
                        win2 = 0;
                        win3 = 0;
                        play.setVisibility(View.VISIBLE);
                        dialer.setVisibility(View.VISIBLE);
                        pointer.setVisibility(View.VISIBLE);
                        current_user.child("Wheel_third_choice").setValue("a");
                    }
                    break;

                case R.id.woption2a3:
                    if(checked2){
                        win2 = 1;
                        win1 = 0;
                        win3 = 0;
                        play.setVisibility(View.VISIBLE);
                        dialer.setVisibility(View.VISIBLE);
                        pointer.setVisibility(View.VISIBLE);
                        current_user.child("Wheel_third_choice").setValue("b");
                    }
                    break;

                case R.id.wdecoy1a3:
                    if(checked3){
                        win2 = 0;
                        win1 = 0;
                        win3 = 1;
                        play.setVisibility(View.VISIBLE);
                        dialer.setVisibility(View.VISIBLE);
                        pointer.setVisibility(View.VISIBLE);
                        current_user.child("Wheel_third_choice").setValue("c");
                    }
                    break;
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheelatwo);

        play = (Button) findViewById(R.id.play1);
        next = (Button) findViewById(R.id.nextwheel);
        pointer = (ImageView)findViewById(R.id.arrow);
        currentGold = (TextView) findViewById(R.id.currentGoldOfUserWheel);
        currentPlayer = (TextView) findViewById(R.id.currentWheelPlayer);
        wheelOutcome = (TextView)findViewById(R.id.wheelOutcome);
        quadrantTouched = new boolean[]{false, false, false, false, false};
        detector = new GestureDetector(this, new WheelChoiceA2.MyGestureDetector());
        dialer = (ImageView)findViewById(R.id.imageView_ring);
        dialer.setOnTouchListener(new WheelChoiceA2.MyOnTouchListener());
        choice = (TextView)findViewById(R.id.choice);

        play.setVisibility(View.GONE);
        next.setVisibility(View.GONE);

        allowRotating = true;


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
            public void onClick(View view) {
                current_user.child("gold").setValue(currentGoldValue-5);

                if ((result == "a" || result == "b" || result == "c") && win1 == 1 ){
                    current_user.child("gold").setValue(currentGoldValue+10);
                    choice.setText("Congratulations you won 10 coins");
                } else if ((result == "a" || result == "b") && win2 == 1){
                    current_user.child("gold").setValue(currentGoldValue+15);
                    choice.setText("Congratulations you won 15 coins");
                } else if ((result == "a") && win3 == 1){
                    current_user.child("gold").setValue(currentGoldValue+15);
                    choice.setText("Congratulations you won 15 coins");
                }
                else{
                    choice.setText("Better luck next time you lost 5 coins");
                }
                dialer.setVisibility(View.GONE);
                play.setVisibility(View.GONE);
                b1.setVisibility(View.GONE);
                b2.setVisibility(View.GONE);
                b3.setVisibility(View.GONE);

                next.setVisibility(View.VISIBLE);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDecoy = new Intent(WheelChoiceA2.this, WheelRating5.class );
                goDecoy.setFlags(goDecoy.FLAG_ACTIVITY_NEW_TASK | goDecoy.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(goDecoy);
                finish();
            }
        });



        // load the image only once
        if (imageOriginal == null) {
            imageOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.wheel);
        }

        //load the image only once
        if (matrix == null){
            matrix = new Matrix();
        }else{
            matrix.reset();
        }



        dialer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // method called more than once, but the values only need to be initialized one time
                if (dialerHeight == 0 || dialerWidth == 0) {
                    dialerHeight = dialer.getHeight();
                    dialerWidth = dialer.getWidth();

                    // resize
                    Matrix resize = new Matrix();
                    resize.postScale((float) Math.min(dialerWidth, dialerHeight) / (float) imageOriginal.getWidth(), (float) Math.min(dialerWidth, dialerHeight) / (float) imageOriginal.getHeight());
                    imageScaled = Bitmap.createBitmap(imageOriginal, 0, 0, imageOriginal.getWidth(), imageOriginal.getHeight(), resize, false);

                    // translate to the image view's center
                    float translateX = dialerWidth / 2 - imageScaled.getWidth() / 2;
                    float translateY = dialerHeight / 2 - imageScaled.getHeight() / 2;
                    matrix.postTranslate(translateX, translateY);

                    dialer.setImageBitmap(imageScaled);
                    dialer.setImageMatrix(matrix);
                }
            }

        });



    }

    private class MyOnTouchListener implements View.OnTouchListener {

        private Double startAngle;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    // reset the touched quadrants
                    for (int i = 0; i < quadrantTouched.length; i++) {
                        quadrantTouched[i] = false;
                    }

                    allowRotating = false;

                    startAngle = getAngle(event.getX(), event.getY());
                    break;

                case MotionEvent.ACTION_MOVE:
                    currentAngle = getAngle(event.getX(), event.getY());
                    rotateDialer((float) (startAngle - currentAngle));
                    startAngle = currentAngle;
                    break;

                case MotionEvent.ACTION_UP:
                    allowRotating = true;
                    break;

            }
            // set the touched quadrant to true
            quadrantTouched[getQuadrant(event.getX() - (dialerWidth / 2), dialerHeight - event.getY() - (dialerHeight / 2))] = true;

            detector.onTouchEvent(event);
            return true;
        }

    }
    private double getAngle(double xTouch, double yTouch) {
        double x = xTouch - (dialerWidth / 2d);
        double y = dialerHeight - yTouch - (dialerHeight / 2d);

        switch (getQuadrant(x, y)) {
            case 1:
                return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 2:
                return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 3:
                return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
            case 4:
                return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            default:
                return 0;
        }
    }

    /**
     * @return The selected quadrant.
     */
    private static int getQuadrant(double x, double y) {
        if (x >= 0) {
            return y >= 0 ? 1 : 4;
        } else {
            return y >= 0 ? 2 : 3;
        }
    }

    private void rotateDialer(float degrees) {
        float[] valuesPre = new float[9];
        matrix.getValues(valuesPre);
        double goblalXPre = valuesPre[Matrix.MTRANS_X];
        double goblalYPre = valuesPre[Matrix.MTRANS_Y];

        matrix.postRotate(degrees, dialerWidth / 2, dialerHeight / 2);
        dialer.setImageMatrix(matrix);
        float[] values = new float[9];
        matrix.getValues(values);
        double goblalX = values[Matrix.MTRANS_X];
        double goblalY = values[Matrix.MTRANS_Y];

        float matrixDeg = (float) Math.toDegrees(Math.atan2(goblalY-goblalYPre, goblalX-goblalXPre));

        if (matrixDeg < 0)
        {
            matrixDeg += 360;
        }

        if (matrixDeg >= 0 && matrixDeg < 90) {
            wheelOutcome.setText("A");
            result= "a";
        } else if (matrixDeg >= 90 && matrixDeg < 180) {
            wheelOutcome.setText("B");
            result= "b";
        } else if (matrixDeg >= 180 && matrixDeg < 270) {
            wheelOutcome.setText("C");
            result= "c";
        } else if (matrixDeg >= 270 && matrixDeg < 360) {
            wheelOutcome.setText("D");
            result= "d";
        }
    }

    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // get the quadrant of the start and the end of the fling
            int q1 = getQuadrant(e1.getX() - (dialerWidth / 2), dialerHeight - e1.getY() - (dialerHeight / 2));
            int q2 = getQuadrant(e2.getX() - (dialerWidth / 2), dialerHeight - e2.getY() - (dialerHeight / 2));

            // the inversed rotations
            if ((q1 == 2 && q2 == 2 && Math.abs(velocityX) < Math.abs(velocityY))
                    || (q1 == 3 && q2 == 3)
                    || (q1 == 1 && q2 == 3)
                    || (q1 == 4 && q2 == 4 && Math.abs(velocityX) > Math.abs(velocityY))
                    || ((q1 == 2 && q2 == 3) || (q1 == 3 && q2 == 2))
                    || ((q1 == 3 && q2 == 4) || (q1 == 4 && q2 == 3))
                    || (q1 == 2 && q2 == 4 && quadrantTouched[3])
                    || (q1 == 4 && q2 == 2 && quadrantTouched[3])) {

                dialer.post(new WheelChoiceA2.FlingRunnable(-1 * (velocityX + velocityY)));
            } else {
                // the normal rotation
                dialer.post(new WheelChoiceA2.FlingRunnable(velocityX + velocityY));
            }

            return true;
        }
    }

    private class FlingRunnable implements Runnable {

        private float velocity;

        public FlingRunnable(float velocity) {
            this.velocity = velocity;
        }

        @Override
        public void run() {
            if (Math.abs(velocity) > 5 && allowRotating) {
                rotateDialer(velocity / 75);
                velocity /= 1.0666F;

                // post this instance again
                dialer.post(this);
            }
        }
    }


}
