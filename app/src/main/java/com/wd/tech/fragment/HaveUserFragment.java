package com.wd.tech.fragment;

import android.view.View;

import com.wd.tech.R;

import me.jessyan.autosize.internal.CustomAdapt;


/**
 * 作者: Wang on 2019/2/20 14:53
 * 寄语：加油！相信自己可以！！！
 */


public class HaveUserFragment extends WDFragment implements CustomAdapt {
    @Override
    public int getContent() {
        return R.layout.fragment_have_user;
    }

    @Override
    public void initView(View view) {

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
