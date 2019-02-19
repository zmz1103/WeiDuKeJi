package com.wd.tech.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.view.DataCall;

public class MainActivity extends AppCompatActivity {

    private String s;
    private TextView textView;
    CeshiPresenter ceshiPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CeshiPresenter ceshiPresenter = new CeshiPresenter(new CeCall());


        ceshiPresenter.reqeust("");
    }

    private class CeCall implements DataCall<Result> {


        @Override
        public void success(Result result) {
            Log.e("zmz",""+result.getStatus()+"   "+result.getMessage());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
