package com.wd.tech.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wd.tech.R;
import com.wd.tech.adapter.GroupAdapter;
import com.wd.tech.bean.Group;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.QueryGroupPresenter;
import com.wd.tech.view.DataCall;
import java.util.List;

import butterknife.BindView;

/**
 * date:2019/2/19 19:47
 * author:赵明珠(啊哈)
 * function:
 */
public class MyMessageFrament extends WDFragment{

    QueryGroupPresenter presenter;
    @BindView(R.id.linkman_pre)

    PullToRefreshListView linkman;
    private GroupAdapter groupAdapter;
    private String sessionId;
    private int userId;

    @Override
    public int getContent() {
        return R.layout.activity_my_message_fragment;
    }

    @Override
    public void initView(View view) {
        presenter = new QueryGroupPresenter(new QueryCall());

        groupAdapter = new GroupAdapter(getActivity());

        linkman.setAdapter(groupAdapter);


        if (user != null){

            userId = (int) user.getUserId();
            sessionId = user.getSessionId();

            presenter.reqeust(userId,sessionId);
        }else {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        }

    }

    private class QueryCall implements DataCall<Result<List<Group>>> {
        @Override
        public void success(Result<List<Group>> result) {
            if (result.getStatus().equals("0000")){
                List<Group> groupList = result.getResult();
                Group group = new Group();
                groupList.add(0,group);
                groupList.add(1,group);
                groupList.add(2,group);
                groupAdapter.addAll(groupList);
                groupAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
