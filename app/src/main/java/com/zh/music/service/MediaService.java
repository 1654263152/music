package com.zh.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class MediaService extends Service {
    private static final String TAG = "MediaService";
    public static MediaPlayer mMediaPlayer = new MediaPlayer();
    private MyBinder mBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public class MyBinder extends Binder {

        public void playMusic() {
            mMediaPlayer.start();
        }

        public void iniMediaPlayer(String url) {
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(url);
                mMediaPlayer.prepare();
                playMusic();
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Log.i(TAG, "onCompletion: " + "完成！");
                        pauseMusic();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void pauseMusic() {
            mMediaPlayer.pause();
        }

        public void closeMedia() {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }
        }
    }
}
