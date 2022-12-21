package com.example.sportapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    Button signIn,signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this,navController);


//        signIn=findViewById(R.id.mainAc_SignIn_btn);
//        signUp=findViewById(R.id.mainAc_SignUp_btn);
//
//
//        signIn.setOnClickListener((view -> {
//            Intent signInIntent= new Intent(this, SignInActivity.class);
//            startActivity(signInIntent);
//        }));
//
//        signUp.setOnClickListener((view -> {
//            Intent signUpIntent= new Intent(this, SignUpActivity.class);
//            startActivity(signUpIntent);
//        }));

    }
}
