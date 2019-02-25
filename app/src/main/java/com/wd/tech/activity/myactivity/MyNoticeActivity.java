package com.wd.tech.activity.myactivity;

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
import com.wd.tech.adapter.NoticeListAdapter;
import com.wd.tech.bean.NoticeListDAta;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FindSysNoticePresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyNoticeActivity extends WDActivity {


    @BindView(R.id.ny_notice_layout)
    SmartRefreshLayout mLayout;
    @BindView(R.id.my_notice_listView)
    RecyclerView mRec;
    private int mPage = 1;
    private FindSysNoticePresenter findSysNoticePresenter;
    private NoticeListAdapter noticeListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_notice;
    }

    @Override
    protected void initView() {
        findSysNoticePresenter = new FindSysNoticePresenter(new findList());
        mLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
//设置 Footer 为 球脉冲 样式
        mLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        requestt(mPage);
        mLayout.setEnableRefresh(true);
        //启用刷新
        mLayout.setEnableLoadmore(true);
        //启用加载
        mLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                requestt(mPage);
                noticeListAdapter.clear();
                refreshlayout.finishRefresh();
            }
        });
        mLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                requestt(mPage);
                noticeListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });
        noticeListAdapter = new NoticeListAdapter(this);
        mRec.setLayoutManager(new LinearLayoutManager(this));
        mRec.setAdapter(noticeListAdapter);
    }
    private void requestt(int page) {
        if (user == null) {
            findSysNoticePresenter.reqeust(0, "", page, 5);
        } else {
            findSysNoticePresenter.reqeust(user.getUserId(), user.getSessionId(), page, 5);
        }
    }
    @Override
    protected void destoryData() {
        findSysNoticePresenter.unBind();
    }
    @OnClick(R.id.my_back_notice)
    void iD(){
        finish();
    }

    private class findList implements DataCall<Result<List<NoticeListDAta>>> {
        @Override
        public void success(Result<List<NoticeListDAta>> result) {
            Toast.makeText(MyNoticeActivity.this, ""+result.getMessage()+result.getResult().size(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                noticeListAdapter.setListDAta(result.getResult());
                noticeListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
