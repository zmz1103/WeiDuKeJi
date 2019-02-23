package com.wd.tech.activity;

import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.application.WDApplication;
import com.wd.tech.util.NetWorkUtils;

import butterknife.OnClick;

/**
 * 作者: Wang on 2019/2/21 19:19
 * 寄语：加油！相信自己可以！！！
 */


public class NoNetWorkActivity extends WDActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_no_network;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.no_net_Btn)
    void conntionNet() {
        if (NetWorkUtils.isNetworkAvailable(WDApplication.getAppContext())) {
            finish();
        } else {
            Toast.makeText(WDApplication.getAppContext(), "ffff请检查网络", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void destoryData() {

    }
}
