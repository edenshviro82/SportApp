package com.example.sportapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class IntroductionActivity extends AppCompatActivity {

    ImageView logo, splash;
    LottieAnimationView lottieAnimationView;
    TextView sport,app;
    NavController navController;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        splash = findViewById(R.id.img_back);
        logo = findViewById(R.id.logo);
        lottieAnimationView = findViewById(R.id.lottie_animation);
        sport=findViewById(R.id.sport_tv);
        app=findViewById(R.id.app_tv);


        splash.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        sport.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        app.animate().translationY(1400).setDuration(1000).setStartDelay(4000);










    }
}