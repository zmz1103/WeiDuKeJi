package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wd.tech.R;
import com.wd.tech.adapter.InterestAdapter;
import com.wd.tech.bean.InterestListBean;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.InterestListPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InterestActivity extends WDActivity {


    @BindView(R.id.recyclerlist)
    RecyclerView recyclerlist;
    private InterestListPresenter mInterestListPresenter;
    private InterestAdapter mInterestAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_interest;
    }

    @Override
    protected void initView() {

        mInterestAdapter = new InterestAdapter(this);
        mInterestListPresenter = new InterestListPresenter(new InterestCall());
        recyclerlist.setLayoutManager(new GridLayoutManager(this,2,OrientationHelper.VERTICAL,false));
        recyclerlist.setAdapter(mInterestAdapter);
        mInterestListPresenter.reqeust();

        mInterestAdapter.setInterestClick(new InterestAdapter.InterestClick() {
            @Override
            public void success(int id,String name) {
                Log.e("lk","id"+id+"name"+name);
                Intent intent = new Intent(InterestActivity.this,InterestDetailsActivity.class);
                intent.putExtra("title",name+"");
                intent.putExtra("id",id+"");
                startActivity(intent);

            }
        });


    }

    @Override
    protected void destoryData() {
        mInterestListPresenter = null;
    }



    private class InterestCall implements DataCall<Result<List<InterestListBean>>> {

        private List<InterestListBean> mResult;

        @Override
        public void success(Result<List<InterestListBean>> result) {
            if (result.getStatus().equals("0000")) {
                mResult = result.getResult();
                mInterestAdapter.reset(mResult);
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
