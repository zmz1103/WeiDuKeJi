package com.wd.tech.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wd.tech.R;

import me.jessyan.autosize.internal.CustomAdapt;

public class PublishActivity extends AppCompatActivity implements CustomAdapt {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
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
