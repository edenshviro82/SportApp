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

    private int chanceOfRain;
    private int chanceOfSnow;
    private int chanceOfThunder;
    private int cloudCover;
    private double totalSnowCM;
    private int feelsLikeC;
    private int visibility;
    private double sunHour;
    private int uvIndex;
    private int humidity;
    private int chanceoffog;
    private double waveHeight;
    private double waterTemperature;
    private int dangerLevel;

    public static OceanWeather getWeatherDataForCity(String city) {
        // Replace this with the API key for your marine weather API
        String apiKey = "b43643dbba7c41b6bdd153307230102";

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
                        data.chanceOfSnow = json.getJSONObject("data").getJSONArray("weather").getJSONObject(0).getJSONArray("hourly").getJSONObject(0).getInt("chanceofsnow");
                        data.chanceOfThunder = json.getJSONObject("data").getJSONArray("weather").getJSONObject(0).getJSONArray("hourly").getJSONObject(0).getInt("chanceofthunder");
                        data.cloudCover = json.getJSONObject("data").getJSONArray("weather").getJSONObject(0).getJSONArray("hourly").getJSONObject(0).getInt("cloudcover");
                        data.totalSnowCM =  json.getJSONObject("data").getJSONArray("weather").getJSONObject(0).getDouble("totalSnow_cm");
                        data.feelsLikeC = json.getJSONObject("data").getJSONArray("weather").getJSONObject(0).getJSONArray("hourly").getJSONObject(2).getInt("FeelsLikeC");
                        data.visibility = json.getJSONObject("data").getJSONArray("current_condition").getJSONObject(0).getInt("visibility");
                        data.humidity = json.getJSONObject("data").getJSONArray("current_condition").getJSONObject(0).getInt("humidity");
                        data.chanceoffog = json.getJSONObject("data").getJSONArray("weather").getJSONObject(0).getJSONArray("hourly").getJSONObject(0).getInt("chanceoffog");
                        data.sunHour = json.getJSONObject("data").getJSONArray("weather").getJSONObject(0).getInt("sunHour");
                        data.uvIndex = json.getJSONObject("data").getJSONArray("weather").getJSONObject(0).getInt("uvIndex");


                        Log.d("json", "total snow: " + data.totalSnowCM);


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

    public int getChanceOfRain() {
        return chanceOfRain;
    }

    public void setChanceOfRain(int chanceOfRain) {
        this.chanceOfRain = chanceOfRain;
    }

    public int getChanceOfSnow() {
        return chanceOfSnow;
    }

    public void setChanceOfSnow(int chanceOfSnow) {
        this.chanceOfSnow = chanceOfSnow;
    }

    public int getChanceOfThunder() {
        return chanceOfThunder;
    }

    public void setChanceOfThunder(int chanceOfThunder) {
        this.chanceOfThunder = chanceOfThunder;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }

    public double getTotalSnowCM() {
        return totalSnowCM;
    }

    public void setTotalSnowCM(int totalSnowCM) {
        this.totalSnowCM = totalSnowCM;
    }

    public int getFeelsLikeC() {
        return feelsLikeC;
    }

    public void setFeelsLikeC(int feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }


    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getChanceoffog() {
        return chanceoffog;
    }

    public void setChanceoffog(int chanceoffog) {
        this.chanceoffog = chanceoffog;
    }

    public double getSunHour() {
        return sunHour;
    }

    public void setSunHour(double sunHour) {
        this.sunHour = sunHour;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }


}