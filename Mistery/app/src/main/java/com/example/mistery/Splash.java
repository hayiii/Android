package com.example.mistery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mistery.util.TextViewUtils;


import static android.os.SystemClock.sleep;

public class Splash extends AppCompatActivity {
   private ImageView iv1;
   private ImageView iv2;
   private ImageView iv3;

    private Boolean isFirstIn = false;

   private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);   //隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_splash);

        final SharedPreferences sharedPreferences = this.getSharedPreferences("is_first_in_data",MODE_PRIVATE);
        isFirstIn = sharedPreferences.getBoolean("isFirstIn",true);

        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        tv = findViewById(R.id.text);

        iv1.setImageResource(R.drawable.xc1);

        sleep(1000);

        iv2.setImageResource(R.drawable.xc2);

        sleep(1000);

        iv3.setImageResource(R.drawable.xc3);

        new Thread(){
            @Override
            public void run(){
                try {
                    sleep(2000);
                    new TextViewUtils(tv,"杀人不是目的，是手段。",200);

                    sleep(4000);//休眠4秒
                    if (isFirstIn){
                        Intent intent = new Intent(Splash.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(Splash.this,MainView.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
