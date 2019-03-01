package com.wd.tech.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.ApplyPresenter;
import com.wd.tech.view.DataCall;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/2/27 15:19
 * author:赵明珠(啊哈)
 * function:
 */
public class SendApplyActivity extends WDActivity {

    @BindView(R.id.apply_intro)
    EditText mApplyIntro;
    ApplyPresenter mApplyPresenter;
    private String mGroupId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_group_item;
    }

    @Override
    protected void initView() {
        mApplyPresenter = new ApplyPresenter(new ApplyCall());
        Intent intent = getIntent();

        mGroupId = intent.getStringExtra("mApplyGroupId");

    }
    @OnClick({R.id.add_group_back,R.id.add_group_send})
    public void onClick(View view){
        if (view.getId() == R.id.add_group_back){
            finish();
        }
        
        if (view.getId() == R.id.add_group_send){
            String mIntro = mApplyIntro.getText().toString();
            if (!mIntro.isEmpty()){
                String sessionId = user.getSessionId();
                int userId = (int) user.getUserId();

                mApplyPresenter.reqeust(userId,sessionId,Integer.parseInt(mGroupId),mIntro);

            }else {
                Toast.makeText(this, "备注不能为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void destoryData() {
        mApplyPresenter.unBind();
    }

    private class ApplyCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            Toast.makeText(SendApplyActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
