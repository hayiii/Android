package com.example.weather.gson;

import com.google.gson.annotations.SerializedName;

public class Hourly {
    public String day;
    @SerializedName("wea")
    public String weather;
    public String tem;
}
