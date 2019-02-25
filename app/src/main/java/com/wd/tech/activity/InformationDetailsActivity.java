package com.wd.tech.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wd.tech.R;

public class InformationDetailsActivity extends WDActivity {


    private Intent mIntent;
    private String mId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_details;
    }

    @Override
    protected void initView() {
        mIntent = getIntent();
        mId = mIntent.getStringExtra("id");


    }

    @Override
    protected void destoryData() {

    }
}
