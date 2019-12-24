package com.example.mistery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.mistery.util.FontsUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView startGame;

    private Boolean isFirstIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);   //隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences = this.getSharedPreferences("is_first_in_data",MODE_PRIVATE);
        isFirstIn = sharedPreferences.getBoolean("isFirstIn",true);

        startGame = findViewById(R.id.start_game);

        if (!isFirstIn){
            startGame.setText("继续游戏");
        } else
            startGame.setText("开始游戏");

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstIn){
                    Intent intent = new Intent(MainActivity.this,StartGame.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(MainActivity.this,MainView.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}
