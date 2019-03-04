package com.wd.tech.application;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.DaoSession;
import com.wd.tech.dao.UserDao;

/**
 * date:2019/2/18 18:32
 * author:赵明珠(啊哈)
 * function:
 */
public class WDApplication extends Application {
    private static WDApplication wdApplication;
    /**
     * 主线程ID
     */
    private static int mMainThreadId = -1;

    private UserDao userDao = null;
    /**
     * 主线程ID
     */
    private static Thread mMainThread;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;


    @Override
    public void onCreate() {
        super.onCreate();
        wdApplication = this;
        Fresco.initialize(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        EaseUI.getInstance().init(this, null);

        EMClient.getInstance().setDebugMode(true);

        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);

        userDao = daoSession.getUserDao();

    }

    public UserDao getUserDao() {
        return userDao;
    }

    public static WDApplication getAppContext() {
        return wdApplication;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }
}
