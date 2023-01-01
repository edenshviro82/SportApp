package com.example.sportapp;

import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sportapp.model.Model;
import com.example.sportapp.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherStatusFragment extends Fragment {

    // actual API key from Open Weather



    String city,email,temp,icon,type,description;;
    TextView temperature,state;
    int stateCode;
    ImageView weatherIcon;
    User user;
    Weather data;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather_status, container, false);

        state=view.findViewById(R.id.weather_state_tv);
        temperature=view.findViewById(R.id.weather_degree_tv);
        weatherIcon=view.findViewById(R.id.weather_icon_iv);
        email = WeatherStatusFragmentArgs.fromBundle(getArguments()).getUserEmail();
        Log.d("TAG",email);
        user=Model.instance().getAllUsers().get(email);
        // Get the temperature for a specific city
        data=Weather.getWeatherDataForCity(user.getCity());
        updateUI(data);

        return view;
    }

    private void updateUI(Weather data) {

        temperature.setText(" °C");
        int iconId = getResources().getIdentifier("com.example.sportapp:drawable/" + data.icon, null, null);
        Drawable icon = ContextCompat.getDrawable(getContext(), iconId);
        weatherIcon.setImageDrawable(icon);

    }


    @Override
    public void onResume() {
        super.onResume();
        user=Model.instance().getAllUsers().get(email);
        // Get the temperature for a specific city
        data=Weather.getWeatherDataForCity(user.getCity());
        updateUI(data);
    }



}