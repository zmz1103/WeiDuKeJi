package com.wd.tech.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.DeletePresenter;
import com.wd.tech.view.DataCall;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/3/4 10:24
 * author:赵明珠(啊哈)
 * function:
 */
public class SettingActivity extends WDActivity {


    @BindView(R.id.friend_setting_headpic)
    SimpleDraweeView mHeadPic;
    @BindView(R.id.setting_nickname)
    TextView mNickname;
    @BindView(R.id.friend_setting_name)
    TextView mName;

    private String mid;
    DeletePresenter mDeletePresenter;
    private int userId;
    private String sessionId;
    private String name;
    private String headpic;
    private Dialog builder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_setting;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mid = intent.getStringExtra("mid");
        name = intent.getStringExtra("name");
        headpic = intent.getStringExtra("headpic");
        if (user != null) {
            userId = (int) user.getUserId();
            sessionId = user.getSessionId();
        }

        mHeadPic.setImageURI(Uri.parse(headpic));
        mName.setText(name);
        mNickname.setText(name);

        mDeletePresenter = new DeletePresenter(new DeleteCall());
    }

    @OnClick({R.id.setting_back, R.id.delete_friend, R.id.grouping})
    public void onClick(View view) {
        if (view.getId() == R.id.setting_back) {
            finish();
        }
        if (view.getId() == R.id.delete_friend) {
            initDialog();
        }
        if (view.getId() == R.id.grouping){
            Intent intent = new Intent(SettingActivity.this, GroupsActivity.class);
            startActivity(intent);
        }

    }

    private void initDialog() {
        View mView = View.inflate(SettingActivity.this, R.layout.delete_friend_popwindow, null);
        //设置弹窗布局
        builder = new Dialog(SettingActivity.this, R.style.BottomDialog);
        builder.setContentView(mView);
        builder.setCanceledOnTouchOutside(true);

        ViewGroup.LayoutParams layoutParamsthreefilmreview = mView.getLayoutParams();
        layoutParamsthreefilmreview.width = getResources().getDisplayMetrics().widthPixels;
        mView.setLayoutParams(layoutParamsthreefilmreview);
        builder.getWindow().setGravity(Gravity.BOTTOM);
        builder.show();

        RelativeLayout mDeleteAll = mView.findViewById(R.id.delete_all);
        RelativeLayout mDeleteFriend = mView.findViewById(R.id.delete_my_friend);
        RelativeLayout mCancel = mView.findViewById(R.id.cancel_delete);

        mDeleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeletePresenter.reqeust(userId, sessionId, Integer.parseInt(mid));
                builder.dismiss();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

    }

    @Override
    protected void destoryData() {
        mDeletePresenter.unBind();
    }

    private class DeleteCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                Toast.makeText(SettingActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
