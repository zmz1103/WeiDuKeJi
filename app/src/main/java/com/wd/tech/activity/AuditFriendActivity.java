package com.wd.tech.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.adapter.AuditAdapter;
import com.wd.tech.adapter.NoticeAdapter;
import com.wd.tech.bean.FriendInform;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.AuditPresenter;
import com.wd.tech.presenter.VerifierPresenter;
import com.wd.tech.util.SpaceItemDecoration;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/2/27 19:37
 * author:赵明珠(啊哈)
 * function:好友通知
 */
public class AuditFriendActivity extends WDActivity{

    AuditPresenter mAuditPresenter;
    @BindView(R.id.audit_list)
    RecyclerView mAuditList;
    @BindView(R.id.audit_friend)
    SmartRefreshLayout mAudit;
    VerifierPresenter mVerifierPresenter;
    private AuditAdapter mAdapter;
    private String sessionId;
    private int userId;
    private List<FriendInform> mResult;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_audit_friend;
    }

    @Override
    protected void initView() {
        mAuditPresenter = new AuditPresenter(new AuditCall());
        mVerifierPresenter = new VerifierPresenter(new VerifierCall());

        mAuditList.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        mAuditList.addItemDecoration(new SpaceItemDecoration(10));
        mAdapter = new AuditAdapter(this);
        mAuditList.setAdapter(mAdapter);
        userId = (int) user.getUserId();
        sessionId = user.getSessionId();

        mAuditPresenter.reqeust(userId, sessionId, true);

        mAudit.setEnableRefresh(true);

        mAudit.setEnableLoadmore(true);


        mAudit.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mAuditPresenter.reqeust(userId, sessionId, true);
                refreshlayout.finishRefresh();

            }
        });
        mAudit.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                mAuditPresenter.reqeust(userId, sessionId, false);

                refreshlayout.finishLoadmore();
            }
        });

        mAdapter.setOnClickListenter(new AuditAdapter.OnClickListenter() {
            @Override
            public void onItemClick(int id, int state) {

                mVerifierPresenter.reqeust(userId,sessionId,id,state);
            }
        });
    }

    @Override
    protected void destoryData() {
        mAuditPresenter.unBind();
        mVerifierPresenter.unBind();
    }

    @OnClick(R.id.aduit_back)
    public void onClick() {
        finish();
    }

    private class AuditCall implements DataCall<Result<List<FriendInform>>> {
        @Override
        public void success(Result<List<FriendInform>> result) {
            if (result.getStatus().equals("0000")) {

                mAdapter.clear();

                mResult = result.getResult();

                mAdapter.addAll(mResult);


            }

            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class VerifierCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){

                mAuditPresenter.reqeust(userId, sessionId, true);

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
