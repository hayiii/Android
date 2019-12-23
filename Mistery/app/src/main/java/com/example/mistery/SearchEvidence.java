package com.example.mistery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mistery.msg.ListAdapter;

public class SearchEvidence extends AppCompatActivity {
    private ListAdapter adapter;
    private RecyclerView recyclerView;
    private Button back;
    private TextView textView;
    private LinearLayout linearLayout;
    private LinearLayout evdLayout;

    private MyDB myDB;
    private SQLiteDatabase dbReader;
    private SQLiteDatabase dbWriter;

    public static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);   //隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_search_evidence);

        myDB = new MyDB(this);
        dbReader = myDB.getReadableDatabase();
        dbWriter = myDB.getWritableDatabase();

        String[] first = getResources().getStringArray(R.array.evidence_1);
        String[] second = getResources().getStringArray(R.array.evidence_2);
        String[] third = getResources().getStringArray(R.array.eviende_3);

        linearLayout = findViewById(R.id.evidence_layout);
        evdLayout = findViewById(R.id.show_evidence);
        linearLayout.setVisibility(View.GONE);
        evdLayout.setVisibility(View.VISIBLE);
        back = findViewById(R.id.back_to);
        recyclerView = findViewById(R.id.evidence_view);
        textView = findViewById(R.id.serach_tv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        final int m = getIntent().getIntExtra("index",0);
        initData(m);
        switch (m){
            case 1:
                adapter = new ListAdapter(this,first);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case 2:
                adapter = new ListAdapter(this,second);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case 3:
                adapter = new ListAdapter(this,third);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
                default:
                    finish();
                    break;
        }

        adapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                count++;
                if (count<=3) {
                    dbWriter.execSQL("UPDATE status SET many=? WHERE count = ?",
                            new String[]{count+"",m+""});
                    Toast.makeText(SearchEvidence.this, "你已经打开了" + count + "/3个证据！", Toast.LENGTH_LONG).show();
                }else {
                    dbWriter.execSQL("UPDATE status SET status=? WHERE count = ?",
                            new String[]{2+"",m+""});
                    count = 0;
                    textView.setText("已到达上限！");
                    evdLayout.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void initData(int m){
        Cursor cursor = dbReader.rawQuery("select * from status where count=?",new String[]{m+""});
        cursor.moveToFirst();
        count = cursor.getInt(cursor.getColumnIndex("many"));
    }
}
