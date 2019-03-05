package com.wd.tech.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.wd.tech.R;
import com.wd.tech.adapter.GroupsAdapter;
import com.wd.tech.bean.Group;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.GroupsPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/3/4 19:55
 * author:赵明珠(啊哈)
 * function:分组
 */
public class GroupsActivity extends WDActivity{

    @BindView(R.id.new_groups)
    RelativeLayout mNewGroup;

    @BindView(R.id.my_groups)
    RecyclerView mGroups;

    GroupsPresenter mGroupsPresenter;
    private GroupsAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_groups;
    }

    @Override
    protected void initView() {
        mGroupsPresenter = new GroupsPresenter(new GroupsCall());
        mGroups.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        mAdapter = new GroupsAdapter(this);
        mGroups.setAdapter(mAdapter);
        mGroupsPresenter.reqeust((int)user.getUserId(),user.getSessionId());
    }
    @OnClick(R.id.choose_group_back)
    public void onClick(){
        finish();
    }
    @Override
    protected void destoryData() {
        mGroupsPresenter.unBind();
    }

    private class GroupsCall implements DataCall<Result<List<Group>>> {
        @Override
        public void success(Result<List<Group>> result) {
            if (result.getStatus().equals("0000")){
                List<Group> mResult = result.getResult();

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
