package com.example.sportapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    String userEmail;
    NavController navController;
    private NavDirections action;
    private NavDirections action2;
    Activity t;
    static String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        s=getPackageName();
        t=this;
        Intent thisI = getIntent();
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
                        Navigation.findNavController(t, R.id.home_navhost).navigate(action);
                        return true;

                    case R.id.allReviewsFragment:
                        action= AllReviewsFragmentDirections.actionGlobalAllReviewsFragment(userEmail);
                        Navigation.findNavController(t, R.id.home_navhost).navigate(action);
                        return true;

                    case R.id.weatherStatusFragment:
                        action= WeatherStatusFragmentDirections.actionGlobalWeatherStatusFragment(userEmail);
                        Navigation.findNavController(t, R.id.home_navhost).navigate(action);
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
                //logout
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;


            case R.id.editUserFragment:
                action= EditUserFragmentDirections.actionGlobalEditUserFragment(userEmail);
                Navigation.findNavController(this, R.id.home_navhost).navigate(action);
                return true;

            case R.id.addReviewFragment:
                action= AddReviewFragmentDirections.actionGlobalAddReviewFragment(userEmail);
                Navigation.findNavController(this, R.id.home_navhost).navigate(action);
                return true;

//            case R.id.myReviewsFragment:
//                action= MyReviewsFragmentDirections.actionGlobalMyReviewsFragment(userEmail);
//                Navigation.findNavController(this, R.id.home_navhost).navigate(action);
//                return true;


            default:
                return NavigationUI.onNavDestinationSelected(item,navController);

        }

    }

    public static String getPackage(){return s;}
}