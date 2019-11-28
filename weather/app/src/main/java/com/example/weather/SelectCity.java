package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


public class SelectCity extends AppCompatActivity {
    public static String city_code = "";
    private EditText cityName;
    private Button search_btn;
    private Map<String,String> map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);   //隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.search_layout);
        initData();
        initView();
    }

    private void initData() {
        try {
            InputStream is = getAssets().open("city_code.xml");
            map = new XMLParser().getMap(is);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        cityName = findViewById(R.id.city_name);
        search_btn = findViewById(R.id.query_cityID);

        search_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String city = cityName.getText().toString().trim();
                String code = map.get(city);
                if (code!=null){
                    city_code = code;
                    SelectCity.this.setResult(100);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"输入错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
