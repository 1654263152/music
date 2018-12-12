package com.zh.music;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;


public class MyApplication extends Application {
    private static Context mContext;

    public static Context getInstance() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        LitePal.initialize(mContext);
    }
}
