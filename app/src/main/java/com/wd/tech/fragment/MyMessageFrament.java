package com.wd.tech.fragment;

import android.view.View;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.presenter.QueryGroupPresenter;

/**
 * date:2019/2/19 19:47
 * author:赵明珠(啊哈)
 * function:
 */
public class MyMessageFrament extends WDFragment{

    QueryGroupPresenter presenter;
    @Override
    public int getContent() {
        return R.layout.activity_my_message_fragment;
    }

    @Override
    public void initView(View view) {

    }
}
