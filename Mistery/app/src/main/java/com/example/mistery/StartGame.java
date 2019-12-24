package com.example.mistery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mistery.fragments.ChatFragment;
import com.example.mistery.util.TextViewUtils;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class StartGame extends AppCompatActivity {
    private TextView storyTv;
    private ImageView imageView;
    private LinearLayout layout;

    private TextViewUtils textViewUtils;

    private MyDB myDB;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);   //隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.game_start);

        myDB = new MyDB(this);
        db = myDB.getWritableDatabase();

        imageView = findViewById(R.id.live);
        imageView.setVisibility(View.GONE);

        storyTv = findViewById(R.id.story_tv);
        layout = findViewById(R.id.start_layout);
        textViewUtils = new TextViewUtils(storyTv,getResources().getString(R.string.story),100);

        imageView.setVisibility(View.VISIBLE);
        AnimationDrawable animationDrawable = (AnimationDrawable)imageView.getBackground();
        animationDrawable.start();

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartGame.this,MainView.class);
                startActivity(intent);

                SharedPreferences sharedPreferences = getSharedPreferences("is_first_in_data",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isFirstIn",false);
                editor.commit();

                finish();
            }
        });

    }

    public void DbAdd(){
        ContentValues cv = new ContentValues();
        cv.put("name","李惠");
        cv.put("latest"," ");
        cv.put("count",0);
        db.insert("person",null,cv);
        cv.clear();
        cv.put("count","1");
        cv.put("status",0);
        cv.put("many",0);
        db.insert("status",null,cv);
        cv.clear();
        cv.put("count","2");
        cv.put("status",0);
        cv.put("many",0);
        db.insert("status",null,cv);
        cv.clear();
        cv.put("count","3");
        cv.put("status",0);
        cv.put("many",0);
        db.insert("status",null,cv);
        cv.clear();
        cv.put("count","4");
        cv.put("status",0);
        cv.put("many",0);
        db.insert("status",null,cv);
        cv.clear();
        cv.put("count","music");
        cv.put("status","1");
        cv.put("many",0);
        db.insert("status",null,cv);
    }

    @Override
    public void onStart(){
        super.onStart();
        DbAdd();
    }
}
