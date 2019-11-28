package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.weather.gson.Data;
import com.example.weather.gson.Hourly;
import com.example.weather.gson.Index;
import com.example.weather.gson.WeatherInfo;
import com.example.weather.util.BDLocationUtils;
import com.example.weather.util.HttpUtil;
import com.example.weather.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    //title.xml
    private ImageButton changeCityBtn;
    private TextView current_city;

    //weather_info.xml
    private TextView degree;
    private TextView weatherStatus;
    private TextView maxDegree;
    private TextView minDegree;
    private TextView airLevel;
    private TextView updatetime;


    //forecast.xml
    private LinearLayout forecastLayout;


    //activity_main.xml
    private LinearLayout hourlyForecastLayout;

    //index.xml
   // private TextView wind_direction;
    private TextView wind_speed;
    private TextView zwxTips;
    private TextView zwxLevel;
    private TextView clothLevel;
    private TextView clothTips;
    private TextView airpolution;
    public TextView airTips;

    //private Handler handler;

    private  String code = "101110101";
    private BDLocationUtils bdLocationUtils = null;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bdLocationUtils = new BDLocationUtils(this);
        bdLocationUtils.doLocation();
        code = bdLocationUtils.City;

        weatherStatus = findViewById(R.id.weather_status);
        airLevel = findViewById(R.id.air_level);
        maxDegree = findViewById(R.id.max_degree);
        minDegree = findViewById(R.id.min_degree);

        degree = findViewById(R.id.degree);
        wind_speed = findViewById(R.id.wind_speed);

        requestWeather(code);
        initView();


        changeCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SelectCity.class);
                startActivityForResult(intent,100);
            }
        });

    }

    private void initView(){
        changeCityBtn = findViewById(R.id.change_city_btn);
        current_city = findViewById(R.id.current_city);


        forecastLayout = findViewById(R.id.forecast_layout);

        hourlyForecastLayout = findViewById(R.id.hourly_forecast);

        //wind_direction = findViewById(R.id.wind_dire);

        zwxLevel = findViewById(R.id.zwx_level);
        zwxTips = findViewById(R.id.zwx_tips);
        clothLevel = findViewById(R.id.cloth_level);
        clothTips = findViewById(R.id.cloth_tips);
        airpolution = findViewById(R.id.air_polution);
        airTips = findViewById(R.id.air_tips);
    }

    //根据天气id请求城市天气信息
    public void requestWeather(final String weatherId) {
        String weatherUrl = "https://www.tianqiapi.com/api/?version=v1&cityid="+weatherId+"&appid=38396834&appsecret=Kb68tLAp";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseText = response.body().string();
                final WeatherInfo weatherInfo = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeatherInfo(weatherInfo);
                    }
                });
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showWeatherInfo(WeatherInfo weatherInfo) {
        current_city.setText(weatherInfo.city);
        updatetime = findViewById(R.id.update_time);
        updatetime.setText(weatherInfo.update_time);
        int i=0;
        forecastLayout.removeAllViews();
        hourlyForecastLayout.removeAllViews();
        for (Data date:weatherInfo.data){
            if (i==0){
                airLevel.setText(date.air_level);
                maxDegree.setText(date.max_tem);
                minDegree.setText(date.min_tem);
                degree.setText(date.current_tem);
                weatherStatus.setText(date.weather_status);
                wind_speed.setText(date.win_speed);
                for (Hourly hourly:date.hours){
                    View view = LayoutInflater.from(this).inflate(R.layout.hourly_forecast_item,hourlyForecastLayout,false);
                    TextView hourly_day = view.findViewById(R.id.h_day);
                    TextView statusIv = view.findViewById(R.id.status_iv);
                    TextView forecastDegree = view.findViewById(R.id.forecast_degree);
                    hourly_day.setText(hourly.day);
                    statusIv.setText(hourly.weather);
                    forecastDegree.setText(hourly.tem);
                    hourlyForecastLayout.addView(view);
                }
                for (Index index:date.index){
                    switch (index.title){
                        case "紫外线指数":
                            zwxLevel.setText(index.level);
                            zwxTips.setText(index.tips);
                            break;
                        case "穿衣指数":
                            clothLevel.setText(index.level);
                            clothTips.setText(index.tips);
                            break;
                        case "空气污染扩散指数":
                            airpolution.setText(index.level);
                            airTips.setText(index.tips);
                            break;
                            default:break;
                    }
                }
                i++;
            } else{
                View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
                TextView day = view.findViewById(R.id.date);
                TextView statusTv = view.findViewById(R.id.status_tv);
                TextView minDegreeTv = view.findViewById(R.id.min_degree_tv);
                TextView maxDegreeTv = view.findViewById(R.id.max_degree_tv);
                day.setText(date.day);
                statusTv.setText(date.weather_status);
                minDegreeTv.setText(date.min_tem);
                maxDegreeTv.setText(date.max_tem);
                forecastLayout.addView(view);
                i++;
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 100){
            code = SelectCity.city_code;
            requestWeather(code);
        }
    }
}
