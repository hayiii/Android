package com.example.mistery.msg;

import android.graphics.drawable.Drawable;

public class Person {
    private Drawable tx;
    private String content;
    private String latest;
    private int count;

    public Person(){}


    public Drawable getTx() {
        return tx;
    }

    public String getContent(){
        return this.content;
    }

    public String getLatest(){
        return this.latest;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public void setTx(Drawable tx) {
        this.tx = tx;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
