package com.example.sportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button signIn,signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signIn=findViewById(R.id.mainAc_SignIn_btn);
        signUp=findViewById(R.id.mainAc_SignUp_btn);


        signIn.setOnClickListener((view -> {
            Intent signInIntent= new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
        }));

        signUp.setOnClickListener((view -> {
            Intent signUpIntent= new Intent(this, SignUpActivity.class);
            startActivity(signUpIntent);
        }));

    }
}
