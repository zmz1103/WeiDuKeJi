package com.wd.tech.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.activity.FriendDataActivity;
import com.wd.tech.activity.huanxin.IMActivity;
import com.wd.tech.adapter.GroupAdapter;
import com.wd.tech.bean.FindConversationList;
import com.wd.tech.bean.Group;
import com.wd.tech.bean.Result;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.DaoSession;
import com.wd.tech.dao.FindConversationListDao;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FindConversationListPresenter;
import com.wd.tech.presenter.QueryGroupPresenter;
import com.wd.tech.util.DaoUtils;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;

/**
 * date:2019/2/19 19:47
 * author:赵明珠(啊哈)
 * function:联系人
 */
public class MyMessageFrament extends WDFragment {

    QueryGroupPresenter mQueryGroupPresenter;
    @BindView(R.id.linkman_pre)
    ListView mLinkman;
    FindConversationListPresenter findConversationListPresenter ;
    @BindView(R.id.message_refreshLayout)
    SmartRefreshLayout mSmart;
    private GroupAdapter mGroupAdapter;
    private String mSessionId;
    private int mUserId;
    private SharedPreferences mDate;
    String userNames="";

    @Override
    public int getContent() {
        return R.layout.activity_my_message_fragment;
    }

    @Override
    public void initView(View view) {

        findConversationListPresenter = new FindConversationListPresenter(new QueryData());
        mQueryGroupPresenter = new QueryGroupPresenter(new QueryCall());

        mDate = getActivity().getSharedPreferences("date", Context.MODE_PRIVATE);

        mGroupAdapter = new GroupAdapter(getActivity());

        mLinkman.setAdapter(mGroupAdapter);
        mSmart.setEnableRefresh(true);

        mSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (user != null) {

                    mUserId = (int) user.getUserId();
                    mSessionId = user.getSessionId();

                    mQueryGroupPresenter.reqeust(mUserId, mSessionId);
                }

                refreshlayout.finishRefresh();

            }
        });

        mGroupAdapter.setOnClickChildListenter(new GroupAdapter.OnClickChildListenter() {
            @Override
            public void onClick(int mid,String name) {
                Intent intent = new Intent(getContext(), FriendDataActivity.class);
                intent.putExtra("mUserid",String.valueOf(mid));
                intent.putExtra("mUserName",name);
                startActivity(intent);
            }
        });

        if (user != null) {

            mUserId = (int) user.getUserId();
            mSessionId = user.getSessionId();

            mQueryGroupPresenter.reqeust(mUserId, mSessionId);
        } else {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        }
    }

    private class QueryCall implements DataCall<Result<List<Group>>> {
        @Override
        public void success(Result<List<Group>> result) {
            if (result.getStatus().equals("0000")) {

                List<Group> mGroup = result.getResult();

                Log.e("zmz","初始化："+userNames);
                userNames = "";

                for (int i = 0; i < mGroup.size(); i++) {
                    for (int j = 0; j < mGroup.get(i).getFriendInfoList().size(); j++) {
                        userNames = userNames+mGroup.get(i).getFriendInfoList().get(j).getUserName()+",";
                    }
                }

                if (userNames.equals("")){

                }else {
                    String substring = userNames.substring(0, userNames.length() - 1);
                    findConversationListPresenter.reqeust((int)user.getUserId(),user.getSessionId(),userNames);
                }



                List<Group> groupList = result.getResult();

                mGroupAdapter.clear();

                mGroupAdapter.addAll(groupList,mGroup);

                mGroupAdapter.notifyDataSetChanged();


            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    class QueryData implements DataCall<Result<List<FindConversationList>>> {

        @Override
        public void success(Result<List<FindConversationList>> result) {

            if (result.getStatus().equals("0000")){
                //Toast.makeText(IMActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();

                List<FindConversationList> conversationList = result.getResult();
                for(FindConversationList conversation:conversationList){
                    conversation.setUserName(conversation.getUserName().toLowerCase());
                }
                DaoUtils.getInstance().getConversationDao().insertOrReplaceInTx(conversationList);

            }
        }

        @Override
        public void fail(ApiException e) {

        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mQueryGroupPresenter=null;
    }
}
