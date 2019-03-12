package com.wd.tech.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.wd.tech.R;
import com.wd.tech.adapter.GroupNoticeAdapter;
import com.wd.tech.adapter.MyGroupAdapter;
import com.wd.tech.bean.FriendInform;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.GroupAuditPresenter;
import com.wd.tech.presenter.GroupNoticePresenter;
import com.wd.tech.util.SpaceItemDecoration;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/3/6 10:20
 * author:赵明珠(啊哈)
 * function:
 */
public class GroupNoticeActivity extends WDActivity {

    @BindView(R.id.notice_recycler)
    RecyclerView mRecyclerView;
    GroupNoticePresenter mGroupNoticePresenter;
    GroupAuditPresenter mGroupAuditPresenter;
    private GroupNoticeAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_notice;
    }

    @Override
    protected void initView() {
        mGroupNoticePresenter = new GroupNoticePresenter(new GroupCall());
        mGroupAuditPresenter = new GroupAuditPresenter(new AuditCall());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        mAdapter = new GroupNoticeAdapter(this);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        mRecyclerView.setAdapter(mAdapter);
        mGroupNoticePresenter.reqeust((int)user.getUserId(),user.getSessionId());

        mAdapter.setOnClickListener(new GroupNoticeAdapter.OnClickListener() {
            @Override
            public void onClick(int id, int notice) {
                mGroupAuditPresenter.reqeust((int)user.getUserId(),user.getSessionId(),id,notice);
            }
        });
    }

    @OnClick(R.id.group_notice_back)
    public void onClick() {
        finish();
    }

    @Override
    protected void destoryData() {
        mGroupNoticePresenter=null;
        mGroupAuditPresenter=null;
    }

    private class GroupCall implements DataCall<Result<List<FriendInform>>> {
        @Override
        public void success(Result<List<FriendInform>> result) {
            if (result.getStatus().equals("0000")) {

                List<FriendInform> mResult = result.getResult();

                mAdapter.clear();
                mAdapter.addAll(mResult);
                mAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class AuditCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                mGroupNoticePresenter.reqeust((int)user.getUserId(),user.getSessionId());
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
