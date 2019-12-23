package com.example.mistery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDB extends SQLiteOpenHelper {

    public static String CREATE_PERSON = "create table person(" +
            "name text primary key," +
            "latest TEXT NOT NULL," +
            "count integer NOT NULL)";

    public static String CREATE_MSG = "create table msg(" +
            "id integer primary key AUTOINCREMENT," +
            "user_name TEXT NOT NULL," +
            "type integer," +
            "content TEXT NOT NULL)";

    public static String CREATE_THREAD = "create table thread(" +
            "id integer primary key AUTOINCREMENT," +
            "t_title TEXT NOT NULL," +
            "t_item TEXT NOT NULL)";

    public static String CREATE_STATUS = "create table status(" +
            "count text NOT NULL," +
            "status integer NOT NULL," +
            "many integer NOT NULL)";

    private Context context;


    public MyDB(@Nullable Context context) {
        super(context,"sql.db",null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSON);
        db.execSQL(CREATE_MSG);
        db.execSQL(CREATE_THREAD);
        db.execSQL(CREATE_STATUS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
