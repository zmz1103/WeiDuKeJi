package com.wd.tech.activity.huanxin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.wd.tech.R;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.bean.FindConversationList;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FindConversationListPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class IMGroupActivity extends WDActivity {


    FindConversationListPresenter findConversationListPresenter ;
    private FindConversationList conversation;
    @BindView(R.id.chat_name)
    TextView mName;
    private EaseChatFragment chatFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_im;
    }

    @Override
    protected void initView() {
        findConversationListPresenter = new FindConversationListPresenter(new QueryData());
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    android.Manifest.permission.RECORD_AUDIO,android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA},0);
        }

        Intent intent = getIntent();
        String UserName = intent.getStringExtra("UserNames");

        chatFragment = new EaseChatFragment();

        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.hx_ok, chatFragment).commit();
        chatFragment.hideTitleBar();

        findConversationListPresenter.reqeust((int)user.getUserId(),user.getSessionId(),UserName);

    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        chatFragment.hideTitleBar();
    }

    @OnClick(R.id.chat_back)
    public void onClick(){
        finish();
    }
    class QueryData implements DataCall<Result<List<FindConversationList>>> {

        @Override
        public void success(Result<List<FindConversationList>> result) {

            if (result.getStatus().equals("0000")){
                List<FindConversationList> mResult = result.getResult();
                conversation = mResult.get(0);
                mName.setText(conversation.getNickName());
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
}
