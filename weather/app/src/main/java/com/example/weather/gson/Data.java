package com.example.weather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    public String day;
    @SerializedName("wea")
    public String weather_status;
    public String air_level;
    @SerializedName("tem1")
    public String max_tem;
    @SerializedName("tem2")
    public String min_tem;
    @SerializedName("tem")
    public String current_tem;
    //@SerializedName("win")
    //public String win_dire;
    public String win_speed;

    public List<Hourly> hours;
    public List<Index> index;
}
