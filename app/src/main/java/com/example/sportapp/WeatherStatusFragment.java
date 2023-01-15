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

import java.util.List;

public class WeatherStatusFragment extends Fragment {

    // actual API key from Open Weather



    String email,temp,icon,type,description;;
    TextView temperature,city,sport;
    TextView prop1Headline,prop2Headline,prop3Headline,prop4Headline,prop5Headline;
    TextView prop1,prop2,prop3,prop4,prop5;
    int stateCode;
    ImageView weatherIcon;
    User user=new User();
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
       // user=Model.instance().getAllUsers().get(email);
        Model.instance().getAllUsers((userList)->{
            List<User> list= userList;
            user=Model.instance().getUserByEmail(list,email);
        });

        // Get the temperature for a specific city
        data=Weather.getWeatherDataForCity(user.getCity());
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
        OceanWeather oceanData=OceanWeather.getWeatherDataForCity(user.getCity());

        switch (user.getSport()) {
            case "Running":
                prop1Headline.setText("Feels like: ");
                prop1.setText(oceanData.getFeelsLikeC() + " °C");
                prop2Headline.setText("Humidity: ");
                prop2.setText(oceanData.getHumidity() + " %");
                prop3Headline.setText("Weather description: ");
                prop3.setText(data.weatherDescription);
                prop4Headline.setText("uvIndex: ");
                prop4.setText(oceanData.getUvIndex() + " uv");
                prop5Headline.setText("Visibility: ");
                prop5.setText(oceanData.getVisibility() + " %");
                break;
            case "Skiing":
                prop1Headline.setText("total snow: ");
                prop1.setText(oceanData.getTotalSnowCM() + " cm");
                prop2Headline.setText("Chance of snow: ");
                prop2.setText(oceanData.getChanceOfSnow() + " %");
                prop3Headline.setText("Weather description: ");
                prop3.setText(data.weatherDescription);
                prop4Headline.setText("Wind Direction: ");
                prop4.setText(data.windDirection);
                prop5Headline.setText("cloud cover: ");
                prop5.setText(oceanData.getCloudCover() + " %");

                break;
            case "Kiting":
                prop1Headline.setText("Wind Speed: ");
                prop1.setText(data.windSpeed+" meter/sec");
                prop2Headline.setText("Wind Direction: ");
                prop2.setText(data.windDirection);
                prop3Headline.setText("Weather description: ");
                prop3.setText(data.weatherDescription);
                prop4Headline.setText("rain chance today: ");
                prop4.setText(oceanData.getChanceOfRain() + "%");
                prop5Headline.setText("uvIndex: ");
                prop5.setText(oceanData.getUvIndex() + " uv");
                break;
            case "Abseiling":
                prop1Headline.setText("Wind Speed: ");
                prop1.setText(data.windSpeed+" meter/sec");
                prop2Headline.setText("rain chance today: ");
                prop2.setText(oceanData.getChanceOfRain() + " %");
                prop3Headline.setText("uvIndex: ");
                prop3.setText(oceanData.getUvIndex() + " uv");
                prop4Headline.setText("Chance of thunder: ");
                prop4.setText(oceanData.getChanceOfThunder() + " %");
                prop5Headline.setText("Visibility: ");
                prop5.setText(oceanData.getVisibility() + " %");
                break;
            case "Basketball":
                prop1Headline.setText("Feels like: ");
                prop1.setText(oceanData.getFeelsLikeC() + " °C");
                prop2Headline.setText("Humidity: ");
                prop2.setText(oceanData.getHumidity() + " %");
                prop3Headline.setText("Weather description: ");
                prop3.setText(data.weatherDescription);
                prop4Headline.setText("uvIndex: ");
                prop4.setText(oceanData.getUvIndex() + " uv");
                prop5Headline.setText("Wind Speed: ");
                prop5.setText(data.windSpeed+" meter/sec");
                break;
            case "Football":
                prop1Headline.setText("total snow: ");
                prop1.setText(oceanData.getTotalSnowCM() + " cm");
                prop2Headline.setText("Humidity: ");
                prop2.setText(oceanData.getHumidity() + " %");
                prop3Headline.setText("Weather description: ");
                prop3.setText(data.weatherDescription);
                prop4Headline.setText("uvIndex: ");
                prop4.setText(oceanData.getUvIndex() + " uv");
                prop5Headline.setText("rain chance today: ");
                prop5.setText(oceanData.getChanceOfRain() + "%");
                break;
            case "Outside walking":
                prop1Headline.setText("Feels like: ");
                prop1.setText(oceanData.getFeelsLikeC() + " °C");
                prop2Headline.setText("Humidity: ");
                prop2.setText(oceanData.getHumidity() + " %");
                prop3Headline.setText("Weather description: ");
                prop3.setText(data.weatherDescription);
                prop4Headline.setText("uvIndex: ");
                prop4.setText(oceanData.getUvIndex() + " uv");
                prop5Headline.setText("Visibility: ");
                prop5.setText(oceanData.getVisibility() + " % ");
                break;

            case "Badminton":
                prop1Headline.setText("Wind Speed: ");
                prop1.setText(data.windSpeed+" meter/sec");
                prop2Headline.setText("Wind Direction: ");
                prop2.setText(data.windDirection);
                prop3Headline.setText("Weather description: ");
                prop3.setText(data.weatherDescription);
                prop4Headline.setText("rain chance today: ");
                prop4.setText(oceanData.getChanceOfRain() + "%");
                prop5Headline.setText("Feels like: ");
                prop5.setText(oceanData.getFeelsLikeC() + " °C");
                break;

            case "Biking":
                prop1Headline.setText("Wind Speed: ");
                prop1.setText(data.windSpeed+" meter/sec");
                prop2Headline.setText("Humidity: ");
                prop2.setText(oceanData.getHumidity() + " %");
                prop3Headline.setText("Chance of fog: ");
                prop3.setText(oceanData.getChanceoffog() + " %");
                prop4Headline.setText("rain chance today: ");
                prop4.setText(oceanData.getChanceOfRain() + "%");
                prop5Headline.setText("Visibility: ");
                prop5.setText(oceanData.getVisibility() + " % ");
                break;
            case "Yoga":
                prop1Headline.setText("Feels like: ");
                prop1.setText(oceanData.getFeelsLikeC() + " °C");
                prop2Headline.setText("Humidity: ");
                prop2.setText(oceanData.getHumidity() + " %");
                prop3Headline.setText("Weather description: ");
                prop3.setText(data.weatherDescription);
                prop4Headline.setText("rain chance today: ");
                prop4.setText(oceanData.getChanceOfRain() + "%");
                prop5Headline.setText("Wind Speed: ");
                prop5.setText(data.windSpeed+" meter/sec");
                break;
            case "Tennis":
                prop1Headline.setText("Wind Speed: ");
                prop1.setText(data.windSpeed+" meter/sec");
                prop2Headline.setText("Wind Direction: ");
                prop2.setText(data.windDirection);
                prop3Headline.setText("Weather description: ");
                prop3.setText(data.weatherDescription);
                prop4Headline.setText("rain chance today: ");
                prop4.setText(oceanData.getChanceOfRain() + "%");
                prop5Headline.setText("Feels like: ");
                prop5.setText(oceanData.getFeelsLikeC() + " °C");
                break;
            default:
                // do something for any other activity
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Model.instance().getAllUsers((userList)->{
            List<User> list= userList;
            for(User u:list)
            {
                if(u.getEmail().equals(email))
                    user=u;
            }
        });
        // Get the temperature for a specific city
        data=Weather.getWeatherDataForCity(user.getCity());
        updateUI(data);
    }



}