package com.example.sportapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    String userEmail;
    NavController navController;
    private NavDirections action;
    Activity activity;
    static String s;
    Intent thisI;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth= FirebaseAuth.getInstance();
        setContentView(R.layout.activity_home);
        s=getPackageName();
        activity=this;
        thisI = getIntent();
        userEmail = thisI.getStringExtra("userEmail");

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.home_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);

        BottomNavigationView bottomNavView = findViewById(R.id.home_bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.myReviewsFragment:
                        action= MyReviewsFragmentDirections.actionGlobalMyReviewsFragment(userEmail);
                        Navigation.findNavController(activity, R.id.home_navhost).navigate(action);
                        return true;

                    case R.id.allReviewsFragment:
                        action= AllReviewsFragmentDirections.actionGlobalAllReviewsFragment();
                        Navigation.findNavController(activity, R.id.home_navhost).navigate(action);
                        return true;

                    case R.id.weatherStatusFragment:
                        action= WeatherStatusFragmentDirections.actionGlobalWeatherStatusFragment(userEmail);
                        Navigation.findNavController(activity, R.id.home_navhost).navigate(action);
                        return true;
                    default:
                        return NavigationUI.onNavDestinationSelected(item,navController);

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu_home_act,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                navController.popBackStack();
                return true;

            case R.id.signUpFragment:
                firebaseAuth.signOut();
                Intent intent = new Intent(this, IntroductionActivity.class);
                startActivity(intent);
                return true;

            case R.id.userDetailsFragment:
                action= UserDetailsFragmentDirections.actionGlobalUserDetailsFragment(userEmail);
                Navigation.findNavController(this, R.id.home_navhost).navigate(action);
                return true;

            case R.id.addReviewFragment:
                action= AddReviewFragmentDirections.actionGlobalAddReviewFragment(userEmail);
                Navigation.findNavController(this, R.id.home_navhost).navigate(action);
                return true;


            default:
                return NavigationUI.onNavDestinationSelected(item,navController);

        }

    }

    public static String getPackage(){return s;}
}