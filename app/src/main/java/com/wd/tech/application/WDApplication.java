package com.wd.tech.application;

import android.app.Application;
import android.content.Context;

/**
 * date:2019/2/18 18:32
 * author:赵明珠(啊哈)
 * function:
 */
public class WDApplication extends Application {
    private static WDApplication wdApplication ;

    @Override
    public void onCreate() {
        super.onCreate();
        wdApplication = this;
    }

    public static Context getAppContext(){
        return wdApplication;
    }

}
