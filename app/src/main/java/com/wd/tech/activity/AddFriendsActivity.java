package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.AddFriendPresenter;
import com.wd.tech.view.DataCall;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class AddFriendsActivity extends WDActivity implements CustomAdapt {

    @BindView(R.id.addfriends_back)
    ImageView addfriendsBack;
    @BindView(R.id.addfriends_send)
    TextView addfriendsSend;
    @BindView(R.id.addfriends_headpic)
    SimpleDraweeView addfriendsHeadPic;
    @BindView(R.id.addfriends_nickname)
    TextView addfriendsNickName;
    @BindView(R.id.addfriends_signature)
    TextView addfriendsSignature;
    @BindView(R.id.et_remark)
    EditText etRemark;
    private int mMUserid;
    private AddFriendPresenter mAddFriendPresenter;
    private String mMHeadPic;
    private String mMNickName;
    private String mMSignature;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friends;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mMUserid = intent.getExtras().getInt("mUserid");
        mMHeadPic = intent.getExtras().getString("mHeadPic");
        mMNickName = intent.getExtras().getString("mNickName");
        mMSignature = intent.getExtras().getString("mSignature");
        addfriendsHeadPic.setImageURI(mMHeadPic);
        addfriendsNickName.setText(mMNickName);
        addfriendsSignature.setText(mMSignature);


    }


    class AddFriends implements DataCall<Result>{

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                Toast.makeText(AddFriendsActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(AddFriendsActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void destoryData() {

    }

    @OnClick({R.id.addfriends_back, R.id.addfriends_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addfriends_back:
                finish();
                break;
            case R.id.addfriends_send:
                mAddFriendPresenter = new AddFriendPresenter(new AddFriends());
                mAddFriendPresenter.reqeust((int)user.getUserId(),user.getSessionId(),mMUserid,etRemark.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddFriendPresenter=null;
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
