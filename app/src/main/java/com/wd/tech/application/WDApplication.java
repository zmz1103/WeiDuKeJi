package com.wd.tech.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * date:2019/2/18 18:32
 * author:赵明珠(啊哈)
 * function:
 */
public class WDApplication extends Application {
    private static WDApplication wdApplication ;
    /** 主线程ID */
    private static int mMainThreadId = -1;
    /** 主线程ID */
    private static Thread mMainThread;
    /** 主线程Handler */
    private static Handler mMainThreadHandler;
    /** 主线程Looper */
    private static Looper mMainLooper;


    @Override
    public void onCreate() {
        super.onCreate();
        wdApplication = this;
        Fresco.initialize(this);
    }

    public static Context getAppContext(){
        return wdApplication;
    }

    /** 获取主线程ID */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /** 获取主线程 */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /** 获取主线程的handler */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /** 获取主线程的looper */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }
}
