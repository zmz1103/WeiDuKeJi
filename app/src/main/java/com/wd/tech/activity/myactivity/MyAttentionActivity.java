package com.wd.tech.activity.myactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.adapter.AttentionListAdapter;
import com.wd.tech.adapter.CollectListAdapter;
import com.wd.tech.bean.AttentionListData;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FindFollowUserListPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyAttentionActivity extends WDActivity {


    private FindFollowUserListPresenter findFollowUserListPresenter;
    private int page = 1;
    private AttentionListAdapter attentionListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_attention;
    }

    @BindView(R.id.my_att_layout)
    SmartRefreshLayout mLayout;
    @BindView(R.id.my_att_rec)
    RecyclerView mRec;

    @Override
    protected void initView() {

        findFollowUserListPresenter = new FindFollowUserListPresenter(new findFollow());
        mLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
//设置 Footer 为 球脉冲 样式
        mLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        requestt(page);
        mLayout.setEnableRefresh(true);
        //启用刷新
        mLayout.setEnableLoadmore(true);
        //启用加载
        mLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                requestt(page);
                attentionListAdapter.clear();
                refreshlayout.finishRefresh();
            }
        });
        mLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestt(page);
                attentionListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });
        attentionListAdapter = new AttentionListAdapter(this);
        mRec.setLayoutManager(new LinearLayoutManager(this));
        mRec.setAdapter(attentionListAdapter);
    }

    private void requestt(int page) {
        if (user == null) {
            findFollowUserListPresenter.reqeust(0, "", page, 10);
        } else {
            findFollowUserListPresenter.reqeust(user.getUserId(), user.getSessionId(), page, 10);
        }
    }
    @OnClick(R.id.my_back_gz)
    void dDian(View v){
        switch (v.getId()){
            case R.id.my_back_gz:
                finish();
                break;

                default:
                    break;
        }
    }

    @Override
    protected void destoryData() {
        findFollowUserListPresenter.unBind();
    }

    private class findFollow implements DataCall<Result<List<AttentionListData>>> {

        @Override
        public void success(Result<List<AttentionListData>> result) {
            Toast.makeText(MyAttentionActivity.this, "" + result.getMessage() + result.getResult().size(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                attentionListAdapter.setListData(result.getResult());
                attentionListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
