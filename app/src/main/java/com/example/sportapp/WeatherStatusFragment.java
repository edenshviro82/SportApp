package com.example.sportapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sportapp.model.Model;
import com.example.sportapp.model.User;

public class WeatherStatusFragment extends Fragment {

    // actual API key from Open Weather



    String email,temp,icon,type,description;;
    TextView temperature,city,sport;
    TextView prop1Headline,prop2Headline,prop3Headline,prop4Headline,prop5Headline;
    TextView prop1,prop2,prop3,prop4,prop5;
    int stateCode;
    ImageView weatherIcon;
    User user;
    Weather data;




    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather_status, container, false);

        city=view.findViewById(R.id.weather_city_tv);
        temperature=view.findViewById(R.id.weather_degree_tv);
        weatherIcon=view.findViewById(R.id.weather_icon_iv);
        sport=view.findViewById(R.id.weather_sport_tv);

        prop1Headline=view.findViewById(R.id.weather_prop1_headline_tv);
        prop2Headline=view.findViewById(R.id.weather_prop2_headline_tv);
        prop3Headline=view.findViewById(R.id.weather_prop3_headline_tv);
        prop4Headline=view.findViewById(R.id.weather_prop4_headline_tv);
        prop5Headline=view.findViewById(R.id.weather_prop5_headline_tv);
        prop1=view.findViewById(R.id.weather_prop1_tv);
        prop2=view.findViewById(R.id.weather_prop2_tv);
        prop3=view.findViewById(R.id.weather_prop3_tv);
        prop4=view.findViewById(R.id.weather_prop4_tv);
        prop5=view.findViewById(R.id.weather_prop5_tv);



        email = WeatherStatusFragmentArgs.fromBundle(getArguments()).getUserEmail();
        Log.d("TAG",email);
        user=Model.instance().getAllUsers().get(email);
        // Get the temperature for a specific city
        data=Weather.getWeatherDataForCity(user.getCity(),user.getSport());
        updateUI(data);

        return view;
    }

    private void updateUI(Weather data) {

          int roundTemp=(int)Math.round(data.temperature);
          temperature.setText(roundTemp+" °C");
          int drawableResourceId = getResources().getIdentifier(data.icon, "drawable", getActivity().getPackageName());
          weatherIcon.setImageResource(drawableResourceId);
          city.setText(user.getCity());
          sport.setText("Chosen sport: "+user.getSport());

        prop1Headline.setText("");
        prop2Headline.setText("");
        prop3Headline.setText("");
        prop4Headline.setText("");
        prop5Headline.setText("");
        prop1.setText("");
        prop2.setText("");
        prop3.setText("");
        prop4.setText("");
        prop5.setText("");




    }


    @Override
    public void onResume() {
        super.onResume();
        user=Model.instance().getAllUsers().get(email);
        // Get the temperature for a specific city
        data=Weather.getWeatherDataForCity(user.getCity(),user.getSport());
        updateUI(data);
    }



}