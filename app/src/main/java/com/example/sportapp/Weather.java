package com.example.sportapp;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Weather {


        String icon;
        String description,state;
        String windSpeed, windDirection,weatherDescription;
        double temperature;

    public static Weather getWeatherDataForCity(String city) {

            String apiKey="874e893c6aedb16c688cacfa4130b321";

            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

            // Create a new OkHttpClient
            OkHttpClient client = new OkHttpClient();

            // Create a new request using the GET method
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            // Use an Executor to make the API call in the background
            Executor executor = Executors.newSingleThreadExecutor();
            Future<Weather> future = ((ExecutorService) executor).submit(new Callable<Weather>() {
            @Override
            public Weather call() throws Exception {
                try {
                    // Make the API call using the client and get the response
                    Response response = client.newCall(request).execute();

                    // Check the status code of the response to make sure the request was successful
                    if (response.code() == 200) {
                        // Get the weather data from the API response
                        String responseBody = response.body().string();
                        JSONObject json = new JSONObject(responseBody);

                        Weather data = new Weather();
                        data.icon = updateIcon(json.getJSONArray("weather").getJSONObject(0).getInt("id"));
                        data.description = json.getJSONArray("weather").getJSONObject(0).getString("description");
                        data.temperature = json.getJSONObject("main").getDouble("temp")-273.27;
                        data.windSpeed=json.getJSONObject("wind").getString("speed"); //meter/sec
                        double windDegree = json.getJSONObject("wind").getDouble("deg");
                        data.windDirection= calcWindDirection(windDegree);
                        data.weatherDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");

                        Log.d("json", String.valueOf(json));

                        Log.d("TAG", data.temperature + "");
                        return data;
                    } else {
                        // An error occurred, return null
                        Log.d("ERROR", response.code() + "");
                        return null;
                    }
                } catch (IOException | JSONException e) {
                    // An error occurred, return null
                    Log.d("ERROR", e + "");
                    return null;
                }
            }
        });

        try {
            // Wait for the background task to complete and return the result
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            // An error occurred, return null
            return null;
        }
    }
    private static String calcWindDirection(double windDegree) {
        //calculate the wind direction according to the wind degree
        int windDegreeInt = (int) windDegree;
        if (windDegreeInt >= 348.75 || windDegreeInt < 11.25) {
            return "North";
        } else if (windDegreeInt >= 11.25 && windDegreeInt < 33.75) {
            return "North-Northeast";
        } else if (windDegreeInt >= 33.75 && windDegreeInt < 56.25) {
            return "Northeast";
        } else if (windDegreeInt >= 56.25 && windDegreeInt < 78.75) {
            return "East-Northeast";
        } else if (windDegreeInt >= 78.75 && windDegreeInt < 101.25) {
            return "East";
        } else if (windDegreeInt >= 101.25 && windDegreeInt < 123.75) {
            return "East-Southeast";
        } else if (windDegreeInt >= 123.75 && windDegreeInt < 146.25) {
            return "Southeast";
        } else if (windDegreeInt >= 146.25 && windDegreeInt < 168.75) {
            return "South-Southeast";
        } else if (windDegreeInt >= 168.75 && windDegreeInt < 191.25) {
            return "South";
        } else if (windDegreeInt >= 191.25 && windDegreeInt < 213.75) {
            return "South-Southwest";
        } else if (windDegreeInt >= 213.75 && windDegreeInt < 236.25) {
            return "Southwest";
        } else if (windDegreeInt >= 236.25 && windDegreeInt < 258.75) {
            return "West-Southwest";
        } else if (windDegreeInt >= 258.75 && windDegreeInt < 281.25) {
            return "West";
        } else if (windDegreeInt >= 281.25 && windDegreeInt < 303.75) {
            return "West-Northwest";
        } else if (windDegreeInt >= 303.75 && windDegreeInt < 326.25) {
            return "Northwest";
        } else if (windDegreeInt >= 326.25 && windDegreeInt < 348.75) {
            return "North-Northwest";
        } else {
            return "Unknown";
        }
    }

    public static String updateIcon(int state) {
        //update the icon according to the state
        if (state >= 200 && state <= 232) {
            return "thunderstorm1";
        } else if (state >= 300 && state <= 321) {
            return "lightrain";
        } else if (state >= 500 && state <= 531) {
            return "thunderstorm1";
        } else if (state >= 600 && state <= 622) {
            return "snow1";
        } else if (state >= 701 && state <= 781) {
            return "fog";
        } else if (state == 800) {
            return "sunny";
        } else if (state == 801 || state == 802) {
            return "cloudy";
        } else if (state == 803 || state == 804) {
            return "overcast";
        } else if (state >= 900 && state <= 906) {
            return "thunderstorm2";
        } else {
            return "launcher";
        }
    }

}
