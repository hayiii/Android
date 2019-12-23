package com.example.mistery.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mistery.EndGame;
import com.example.mistery.MyDB;
import com.example.mistery.R;
import com.example.mistery.SearchEvidence;


public class SearchFragment extends Fragment {
    private String mFrom;

    private MyDB myDB;
    private SQLiteDatabase dbReader;
    private SQLiteDatabase dbWriter;

    private ImageView first;
    private ImageView second;
    private ImageView third;
    private ImageView select;


    public static SearchFragment newInstance(String from) {
        SearchFragment fragment = new SearchFragment();
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        first = view.findViewById(R.id.first);
        second = view.findViewById(R.id.second);
        third = view.findViewById(R.id.third);
        select = view.findViewById(R.id.to_select);

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchEvidence.class);
                intent.putExtra("index",1);
                startActivity(intent);
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchEvidence.class);
                intent.putExtra("index",2);
                startActivity(intent);
            }
        });

        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchEvidence.class);
                intent.putExtra("index",3);
                startActivity(intent);
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String[] items = new String[]{"李惠","谷梁野","云美","丁蕊"};
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("请指出你认为是凶手的人")
                        .setIcon(getResources().getDrawable(R.drawable.search_after))
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (items[which]){
                                    case "谷梁野":
                                        Intent intent = new Intent(getActivity(), EndGame.class);
                                        intent.putExtra("isTrue",true);
                                        startActivity(intent);
                                        break;
                                        default:
                                            Intent intent1 = new Intent(getActivity(), EndGame.class);
                                            intent1.putExtra("isTrue",false);
                                            startActivity(intent1);
                                            break;
                                }
                            }
                        }).create();
                alertDialog.show();
            }
        });


    }

    public void initData(){
        Cursor cursor = dbReader.query("status",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String item = cursor.getString(cursor.getColumnIndex("count"));
            int status = cursor.getInt(cursor.getColumnIndex("status"));
            switch (item){
                case "1":
                    if (status == 0) {
                        first.setImageDrawable(getResources().getDrawable(R.drawable.first_lock));
                        first.setClickable(false);
                    }
                    else if (status == 1) {
                        first.setImageDrawable(getResources().getDrawable(R.drawable.first));
                        first.setClickable(true);
                    }
                    else {
                        first.setImageDrawable(getResources().getDrawable(R.drawable.first_lock));
                        first.setClickable(false);
                    }
                    break;
                case "2":
                    if (status == 0) {
                        second.setImageDrawable(getResources().getDrawable(R.drawable.second_before));
                        second.setClickable(false);
                    }
                    else if (status == 1) {
                        second.setImageDrawable(getResources().getDrawable(R.drawable.second));
                        second.setClickable(true);
                    }
                    else {
                        second.setImageDrawable(getResources().getDrawable(R.drawable.second_before));
                        second.setClickable(false);
                    }
                    break;
                case "3":
                    if (status == 0) {
                        third.setImageDrawable(getResources().getDrawable(R.drawable.third_before));
                        third.setClickable(false);
                    }
                    else if (status == 1) {
                        third.setImageDrawable(getResources().getDrawable(R.drawable.third));
                        third.setClickable(true);
                    }
                    else {
                        third.setImageDrawable(getResources().getDrawable(R.drawable.third_before));
                        third.setClickable(false);
                    }
                    break;
                case "4":
                    if (status == 0) {
                        select.setImageDrawable(getResources().getDrawable(R.drawable.point));
                        select.setClickable(true);
                    }
                    else {
                        select.setImageDrawable(getResources().getDrawable(R.drawable.point));
                        select.setClickable(true);
                    }
                    break;
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        initData();
    }
}
