package com.wd.tech.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.CreateGroupPresenter;
import com.wd.tech.view.DataCall;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/2/26 19:28
 * author:赵明珠(啊哈)
 * function:
 */
public class CreateGroupActivity extends WDActivity{

    @BindView(R.id.group_name)
    EditText mGroupName;

    @BindView(R.id.group_introduce)
    EditText mGroupIntroduce;

    CreateGroupPresenter mCreateGroup;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_group;
    }

    @Override
    protected void initView() {
        mCreateGroup = new CreateGroupPresenter(new CreateCall());
    }

    @OnClick({R.id.establish,R.id.create_back})
    public void onClick(View view){
        if (view.getId() == R.id.create_back){
            finish();
        }
        if (view.getId() == R.id.establish){
            int userId = (int) user.getUserId();
            String sessionId = user.getSessionId();

            String groupName = mGroupName.getText().toString();

            String groupIntroduce = mGroupIntroduce.getText().toString();
            if (!groupName.isEmpty()){
                Log.e("zmz","群名："+groupName);
                Log.e("zmz","介绍："+groupIntroduce);
                mCreateGroup.reqeust(userId,sessionId,groupName,groupIntroduce);

            }else {
                Toast.makeText(this, "群名不能为空", Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    protected void destoryData() {

    }

    class CreateCall implements DataCall<Result> {
        @Override
        public void success(Result result) {

            Toast.makeText(CreateGroupActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {

                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
