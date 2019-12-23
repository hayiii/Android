package com.example.mistery.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mistery.MyDB;
import com.example.mistery.R;

import java.util.Arrays;
import java.util.List;


public class ThreadFragment extends Fragment {
    private String mFrom;

    public List<String> corpseList;
    public List<String> threadList;

    private LinearLayout corpseLayout;
    private LinearLayout threadLayout;
    private LinearLayout evidenceLayout;

    private Button stBt;
    private Button xcBt;
    private Button zjBt;

    private Activity activity;

    public MyDB myDB;
    public SQLiteDatabase dbReader;


    public static ThreadFragment newInstance(String from) {
        ThreadFragment fragment = new ThreadFragment();
        Bundle args = new Bundle();
        args.putString("from",from);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = new MyDB(getActivity());
        dbReader = myDB.getReadableDatabase();

        if (getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thread, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        activity = getActivity();

        corpseLayout = view.findViewById(R.id.corpse);
        threadLayout = view.findViewById(R.id.thread);
        evidenceLayout = view.findViewById(R.id.evidence);
        stBt = view.findViewById(R.id.st_bt);
        xcBt = view.findViewById(R.id.xc_bt);
        zjBt = view.findViewById(R.id.zj_bt);

        initData();
        initView();

        stBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
            }
        });

        xcBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    xcBt.setBackground(getResources().getDrawable(R.drawable.live_before));
                    stBt.setBackground(getResources().getDrawable(R.drawable.corpse_after));
                    zjBt.setBackground(getResources().getDrawable(R.drawable.evidence_after));
                }
                corpseLayout.setVisibility(View.GONE);
                threadLayout.setVisibility(View.VISIBLE);
                evidenceLayout.setVisibility(View.GONE);

                threadLayout.removeAllViews();
                for (int i=0;i<threadList.size();i++){
                    String []s = threadList.get(i).split(" ");
                    View view1 = LayoutInflater.from(activity).inflate(R.layout.thread_item,threadLayout,false);
                    TextView title = view1.findViewById(R.id.title);
                    title.setText(s[0]);
                    TextView tv = view1.findViewById(R.id.thread_tv);
                    tv.setText(s[1]);
                    threadLayout.addView(view1);
                }
            }
        });

        zjBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    xcBt.setBackground(getResources().getDrawable(R.drawable.live_after));
                    stBt.setBackground(getResources().getDrawable(R.drawable.corpse_after));
                    zjBt.setBackground(getResources().getDrawable(R.drawable.evidence_before));
                }
                showDb();
            }
        });
    }

    private void initView(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            stBt.setBackground(getResources().getDrawable(R.drawable.corpse_before));
            xcBt.setBackground(getResources().getDrawable(R.drawable.live_after));
            zjBt.setBackground(getResources().getDrawable(R.drawable.evidence_after));
        }
        corpseLayout.setVisibility(View.VISIBLE);
        threadLayout.setVisibility(View.GONE);
        evidenceLayout.setVisibility(View.GONE);

        corpseLayout.removeAllViews();
        for (int i=0;i<corpseList.size();i++){
            String []s = corpseList.get(i).split(" ");
            View view1 = LayoutInflater.from(activity).inflate(R.layout.thread_item,corpseLayout,false);
            TextView title = view1.findViewById(R.id.title);
            title.setText(s[0]);
            TextView tv = view1.findViewById(R.id.thread_tv);
            tv.setText(s[1]);
            corpseLayout.addView(view1);
        }
    }

    private void initData(){
        corpseList = Arrays.asList(getResources().getStringArray(R.array.corpse));
        threadList = Arrays.asList(getResources().getStringArray(R.array.threads));
    }

    private void showDb(){
        evidenceLayout.setVisibility(View.VISIBLE);
        corpseLayout.setVisibility(View.GONE);
        threadLayout.setVisibility(View.GONE);

        evidenceLayout.removeAllViews();
        Cursor cursor = dbReader.query("thread",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            View view = LayoutInflater.from(activity).inflate(R.layout.thread_item,evidenceLayout,false);
            TextView title = view.findViewById(R.id.title);
            TextView tv = view.findViewById(R.id.thread_tv);
            title.setText(cursor.getString(cursor.getColumnIndex("t_title")));
            tv.setText(cursor.getString(cursor.getColumnIndex("t_item")));
            evidenceLayout.addView(view);
        }
    }
}
