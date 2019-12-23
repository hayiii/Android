package com.example.mistery;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicServer extends Service {
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    @Deprecated
    public void onStart(Intent intent,int startId){
        super.onStart(intent,startId);
        if (mediaPlayer==null){
           // mediaPlayer = MediaPlayer.create(this,R.raw.);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }
}
