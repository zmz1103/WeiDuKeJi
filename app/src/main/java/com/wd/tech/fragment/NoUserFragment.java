package com.wd.tech.fragment;

import android.content.Intent;
import android.view.View;

import com.wd.tech.R;
import com.wd.tech.activity.HomeActivity;
import com.wd.tech.activity.MainActivity;

import butterknife.OnClick;

/**
 * 作者: Wang on 2019/2/20 14:20
 * 寄语：加油！相信自己可以！！！
 */


public class NoUserFragment extends WDFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_no_user;
    }

    @OnClick(R.id.goLoginBtn)
    void onclick() {
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    @Override
    public void initView(View view) {

    }
}
