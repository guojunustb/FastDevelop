
package com.fastdevtest;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;

/**
 * Created by guojun on 16/4/6 22:35.
 */
public class MyApplication extends Application {
    public static final boolean DEBUG = true;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Logger
        Logger.init().hideThreadInfo().setLogLevel(DEBUG ? LogLevel.FULL : LogLevel.NONE);
        ButterKnife.setDebug(DEBUG);
    }
}
