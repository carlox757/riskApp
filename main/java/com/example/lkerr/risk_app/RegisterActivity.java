package com.example.lkerr.risk_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPasswordField;
    private Spinner mAge;
    private Button mRegisterButton;
    private Spinner mGender;
    private Spinner mIncome;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private Spinner mEducation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        Firebase.setAndroidContext(this);

        mAuth = FirebaseAuth.getInstance();
        mNameField = (EditText) findViewById(R.id.etName);
        mEmailField = (EditText) findViewById(R.id.etEmail);
        mPasswordField = (EditText) findViewById(R.id.etPassword);
        mRegisterButton = (Button) findViewById(R.id.bRegister);

        mIncome = (Spinner) findViewById(R.id.incomeSpinner);
        List<String> iList = new ArrayList<String>();
        iList.add("Under 20k");
        iList.add("20k - 29k");
        iList.add("30k - 39k");
        iList.add("Over 40k");
        ArrayAdapter<String> iDataAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, iList);
        iDataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mIncome.setAdapter(iDataAdapter);

        mAge = (Spinner) findViewById(R.id.ageSpinner);
        List<String> aList = new ArrayList<String>();
        aList.add("Under 18");
        aList.add("18 - 24");
        aList.add("25 - 34");
        aList.add("35 - 44");
        aList.add("44+");
        ArrayAdapter<String> aDataAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, aList);
        aDataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mAge.setAdapter(aDataAdapter);

        mGender = (Spinner) findViewById(R.id.genderSpinner);
        List<String> gList = new ArrayList<String>();
        gList.add("male");
        gList.add("female");
        ArrayAdapter<String> gdataAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, gList);
        gdataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mGender.setAdapter(gdataAdapter);

        mEducation = (Spinner) findViewById(R.id.educationSpinner);
        List<String> elist = new ArrayList<String>();
        elist.add("University");
        elist.add("High School");
        elist.add("Primary School");
        elist.add("No education");
        ArrayAdapter<String> eDataAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, elist);
        eDataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mEducation.setAdapter(eDataAdapter);

        mProgress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startRegister();
            }
        });
    }

    private void startRegister() {

        final String name = mNameField.getText().toString().trim().toLowerCase();
        final String email = mEmailField.getText().toString().trim().toLowerCase();
        final String password = mPasswordField.getText().toString().trim();
        final String age = String.valueOf(mAge.getSelectedItem());
        final String gender = String.valueOf(mGender.getSelectedItem());
        final String education = String.valueOf(mEducation.getSelectedItem());
        final String income = String.valueOf(mIncome.getSelectedItem());
        final int gold = 100;


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {


            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        mProgress.setMessage("Signing you up now!");
                        mProgress.show();

                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user = mDatabase.child(user_id);

                        current_user.child("name").setValue(name);
                        current_user.child("email").setValue(email);
                        current_user.child("age").setValue(age);
                        current_user.child("gender").setValue(gender);
                        current_user.child("education").setValue(education);
                        current_user.child("income").setValue(income);
                        current_user.child("Gold").setValue(gold);


                    } else if (password.length() < 6) {
                        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                        alertDialog.setTitle("Password");
                        alertDialog.setMessage("Password must be longer than 6 characters");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                        alertDialog.setTitle("Email");
                        alertDialog.setMessage("The email is already in use");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            });


        }
    }
}