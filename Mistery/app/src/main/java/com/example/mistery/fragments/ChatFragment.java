package com.example.mistery.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mistery.MyDB;
import com.example.mistery.R;
import com.example.mistery.TalkWith;
import com.example.mistery.msg.ConverAdapter;
import com.example.mistery.msg.Person;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {
    private String mFrom;

    private SQLiteDatabase db;
    private MyDB myDB;

    private Person person = new Person();

    private List<Person> personList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ConverAdapter converAdapter;

    public static ChatFragment newInstance(String from) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString("from",from);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myDB = new MyDB(getActivity());
        db = myDB.getReadableDatabase();

        initData();

        recyclerView = view.findViewById(R.id.conversation_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        converAdapter = new ConverAdapter(personList);
        recyclerView.setAdapter(converAdapter);
        converAdapter.notifyDataSetChanged();

        converAdapter.setOnItemClickListener(new ConverAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                person = personList.get(position);
                Intent intent = new Intent(getActivity(),TalkWith.class);
                intent.putExtra("content",personList.get(position).getContent());
                intent.putExtra("count",personList.get(position).getCount());
                startActivity(intent);
            }
        });
   }


   private void initData(){
        personList = new ArrayList<>();
        Cursor cursor = db.query("person",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            Person person = new Person();
            person.setContent(cursor.getString(cursor.getColumnIndex("name")));
            person.setLatest(cursor.getString(cursor.getColumnIndex("latest")));
            person.setCount(cursor.getInt(cursor.getColumnIndex("count")));
            switch (cursor.getString(cursor.getColumnIndex("name"))){
                case "李惠":
                    person.setTx(getResources().getDrawable(R.drawable.gurl1));
                    break;
                case "云美":
                        person.setTx(getResources().getDrawable(R.drawable.gurl4));
                        break;
                case "谷梁野":
                        person.setTx(getResources().getDrawable(R.drawable.man2));
                        break;
                case "丁蕊":
                    person.setTx(getResources().getDrawable(R.drawable.dr));
                    break;
                case "张菊":
                    person.setTx(getResources().getDrawable(R.drawable.gurl2));
                    break;
                case "田尧":
                    person.setTx(getResources().getDrawable(R.drawable.ty));
                    break;
                case "王山":
                    person.setTx(getResources().getDrawable(R.drawable.ws));
                    break;
                case "黎岛":
                    person.setTx(getResources().getDrawable(R.drawable.ld));
                    break;
                }
             personList.add(person);
        }
        cursor.close();
   }

   @Override
    public void onResume(){
        super.onResume();
        initData();
   }

}
