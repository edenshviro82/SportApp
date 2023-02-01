package com.example.sportapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportapp.databinding.FragmentMyReviewDetailsBinding;
import com.example.sportapp.databinding.FragmentWeatherStatusBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.User;


public class WeatherStatusFragment extends Fragment {

    // actual API key from Open Weather
    String email;
    @NonNull FragmentWeatherStatusBinding binding;
    User user=new User();
    Weather data;



    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Weather Status");
        binding = FragmentWeatherStatusBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        email = WeatherStatusFragmentArgs.fromBundle(getArguments()).getUserEmail();

        return view;
    }

    private void updateUI(Weather data) {

          int roundTemp=(int)Math.round(data.temperature);
          binding.weatherDegreeTv.setText(roundTemp+" °C"); // Displaying the rounded temperature with degree symbol
          int drawableResourceId = getResources().getIdentifier(data.icon, "drawable", getActivity().getPackageName());
          binding.weatherIconIv.setImageResource(drawableResourceId); // Setting the image resource based on the weather icon name
          binding.weatherCityTv.setText(user.getCity());
          binding.weatherSportTv.setText("Chosen sport: "+user.getSport()); // Displaying the chosen sport

        // Clearing any existing text in the TextView
        binding.weatherProp1HeadlineTv.setText("");
        binding.weatherProp2HeadlineTv.setText("");
        binding.weatherProp3HeadlineTv.setText("");
        binding.weatherProp4HeadlineTv.setText("");
        binding.weatherProp5HeadlineTv.setText("");
        binding.weatherProp1Tv.setText("");
        binding.weatherProp2Tv.setText("");
        binding.weatherProp3Tv.setText("");
        binding.weatherProp4Tv.setText("");
        binding.weatherProp5Tv.setText("");
        OceanWeather oceanData=OceanWeather.getWeatherDataForCity(user.getCity());

        if(oceanData == null)
            Toast.makeText(getActivity().getApplicationContext(), "Invalid city ", Toast.LENGTH_LONG).show();

        //changing the fields according to the sport the user choosed
        switch (user.getSport()) {
            case "Running":
                binding.weatherProp1HeadlineTv.setText("Feels like: ");
                binding.weatherProp1Tv.setText(oceanData.getFeelsLikeC() + " °C");
                binding.weatherProp2HeadlineTv.setText("Humidity: ");
                binding.weatherProp2Tv.setText(oceanData.getHumidity() + " %");
                binding.weatherProp3HeadlineTv.setText("description: ");
                binding.weatherProp3Tv.setText(data.weatherDescription);
                binding.weatherProp4HeadlineTv.setText("uvIndex: ");
                binding.weatherProp4Tv.setText(oceanData.getUvIndex() + " uv");
                binding.weatherProp5HeadlineTv.setText("Visibility: ");
                binding.weatherProp5Tv.setText(oceanData.getVisibility() + " %");
                break;
            case "Skiing":
                binding.weatherProp1HeadlineTv.setText("total snow: ");
                binding.weatherProp1Tv.setText(oceanData.getTotalSnowCM() + " cm");
                binding.weatherProp2HeadlineTv.setText("Chance of snow: ");
                binding.weatherProp2Tv.setText(oceanData.getChanceOfSnow() + " %");
                binding.weatherProp3HeadlineTv.setText("description: ");
                binding.weatherProp3Tv.setText(data.weatherDescription);
                binding.weatherProp4HeadlineTv.setText("Wind Direction: ");
                binding.weatherProp4Tv.setText(data.windDirection);
                binding.weatherProp5HeadlineTv.setText("cloud cover: ");
                binding.weatherProp5Tv.setText(oceanData.getCloudCover() + " %");

                break;
            case "Kiting":
                binding.weatherProp1HeadlineTv.setText("Wind Speed: ");
                binding.weatherProp1Tv.setText(data.windSpeed+" meter/sec");
                binding.weatherProp2HeadlineTv.setText("Wind Direction: ");
                binding.weatherProp2Tv.setText(data.windDirection);
                binding.weatherProp3HeadlineTv.setText("description: ");
                binding.weatherProp3Tv.setText(data.weatherDescription);
                binding.weatherProp4HeadlineTv.setText("rain chance today: ");
                binding.weatherProp4Tv.setText(oceanData.getChanceOfRain() + "%");
                binding.weatherProp5HeadlineTv.setText("uvIndex: ");
                binding.weatherProp5Tv.setText(oceanData.getUvIndex() + " uv");
                break;
            case "Abseiling":
                binding.weatherProp1HeadlineTv.setText("Wind Speed: ");
                binding.weatherProp1Tv.setText(data.windSpeed+" meter/sec");
                binding.weatherProp2HeadlineTv.setText("rain chance today: ");
                binding.weatherProp2Tv.setText(oceanData.getChanceOfRain() + " %");
                binding.weatherProp3HeadlineTv.setText("uvIndex: ");
                binding.weatherProp3Tv.setText(oceanData.getUvIndex() + " uv");
                binding.weatherProp4HeadlineTv.setText("Chance of thunder: ");
                binding.weatherProp4Tv.setText(oceanData.getChanceOfThunder() + " %");
                binding.weatherProp5HeadlineTv.setText("Visibility: ");
                binding.weatherProp5Tv.setText(oceanData.getVisibility() + " %");
                break;
            case "Basketball":
                binding.weatherProp1HeadlineTv.setText("Feels like: ");
                binding.weatherProp1Tv.setText(oceanData.getFeelsLikeC() + " °C");
                binding.weatherProp2HeadlineTv.setText("Humidity: ");
                binding.weatherProp2Tv.setText(oceanData.getHumidity() + " %");
                binding.weatherProp3HeadlineTv.setText("description: ");
                binding.weatherProp3Tv.setText(data.weatherDescription);
                binding.weatherProp4HeadlineTv.setText("uvIndex: ");
                binding.weatherProp4Tv.setText(oceanData.getUvIndex() + " uv");
                binding.weatherProp5HeadlineTv.setText("Wind Speed: ");
                binding.weatherProp5Tv.setText(data.windSpeed+" meter/sec");
                break;
            case "Football":
                binding.weatherProp1HeadlineTv.setText("total snow: ");
                binding.weatherProp1Tv.setText(oceanData.getTotalSnowCM() + " cm");
                binding.weatherProp2HeadlineTv.setText("Humidity: ");
                binding.weatherProp2Tv.setText(oceanData.getHumidity() + " %");
                binding.weatherProp3HeadlineTv.setText("description: ");
                binding.weatherProp3Tv.setText(data.weatherDescription);
                binding.weatherProp4HeadlineTv.setText("uvIndex: ");
                binding.weatherProp4Tv.setText(oceanData.getUvIndex() + " uv");
                binding.weatherProp5HeadlineTv.setText("rain chance today: ");
                binding.weatherProp5Tv.setText(oceanData.getChanceOfRain() + "%");
                break;
            case "Outside walking":
                binding.weatherProp1HeadlineTv.setText("Feels like: ");
                binding.weatherProp1Tv.setText(oceanData.getFeelsLikeC() + " °C");
                binding.weatherProp2HeadlineTv.setText("Humidity: ");
                binding.weatherProp2Tv.setText(oceanData.getHumidity() + " %");
                binding.weatherProp3HeadlineTv.setText("description: ");
                binding.weatherProp3Tv.setText(data.weatherDescription);
                binding.weatherProp4HeadlineTv.setText("uvIndex: ");
                binding.weatherProp4Tv.setText(oceanData.getUvIndex() + " uv");
                binding.weatherProp5HeadlineTv.setText("Visibility: ");
                binding.weatherProp5Tv.setText(oceanData.getVisibility() + " % ");
                break;

            case "Badminton":
                binding.weatherProp1HeadlineTv.setText("Wind Speed: ");
                binding.weatherProp1Tv.setText(data.windSpeed+" meter/sec");
                binding.weatherProp2HeadlineTv.setText("Wind Direction: ");
                binding.weatherProp2Tv.setText(data.windDirection);
                binding.weatherProp3HeadlineTv.setText("description: ");
                binding.weatherProp3Tv.setText(data.weatherDescription);
                binding.weatherProp4HeadlineTv.setText("rain chance today: ");
                binding.weatherProp4Tv.setText(oceanData.getChanceOfRain() + "%");
                binding.weatherProp5HeadlineTv.setText("Feels like: ");
                binding.weatherProp5Tv.setText(oceanData.getFeelsLikeC() + " °C");
                break;

            case "Biking":
                binding.weatherProp1HeadlineTv.setText("Wind Speed: ");
                binding.weatherProp1Tv.setText(data.windSpeed+" meter/sec");
                binding.weatherProp2HeadlineTv.setText("Humidity: ");
                binding.weatherProp2Tv.setText(oceanData.getHumidity() + " %");
                binding.weatherProp3HeadlineTv.setText("Chance of fog: ");
                binding.weatherProp3Tv.setText(oceanData.getChanceoffog() + " %");
                binding.weatherProp4HeadlineTv.setText("rain chance today: ");
                binding.weatherProp4Tv.setText(oceanData.getChanceOfRain() + "%");
                binding.weatherProp5HeadlineTv.setText("Visibility: ");
                binding.weatherProp5Tv.setText(oceanData.getVisibility() + " % ");
                break;
            case "Yoga":
                binding.weatherProp1HeadlineTv.setText("Feels like: ");
                binding.weatherProp1Tv.setText(oceanData.getFeelsLikeC() + " °C");
                binding.weatherProp2HeadlineTv.setText("Humidity: ");
                binding.weatherProp2Tv.setText(oceanData.getHumidity() + " %");
                binding.weatherProp3HeadlineTv.setText("description: ");
                binding.weatherProp3Tv.setText(data.weatherDescription);
                binding.weatherProp4HeadlineTv.setText("rain chance today: ");
                binding.weatherProp4Tv.setText(oceanData.getChanceOfRain() + "%");
                binding.weatherProp5HeadlineTv.setText("Wind Speed: ");
                binding.weatherProp5Tv.setText(data.windSpeed+" meter/sec");
                break;
            case "Tennis":
                binding.weatherProp1HeadlineTv.setText("Wind Speed: ");
                binding.weatherProp1Tv.setText(data.windSpeed+" meter/sec");
                binding.weatherProp2HeadlineTv.setText("Wind Direction: ");
                binding.weatherProp2Tv.setText(data.windDirection);
                binding.weatherProp3HeadlineTv.setText("description: ");
                binding.weatherProp3Tv.setText(data.weatherDescription);
                binding.weatherProp4HeadlineTv.setText("rain chance today: ");
                binding.weatherProp4Tv.setText(oceanData.getChanceOfRain() + "%");
                binding.weatherProp5HeadlineTv.setText("Feels like: ");
                binding.weatherProp5Tv.setText(oceanData.getFeelsLikeC() + " °C");
                break;
            default:
                // do something for any other activity
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        binding.weatherProgressBar.setVisibility(View.VISIBLE);
        reloadData(email);

    }

    public void reloadData(String email)
    {
        Model.instance().getAllUsers((userList)->{
            user=Model.instance().getUserByEmail(userList,email);
            data=Weather.getWeatherDataForCity(user.getCity());
            if(data!=null){
                // If weather data is found, update the UI
                updateUI(data);
            }
            else{
                // If weather data is not found, show a Toast message
                Toast.makeText(getActivity().getApplicationContext(), "Invalid city ", Toast.LENGTH_LONG).show();
            }
            // Hide the progress bar after data has been loaded
            binding.weatherProgressBar.setVisibility(View.GONE);

        });

    }



}