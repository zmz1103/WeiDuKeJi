package com.wd.tech.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.adapter.FriendAdapter;
import com.wd.tech.bean.FuzzyQuery;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FuzzyQueryPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/2/27 18:42
 * author:赵明珠(啊哈)
 * function:模糊查询好友
 */
public class SearchMyFriendActivity extends WDActivity {

    FuzzyQueryPresenter mFuzzyQueryPresenter;
    @BindView(R.id.my_friend_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_my_friend)
    EditText mEditext;
    private FriendAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_serach_my_friend;
    }

    @Override
    protected void initView() {

        mFuzzyQueryPresenter = new FuzzyQueryPresenter(new QueryCall());

        mAdapter = new FriendAdapter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));

        mRecyclerView.setAdapter(mAdapter);

        mEditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = mEditext.getText().toString();

                if (!name.isEmpty()) {
                    int userId = (int) user.getUserId();
                    String sessionId = user.getSessionId();
                    mFuzzyQueryPresenter.reqeust(userId,sessionId,name);
                }else {
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });

    }

    @OnClick({R.id.delete,R.id.cancel})
    public void onClick(View view){
        if (view.getId() == R.id.delete){
            mEditext.setText("");
        }
        if (view.getId() == R.id.cancel){
            finish();
        }
    }

    @Override
    protected void destoryData() {
        mFuzzyQueryPresenter.unBind();
    }

    private class QueryCall implements DataCall<Result<List<FuzzyQuery>>> {
        @Override
        public void success(Result<List<FuzzyQuery>> result) {
            if (result.getStatus().equals("0000")) {

                List<FuzzyQuery> mResult = result.getResult();

                mAdapter.clear();

                mAdapter.addAll(mResult);
                Log.e("zmz","查询");
                mAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
