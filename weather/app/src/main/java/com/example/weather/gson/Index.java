package com.example.weather.gson;

import com.google.gson.annotations.SerializedName;

public class Index {
    public String title;
    public String level;
    @SerializedName("desc")
    public String tips;
}
