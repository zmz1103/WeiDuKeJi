package com.wd.tech.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wd.tech.R;
import com.wd.tech.adapter.GroupAdapter;
import com.wd.tech.bean.FriendInfoList;
import com.wd.tech.bean.Group;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.User;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.DaoSession;
import com.wd.tech.dao.UserDao;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.QueryGroupPresenter;
import com.wd.tech.view.DataCall;

import java.util.ArrayList;
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

    @Override
    public int getContent() {
        return R.layout.activity_my_message_fragment;
    }

    @Override
    public void initView(View view) {
        presenter = new QueryGroupPresenter(new QueryCall());

        groupAdapter = new GroupAdapter(getActivity());

        linkman.setAdapter(groupAdapter);



        presenter.reqeust(40,"155075257646440");

    }

    private class QueryCall implements DataCall<Result<List<Group>>> {
        @Override
        public void success(Result<List<Group>> result) {
            if (result.getStatus().equals("0000")){
                List<Group> groupList = result.getResult();
                Group group = new Group();
                groupList.add(0,group);
                groupList.add(1,group);
                Group group2 = new Group();
                List<FriendInfoList>lists = new ArrayList<>();
                FriendInfoList friendInfoList = new FriendInfoList();
                friendInfoList.setNickName("啊哈");
                friendInfoList.setSignature("呃呃呃");
                lists.add(friendInfoList);
                group2.setFriendInfoList(lists);

                group2.setCurrentNumber(10);
                group2.setGroupName("我的好友");
                groupList.add(2,group2);

                group.setCurrentNumber(10);
                group.setGroupName("黑名单");
                groupList.add(3,group);
                groupAdapter.addAll(groupList);
                groupAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
