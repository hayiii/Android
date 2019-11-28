package com.example.weather.util;

import android.app.Activity;

import com.example.weather.gson.WeatherInfo;
import com.google.gson.Gson;

public class Utility extends Activity {

    public static WeatherInfo handleWeatherResponse(String response){
        Gson gson = new Gson();
        WeatherInfo weatherInfo = gson.fromJson(response,WeatherInfo.class);

        return weatherInfo;
    }



}
