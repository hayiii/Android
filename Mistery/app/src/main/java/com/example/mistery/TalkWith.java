package com.example.mistery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mistery.msg.Msg;
import com.example.mistery.msg.MsgAdapter;
import com.example.mistery.msg.MyAdapter;
import com.example.mistery.msg.MyAdapter.OnItemClickListener;
import com.example.mistery.myview.GradientShaderTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TalkWith extends AppCompatActivity implements View.OnTouchListener {

    private MyDB myDB;
    private SQLiteDatabase dbWriter;
    private SQLiteDatabase dbReader;
    private Cursor cursor;
    private Drawable drawable;

    private int count;

    private ArrayList<Msg> myList = new ArrayList<>();

    private List l = new ArrayList();

    private ImageView backBt;
    private TextView name;

    private RecyclerView selectRv;
    private Button selectBt;
    private ScrollView screen;

    private RecyclerView msgRecyclerView;

    private MsgAdapter msgAdapter;

    private List<String> lh = new ArrayList<>();
    private List<String> ym = new ArrayList<>();
    private List<String> gly = new ArrayList<>();
    private List<String> ws = new ArrayList<>();
    private List<String> tr = new ArrayList<>();
    private List<String> zj = new ArrayList<>();
    private List<String> dr = new ArrayList<>();
    private List<String> ld = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);   //隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_talkwith);

        backBt = findViewById(R.id.back_bt);
        msgRecyclerView = findViewById(R.id.msg_layout);
        selectBt = findViewById(R.id.select_bt);
        selectRv = findViewById(R.id.select_rv);
        screen = findViewById(R.id.screen);

        myDB = new MyDB(this);
        dbWriter = myDB.getWritableDatabase();
        dbReader = myDB.getReadableDatabase();
        name = findViewById(R.id.name);
        name.setText(getIntent().getStringExtra("content"));
        count = getIntent().getIntExtra("count",0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        msgRecyclerView.setLayoutManager(layoutManager);


        //get
        lh = Arrays.asList(getResources().getStringArray(R.array.lh1));
        ym = Arrays.asList(getResources().getStringArray(R.array.ym));
        gly = Arrays.asList(getResources().getStringArray(R.array.gly));
        ws = Arrays.asList(getResources().getStringArray(R.array.ws));
        tr = Arrays.asList(getResources().getStringArray(R.array.tr));
        zj = Arrays.asList(getResources().getStringArray(R.array.zj));
        dr = Arrays.asList(getResources().getStringArray(R.array.dr));
        ld = Arrays.asList(getResources().getStringArray(R.array.ld));

        screen.setOnTouchListener(this);
        msgRecyclerView.setOnTouchListener(this);

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
               // finish();
                Intent intent = new Intent(TalkWith.this,MainView.class);
                startActivity(intent);
            }
        });

       switch (name.getText().toString()){
            case "李惠":
                drawable = getResources().getDrawable(R.drawable.gurl1);
                talking(lh);
                break;
            case "云美":
                drawable = getResources().getDrawable(R.drawable.gurl4);
                talking(ym);
                break;
           case "谷梁野":
               drawable = getResources().getDrawable(R.drawable.man2);
               talking(gly);
               break;
           case "田尧":
               drawable = getResources().getDrawable(R.drawable.ty);
               talking(tr);
               break;
           case "张菊":
               drawable = getResources().getDrawable(R.drawable.gurl2);
               talking(zj);
               break;
           case "王山":
               drawable = getResources().getDrawable(R.drawable.ws);
               talking(ws);
               break;
           case "黎岛":
               drawable = getResources().getDrawable(R.drawable.ld);
               talking(ld);
               break;
           case "丁蕊":
               drawable = getResources().getDrawable(R.drawable.dr);
               talking(dr);
               break;
        }
    }


    public void talking(final List<String> list) {
        msgAdapter = new MsgAdapter(myList);
        msgRecyclerView.setAdapter(msgAdapter);

        initData();
        msgAdapter.notifyDataSetChanged();

        LinearLayoutManager mlayoutManager = new LinearLayoutManager(TalkWith.this);
        selectRv.setLayoutManager(mlayoutManager);

        selectBt.setVisibility(View.VISIBLE);
        selectRv.setVisibility(View.GONE);

        if (count==100){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                selectBt.setBackground(getResources().getDrawable(R.drawable.not_bt));
                selectBt.setEnabled(false);
            }
        }else {
            if (splitString(list.get(count))[0][1].equals("1")){
                addToView(myList, new Msg(splitString(list.get(count))[0][0], drawable, Msg.TYPE_RECEIVED), msgAdapter);
                Update();
            }
                selectBt.setEnabled(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    selectBt.setBackground(getResources().getDrawable(R.drawable.ok_bt));
                }
                selectBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        selectRv.setVisibility(View.VISIBLE);
                        selectBt.setVisibility(View.GONE);

                        final String[][] ss = splitString(list.get(count));
                        l.clear();
                        for (int i = 0; i < ss.length; i++) {
                            l.add(ss[i][0]);
                        }
                        final String[] strings = (String[]) l.toArray(new String[l.size()]);
                        MyAdapter myAdapter = new MyAdapter(strings);
                        selectRv.setAdapter(myAdapter);
                        for (int i = 0; i < strings.length; i++) {
                            myAdapter.notifyItemInserted(i);
                            selectRv.scrollToPosition(i);
                        }

                        myAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, final int position) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    selectBt.setBackground(getResources().getDrawable(R.drawable.not_bt));
                                    selectBt.setEnabled(false);
                                }
                                selectBt.setVisibility(View.VISIBLE);
                                selectRv.setVisibility(View.GONE);
                                //ls = strings[position];
                                DbAdd(strings[position], Msg.TYPE_SENT);
                                addToView(myList, new Msg(strings[position], getResources().getDrawable(R.drawable.man1), Msg.TYPE_SENT), msgAdapter);
                                Update();

                                count = Integer.parseInt(ss[position][2]);

                                if (count == 100) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        selectBt.setVisibility(View.VISIBLE);
                                        selectBt.setBackground(getResources().getDrawable(R.drawable.not_bt));
                                        selectBt.setEnabled(false);
                                    }

                                } else {
                                    String[][] sss = splitString(list.get(count));
                                    while (sss[0][1].equals("1")) {
                                        final String[][] finalSss = sss;

                                        show();

                                        Thread thread = new Thread() {
                                            @Override
                                            public void run() {

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        selectBt.setEnabled(false);
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                            selectBt.setBackground(getResources().getDrawable(R.drawable.not_bt));
                                                        }
                                                    }
                                                });

                                                try {
                                                    sleep(2300);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        DbAdd(finalSss[0][0], Msg.TYPE_RECEIVED);
                                                        addToView(myList, new Msg(finalSss[0][0], drawable, Msg.TYPE_RECEIVED), msgAdapter);
                                                        //ls = finalSss[0][0];
                                                        Update();
                                                        if (Integer.parseInt(finalSss[0][2])!=100) {
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                                selectBt.setBackground(getResources().getDrawable(R.drawable.ok_bt));
                                                            }
                                                            selectBt.setEnabled(true);
                                                        }else {
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                                selectBt.setVisibility(View.VISIBLE);
                                                                selectBt.setBackground(getResources().getDrawable(R.drawable.not_bt));
                                                                selectBt.setEnabled(false);
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                        };
                                        thread.start();

                                        count = Integer.parseInt(finalSss[0][2]);

                                        if (count == 100) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                selectBt.setVisibility(View.VISIBLE);
                                                selectBt.setBackground(getResources().getDrawable(R.drawable.not_bt));
                                                selectBt.setEnabled(false);
                                                break;
                                            }
                                        } else {
                                            sss = splitString(list.get(count));
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                selectBt.setVisibility(View.VISIBLE);
                                                selectBt.setBackground(getResources().getDrawable(R.drawable.ok_bt));
                                            }
                                        }
                                    }
                                }

                            }
                        });
                    }
                });
            }
    }

    public void DbAdd(String c,int type){
        ContentValues cv = new ContentValues();
        cv.put("user_name",name.getText().toString());
        cv.put("content",c);
        cv.put("type",type);
        dbWriter.replace("msg",null,cv);
    }

    public void Update(){
        String c = name.getText().toString();
        Cursor cursor = dbReader.rawQuery("SELECT * FROM msg WHERE user_name=?",new String[]{c});
        cursor.moveToLast();
        String ls = cursor.getString(cursor.getColumnIndex("content"));
        dbWriter.execSQL("UPDATE person SET count=? WHERE name = ?",
                new String[]{count+"",c});
        dbWriter.execSQL("UPDATE person SET latest=? WHERE name = ?",
                new String[]{ls,c});
    }


    private void addToView(ArrayList<Msg> list,Msg msg,MsgAdapter mMsgAdapter){
        list.add(msg);
        mMsgAdapter.notifyItemInserted(list.size() -1);
        msgRecyclerView.scrollToPosition(mMsgAdapter.getItemCount());
       // msgAdapter.notifyItemChanged(list.size());
        msgAdapter.notifyItemRangeChanged(list.size()-1,1);
    }

    private String[][] splitString(String string){
        String[] strings = string.split("-");
        String[][] ss = new String[strings.length][];
        for (int i=0;i<strings.length;i++){
            ss[i] = strings[i].split(" ");
        }
        return ss;
    }

    public void initData(){
        myList.clear();
        cursor = dbReader.rawQuery("SELECT * FROM msg WHERE user_name = ?",new String[]{name.getText().toString()});
        while (cursor.moveToNext()){
            Msg msg = new Msg();
            msg.setContent(cursor.getString(cursor.getColumnIndex("content")));
            if (cursor.getInt(cursor.getColumnIndex("type"))==Msg.TYPE_RECEIVED){
                switch (name.getText().toString()){
                    case "李惠":
                        msg.setTx(getResources().getDrawable(R.drawable.gurl1));
                        break;
                    case "云美":
                        msg.setTx(getResources().getDrawable(R.drawable.gurl4));
                        break;
                    case "谷梁野":
                        msg.setTx(getResources().getDrawable(R.drawable.man2));
                        break;
                    case "张菊":
                        msg.setTx(getResources().getDrawable(R.drawable.gurl2));
                        break;
                    case "丁蕊":
                        msg.setTx(getResources().getDrawable(R.drawable.dr));
                        break;
                    case "田尧":
                        msg.setTx(getResources().getDrawable(R.drawable.ty));
                        break;
                    case "黎岛":
                        msg.setTx(getResources().getDrawable(R.drawable.ld));
                        break;
                    case "王山":
                        msg.setTx(getResources().getDrawable(R.drawable.ws));
                        break;
                }
            } else {
                msg.setTx(getResources().getDrawable(R.drawable.man1));
            }
            msg.setType(cursor.getInt(cursor.getColumnIndex("type")));
            msg.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
            count = getIntent().getIntExtra("count",0);
            myList.add(msg);
            //addToView(myList,msg,msgAdapter);
        }
        cursor.close();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK && selectRv.getVisibility()==View.VISIBLE){
            selectRv.setVisibility(View.GONE);
            selectBt.setVisibility(View.VISIBLE);
            return true;
        }else if (keyCode == KeyEvent.KEYCODE_BACK){
            Update();
            Intent intent = new Intent(TalkWith.this,MainView.class);
            startActivity(intent);
            return true;
        } else {
            return super.onKeyDown(keyCode,event);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (selectRv.getVisibility()==View.VISIBLE)
                {
                    selectRv.setVisibility(View.GONE);
                    selectBt.setVisibility(View.VISIBLE);
                }
                break;
            default:break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onStop(){
        super.onStop();
        Update();
    }

    public void show(){
        if (name.getText().toString().equals("李惠") && count == 9) {
            ContentValues cv = new ContentValues();
            cv.put("name", "云美");
            cv.put("latest", "");
            cv.put("count", 0);
            dbWriter.insert("person", null, cv);
            cv.clear();
            cv.put("name", "谷梁野");
            cv.put("latest", "");
            cv.put("count", 0);
            dbWriter.insert("person", null, cv);
            Toast.makeText(TalkWith.this, "解锁聊天对象：云美、谷梁野", Toast.LENGTH_LONG).show();
        } else if (name.getText().toString().equals("李惠") && count == 7) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("t_title", "尸检报告");
            contentValues.put("t_item", "根据死者胃部食物消化情况来看，死者死亡时间大概在5点-7点，但超过6点的可能微乎其微，但不是没有可能");
            dbWriter.insert("thread", null, contentValues);
           AlertDialog alertDialog = new AlertDialog.Builder(this).create();
           alertDialog.show();
            Window window = alertDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.dialog_style);
            window.setContentView(R.layout.dialog_layout);
            GradientShaderTextView textView = window.findViewById(R.id.dialog_text);
            textView.setText("获得线索--尸检报告，在「线索-证据」查看");
        } else if (name.getText().toString().equals("谷梁野") && count == 60) {
            ContentValues cv = new ContentValues();
            cv.put("name", "丁蕊");
            cv.put("latest", "");
            cv.put("count", 0);
            dbWriter.insert("person", null, cv);
            Toast.makeText(TalkWith.this, "解锁聊天对象：丁蕊", Toast.LENGTH_LONG).show();
        } else if (name.getText().toString().equals("谷梁野") && count == 75) {
            ContentValues cv = new ContentValues();
            cv.put("name", "黎岛");
            cv.put("latest", "");
            cv.put("count", 0);
            dbWriter.insert("person", null, cv);
            Toast.makeText(TalkWith.this, "解锁聊天对象：黎岛", Toast.LENGTH_SHORT).show();
        } else if (name.getText().toString().equals("丁蕊") && count == 17) {
            ContentValues cv = new ContentValues();
            cv.put("t_title", "忍耐的极限");
            cv.put("t_item", "片段：“早上，院子里一定会出现一些猫粪，将汽车停在停车场，引擎盖上布满猫的脚印，花盆里植物的叶子被啃的乱七八糟。虽然知道这些罪行全是一只带白棕斑点的猫犯下的，却苦无对策，就算立了一整排矿泉水瓶挡她，也一点效果都没有，每天都在挑战自己忍耐的极限。”");
            dbWriter.insert("thread", null, cv);
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.show();
            Window window = alertDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.dialog_style);
            window.setContentView(R.layout.dialog_layout);
            GradientShaderTextView textView = window.findViewById(R.id.dialog_text);
            textView.setText("获得线索--忍耐的极限，在「线索-证据」查看");
        } else if (name.getText().toString().equals("云美") && count == 24) {
            ContentValues cv = new ContentValues();
            cv.put("name", "田尧");
            cv.put("latest", "");
            cv.put("count", 0);
            dbWriter.insert("person", null, cv);
            cv.clear();
            cv.put("name", "张菊");
            cv.put("latest", "");
            cv.put("count", 0);
            dbWriter.insert("person", null, cv);
            Toast.makeText(TalkWith.this, "解锁聊天对象：田尧、张菊", Toast.LENGTH_LONG).show();
        } else if (name.getText().toString().equals("李惠") && count == 18) {
            dbWriter.execSQL("UPDATE status SET status=? WHERE count = ?",
                    new String[]{1 + "", "1"});
            new AlertDialog.Builder(TalkWith.this)
                    .setMessage("已开启第一次搜证")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
        } else if (name.getText().toString().equals("谷梁野") && count == 50) {
            dbWriter.execSQL("UPDATE status SET status=? WHERE count = ?",
                    new String[]{2 + "", "1"});
            new AlertDialog.Builder(TalkWith.this)
                    .setMessage("已开启第二次搜证")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
        } else if (name.getText().toString().equals("丁蕊") && count == 28) {
            dbWriter.execSQL("UPDATE status SET status=? WHERE count = ?",
                    new String[]{3 + "", "1"});
            new AlertDialog.Builder(TalkWith.this)
                    .setMessage("已开启第三次搜证")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
        }

    }
}