package com.wd.tech.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wd.tech.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 作者: Wang on 2019/2/19 15:58
 * 寄语：加油！相信自己可以！！！
 */


public class StartActivity extends AppCompatActivity {

    int time = 2;
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (time <= 0) {
                    startActivity(new Intent(StartActivity.this, HomeActivity.class));
                    finish();
                    return;
                }
                time--;
                handler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };
    @BindView(R.id.s_t)
    TextView t;
    @BindView(R.id.s_tt)
    TextView tt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/ziti.ttf");
        t.setTypeface(typeFace);
        tt.setTypeface(typeFace);
        handler.sendEmptyMessage(1);
    }


}
