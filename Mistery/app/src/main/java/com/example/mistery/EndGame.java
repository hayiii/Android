package com.example.mistery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mistery.myview.AutoVerticalScrollTextView;
import com.example.mistery.util.TextViewUtils;

import java.util.ArrayList;
import java.util.List;

public class EndGame extends AppCompatActivity {
    private boolean isTrue;

    private TextView text;
    private LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);   //隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_end_game);

        isTrue = getIntent().getBooleanExtra("isTrue",false);

        text = findViewById(R.id.end_text);
        text.setMovementMethod(ScrollingMovementMethod.getInstance());

        TranslateAnimation animation = (TranslateAnimation) AnimationUtils.loadAnimation(EndGame.this,R.anim.translate);
        text.startAnimation(animation);
    }



}
