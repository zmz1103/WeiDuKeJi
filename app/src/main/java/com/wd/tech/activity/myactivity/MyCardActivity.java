package com.wd.tech.activity.myactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.wd.tech.adapter.CardListAdapter;
import com.wd.tech.bean.CardListData;
import com.wd.tech.bean.NoticeListDAta;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FindFollowUserListPresenter;
import com.wd.tech.presenter.FindMyPostByIdPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;

public class MyCardActivity extends WDActivity {


    private FindMyPostByIdPresenter findMyPostByIdPresenter;

    private int page = 1;
    @BindView(R.id.my_card_layout)
    SmartRefreshLayout mLayout;
    @BindView(R.id.my_card_rec)
    RecyclerView mRec;
    private CardListAdapter cardListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_card;
    }

    @Override
    protected void initView() {
        findMyPostByIdPresenter = new FindMyPostByIdPresenter(new findPost());
//
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
                cardListAdapter.clear();
                refreshlayout.finishRefresh();
            }
        });
        mLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestt(page);
                cardListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });
        cardListAdapter = new CardListAdapter(this);
        mRec.setLayoutManager(new LinearLayoutManager(this));
        mRec.setAdapter(cardListAdapter);
    }

    private void requestt(int page) {
        if (user == null) {
            findMyPostByIdPresenter.reqeust(0, "", page, 10);
        } else {
            findMyPostByIdPresenter.reqeust(user.getUserId(), user.getSessionId(), page, 10);
        }
    }

    @Override
    protected void destoryData() {
        findMyPostByIdPresenter.unBind();
    }

    private class findPost implements DataCall<Result<List<CardListData>>> {
        @Override
        public void success(Result<List<CardListData>> result) {
            Toast.makeText(MyCardActivity.this, "" + result.getMessage() + result.getResult().size(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                cardListAdapter.setListData(result.getResult());

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
