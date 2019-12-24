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
    private TextView ending;

    private Animation hideAnimation;
    private Animation showAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);   //隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_end_game);

        isTrue = getIntent().getBooleanExtra("isTrue",false);

        ending = findViewById(R.id.ending);
        if (isTrue)
            ending.setText("恭喜你，指认成功！");
        else
            ending.setText("很遗憾，指认失败。");

        setShowAnimation();
        setHideAnimation();

        text = findViewById(R.id.end_text);
        text.setMovementMethod(ScrollingMovementMethod.getInstance());

        TranslateAnimation animation = (TranslateAnimation) AnimationUtils.loadAnimation(EndGame.this,R.anim.translate);
        text.startAnimation(animation);
    }

    private void setHideAnimation(){
        if (hideAnimation!=null)
            hideAnimation.cancel();

        hideAnimation = new AlphaAnimation(1.0f,0.0f);
        hideAnimation.setDuration(3000);
        hideAnimation.setFillAfter(true);
        hideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ending.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ending.startAnimation(hideAnimation);
    }

    private void setShowAnimation(){
        if (showAnimation!=null)
            showAnimation.cancel();

        showAnimation = new AlphaAnimation(0.0f,1.0f);
        showAnimation.setDuration(3500);
        showAnimation.setFillAfter(true);
        showAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ending.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ending.startAnimation(showAnimation);
    }


}
