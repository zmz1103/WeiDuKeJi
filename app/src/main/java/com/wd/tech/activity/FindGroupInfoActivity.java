package com.wd.tech.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wd.tech.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindGroupInfoActivity extends WDActivity {

    @BindView(R.id.group_back)
    ImageView groupBack;
    @BindView(R.id.group_head)
    ImageView groupHead;
    @BindView(R.id.group_maxcount)
    TextView groupMaxcount;
    @BindView(R.id.group_image_into)
    ImageView groupImageInto;
    @BindView(R.id.group_meeting)
    TextView groupMeeting;
    @BindView(R.id.group_description_into)
    ImageView groupDescriptionInto;
    @BindView(R.id.group_inform_into)
    ImageView groupInformInto;
    @BindView(R.id.group_manage_into)
    ImageView groupManageInto;
    @BindView(R.id.group_record)
    TextView groupRecord;
    @BindView(R.id.group_dissolve)
    TextView groupDissolve;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_group_info;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {

    }

    @Override
    protected void destoryData() {

    }

    @OnClick({R.id.group_back, R.id.group_image_into, R.id.group_description_into, R.id.group_inform_into, R.id.group_manage_into, R.id.group_dissolve})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.group_back:
                //返回
                finish();
                break;
            case R.id.group_image_into:
                //群成员人数
                break;
            case R.id.group_description_into:
                //群简介
                break;
            case R.id.group_inform_into:
                //群通知
                break;
            case R.id.group_manage_into:
                //群管理
                break;
            case R.id.group_dissolve:
                //查询聊天记录
                break;
            default:
                break;
        }
    }
}
