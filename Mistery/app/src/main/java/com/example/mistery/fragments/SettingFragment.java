package com.example.mistery.fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mistery.MainActivity;
import com.example.mistery.MusicServer;
import com.example.mistery.MyDB;
import com.example.mistery.R;
import com.example.mistery.Splash;

import java.io.File;


public class SettingFragment extends Fragment {
    private String mFrom;

    private TextView reset;
    private TextView suggestion;
    private TextView tips;

    private ImageView music;

    private MyDB myDB;
    private SQLiteDatabase dbReader;
    private SQLiteDatabase dbWriter;

    private Intent intent;

    private boolean status;

    public static SettingFragment newInstance(String from) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString("from",from);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = new MyDB(getContext());
        dbReader = myDB.getReadableDatabase();
        dbWriter = myDB.getWritableDatabase();

        if (getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        suggestion = view.findViewById(R.id.suggestions);
        tips = view.findViewById(R.id.tips);
        reset = view.findViewById(R.id.reset);
        music = view.findViewById(R.id.music);

        intent = new Intent(getActivity(), MusicServer.class);

        initData();


        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status){
                    status = false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        music.setBackground(getActivity().getResources().getDrawable(R.drawable.ifalse));
                        getActivity().stopService(intent);
                    }
                }else {
                    status = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        music.setBackground(getActivity().getResources().getDrawable(R.drawable.select));
                        getActivity().startService(intent);
                    }
                }
                update();
            }
        });

        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity()).setTitle("TIPS").setMessage(getString(R.string.suggestions)).create().show();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteFile(new File("data/data/"+getActivity().getPackageName()));
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("is_first_in_data",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isFirstIn",true);
                editor.commit();
                Intent intent = new Intent(getActivity(), Splash.class);
                startActivity(intent);
            }
        });
    }

    private void initData(){
        Cursor cursor = dbReader.rawQuery("SELECT * FROM status WHERE count=?",new String[]{"music"});
        int m = 1;
        if (cursor.moveToFirst())
            m = cursor.getInt(cursor.getColumnIndex("status"));
        if (m==0){
            status = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                music.setBackground(getActivity().getResources().getDrawable(R.drawable.ifalse));
            }
        }else {
            status = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                music.setBackground(getActivity().getResources().getDrawable(R.drawable.select));
            }
        }
    }

    private void update(){
        Cursor cursor = dbReader.rawQuery("SELECT * FROM status WHERE count=?",new String[]{"music"});
        cursor.moveToFirst();
        if (status)
            dbWriter.execSQL("UPDATE status SET status=? WHERE count = ?",new String[]{0+"","music"});
        else
            dbWriter.execSQL("UPDATE status SET status=? WHERE count = ?",new String[]{1+"","music"});
    }

    public static void DeleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    DeleteFile(f);
                }
                file.delete();
            }
        }
    }

}
