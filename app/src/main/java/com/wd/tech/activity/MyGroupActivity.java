package com.wd.tech.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.hyphenate.easeui.EaseConstant;
import com.wd.tech.R;
import com.wd.tech.activity.huanxin.IMGroupActivity;
import com.wd.tech.adapter.MyGroupAdapter;
import com.wd.tech.bean.MyGroup;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.QueryPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/3/5 18:50
 * author:赵明珠(啊哈)
 * function:
 */
public class MyGroupActivity extends WDActivity{

    @BindView(R.id.my_group_recycler)
    RecyclerView mRecyclerView;
    private MyGroupAdapter mAdapter;
    QueryPresenter mQueryPresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_group_item;
    }

    @Override
    protected void initView() {
        mQueryPresenter = new QueryPresenter(new QueryCall());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        mAdapter = new MyGroupAdapter(this);

        mRecyclerView.setAdapter(mAdapter);


        mQueryPresenter.reqeust((int)user.getUserId(),user.getSessionId());


        mAdapter.setOnClickListener(new MyGroupAdapter.OnClickListener() {
            @Override
            public void onClick(String name) {

                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(MyGroupActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MyGroupActivity.this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_PHONE_STATE
                        }, 100);
                    }
                }

                Intent intent = new Intent(MyGroupActivity.this,IMGroupActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,name);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                startActivity(intent);

            }
        });
    }
    @OnClick(R.id.my_group_back)
    public void onClick(){
        finish();
    }
    @Override
    protected void destoryData() {
        mQueryPresenter.unBind();
    }

    private class QueryCall implements DataCall<Result<List<MyGroup>>> {
        @Override
        public void success(Result<List<MyGroup>> result) {
            if (result.getStatus().equals("0000")){
                List<MyGroup> mResult = result.getResult();
                mAdapter.clear();
                mAdapter.addAll(mResult);
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
