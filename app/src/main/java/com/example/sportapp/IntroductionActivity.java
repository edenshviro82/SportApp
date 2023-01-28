package com.example.sportapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;

public class IntroductionActivity extends AppCompatActivity {

    ImageView logo, splash;
    LottieAnimationView lottieAnimationView;
    TextView sport, app;
    NavController navController;
    FirebaseAuth firebaseAuth;
    Intent i;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        splash = findViewById(R.id.img_back);
        logo = findViewById(R.id.logo);
        lottieAnimationView = findViewById(R.id.lottie_animation);
        sport = findViewById(R.id.sport_tv);
        app = findViewById(R.id.app_tv);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){ //if the user is logged in
             i = new Intent(this, HomeActivity.class);
             i.putExtra("userEmail",firebaseAuth.getCurrentUser().getEmail());
        }else { //first time he need to sign up+
             i = new Intent(this, MainActivity.class);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        splash.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        sport.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        app.animate().translationY(1400).setDuration(1000).setStartDelay(4000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(i);
            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

}