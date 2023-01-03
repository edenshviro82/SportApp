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

public class OceanWeather {

    int chanceOfRain,chanceOfSnow,chanceOfThunder;
    private double waveHeight;
    private double waterTemperature;
    private int dangerLevel;

    public static OceanWeather getWeatherDataForCity(String city) {
            // Replace this with the API key for your marine weather API
            String apiKey = "6876bcc883b64d3f8f5130600230301";

            // Create the request URL
            //String url = "https://api.marineweatherapi.com/v2/weather?q=" + city + "&api_key=" + apiKey;
       // http://api.worldweatheronline.com/premium/v1/marine.ashx?key=6876bcc883b64d3f8f5130600230301&q=London&format=json
//http://api.worldweatheronline.com/premium/v1/weather.ashx?key=6876bcc883b64d3f8f5130600230301&q=Eilat&format=json&num_of_days=5
            String url = "http://api.worldweatheronline.com/premium/v1/weather.ashx?key="+apiKey+"&q="+city+"&format=json&num_of_days=5";

            // Create a new OkHttpClient
            OkHttpClient client = new OkHttpClient();

            // Create a new request using the GET method
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            // Use an Executor to make the API call in the background
            Executor executor = Executors.newSingleThreadExecutor();
            Future<OceanWeather> future = ((ExecutorService) executor).submit(new Callable<OceanWeather>() {
                @Override
                public OceanWeather call() throws Exception {
                    try {
                        // Make the API call using the client and get the response
                        Response response = client.newCall(request).execute();
                        Log.d("json", "response code()"+response.code());

                        // Check the status code of the response to make sure the request was successful
                        if (response.code() == 200) {
                            // Get the ocean weather data from the API response
                            String responseBody = response.body().string();
                            JSONObject json = new JSONObject(responseBody);


                            Log.d("json", "ocean json************"+String.valueOf(json));
                            //data.weather[0].hourly[0].chanceofrain
                            OceanWeather data = new OceanWeather();
                            data.chanceOfRain = json.getJSONObject("data").getJSONArray("weather").getJSONObject(0).getJSONArray("hourly").getJSONObject(0).getInt("chanceofrain");


                            return data;
                        } else {
                            // An error occurred, return null
                            Log.d("json", "eror1");
                            return null;
                        }
                    } catch (IOException | JSONException e) {
                        // An error occurred, return null
                        Log.d("json", e+"");
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

    public double getWaveHeight() {
        return waveHeight;
    }

    public void setWaveHeight(double waveHeight) {
        this.waveHeight = waveHeight;
    }

    public double getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(double waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = dangerLevel;
    }
}
