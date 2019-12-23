package com.example.mistery.msg;

import android.graphics.drawable.Drawable;

public class Msg {
    public static final int TYPE_RECEIVED = 1;
    public static final int TYPE_SENT = 0;

    private Drawable tx;
    private String content;
    private String userName;
    private int type;

    public Msg(String content,Drawable tx, int type){
        this.content = content;
        this.type = type;
        this.tx = tx;
    }

    public Msg(){

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent(){
        return content;
    }

    public int getType(){
        return type;
    }

    public Drawable getTx(){
        return tx;
    }

    public void setTx(Drawable tx) {
        this.tx = tx;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }
}
