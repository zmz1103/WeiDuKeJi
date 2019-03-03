package com.wd.tech.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.presenter.ChattingPresenter;

import butterknife.BindView;

/**
 * date:2019/2/19 19:45
 * author:赵明珠(啊哈)
 * function:消息
 */
public class ContactsFragment extends WDFragment{

    @BindView(R.id.chatting)
    RecyclerView mChatting;
    ChattingPresenter mChattingPresenter;
    @Override
    public int getContent() {
        return R.layout.activity_contacts_fragment;
    }

    @Override
    public void initView(View view) {

    }
}
