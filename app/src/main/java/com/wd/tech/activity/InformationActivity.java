package com.wd.tech.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wd.tech.R;

import me.jessyan.autosize.internal.CustomAdapt;

public class InformationActivity extends WDActivity implements CustomAdapt {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }
}
