package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.adapter.InformationAdapter;
import com.wd.tech.bean.InformationListBean;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.User;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.AddCollectionPresenter;
import com.wd.tech.presenter.BannerPresenter;
import com.wd.tech.presenter.CancelCollectionPresenter;
import com.wd.tech.presenter.InformationListPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InterestDetailsActivity extends WDActivity {


    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerlist)
    RecyclerView recyclerlist;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private InformationAdapter mInformationAdapter;
    private Intent mIntent;
    private InformationListPresenter mInformationListPresenter;
    private int page = 1;
    private String mId;
    private String mName;
    private long mUserId;
    private String mSessionId;
    private AddCollectionPresenter mAddCollectionPresenter;
    private CancelCollectionPresenter mCancelCollectionPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_interest_details;
    }

    @Override
    protected void initView() {
        if (userDao.loadAll().size() > 0) {
            List<User> users = userDao.loadAll();
            mUserId = users.get(0).getUserId();
            mSessionId = users.get(0).getSessionId();
        }
        mIntent = getIntent();
        mName = mIntent.getStringExtra("title");
        mId = mIntent.getStringExtra("id");
        Log.e("lk","mid"+mId+"mName"+mName);
        title.setText(mName);
        recyclerlist.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        mInformationAdapter = new InformationAdapter(this);
        recyclerlist.setAdapter(mInformationAdapter);
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        mInformationListPresenter = new InformationListPresenter(new showListCall());
        requestt(page);
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadmore(true);//启用加载


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                requestt(page);
                mInformationAdapter.clear();
                refreshlayout.finishRefresh();

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestt(page);
                mInformationAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });
        mInformationAdapter.setGuangGaoClick(new InformationAdapter.GuangGaoClick() {
            @Override
            public void ggsuccess(String url) {

                mIntent.putExtra("jumpUrl",url);
                startActivity(mIntent);
            }
        });
        mAddCollectionPresenter = new AddCollectionPresenter(new AddCollectionCall());
        mCancelCollectionPresenter = new CancelCollectionPresenter(new CancelCollectionCall());

        mInformationAdapter.setAddgreat(new InformationAdapter.Addcollection() {
            @Override
            public void addsuccess(int id, int whetherCollection) {
                if (userDao.loadAll().size() > 0) {
                    if (whetherCollection == 2) {
                        mAddCollectionPresenter.reqeust(mUserId, mSessionId, id);
                    } else {
                        String mid = String.valueOf(id);
                        mCancelCollectionPresenter.reqeust(mUserId, mSessionId, mid);
                    }

                } else {
                    Toast.makeText(InterestDetailsActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mInformationAdapter.setDetailstiao(new InformationAdapter.Detailstiao() {
            @Override
            public void detalssuccess(int id) {
                Log.e("lk","id===id"+id);
                Intent intent = new Intent(InterestDetailsActivity.this,InformationDetailsActivity.class);
                intent.putExtra("id",id+"");
                startActivity(intent);
            }
        });



    }
    private void requestt(int page) {
        if (userDao.loadAll().size()>0){

            List<User> users = userDao.loadAll();
            mSessionId = users.get(0).getSessionId();
            mUserId = users.get(0).getUserId();
            Log.e("lk", "有 interid"+mUserId);
            mInformationListPresenter.reqeust(mUserId, mSessionId, mId, page, 10);

        }else {
            Log.e("lk", "无 ");
            mInformationListPresenter.reqeust(0L, " ", mId, page, 10);

        }

    }

    @Override
    protected void destoryData() {
        mAddCollectionPresenter = null;
        mCancelCollectionPresenter = null;
        mInformationListPresenter = null;
    }



    @OnClick({R.id.search, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                break;
            case R.id.back:
                finish();
                break;
        }
    }
    private class showListCall implements DataCall<Result<List<InformationListBean>>> {

        private List<InformationListBean> mResult;

        @Override
        public void success(Result<List<InformationListBean>> result) {
            mResult = result.getResult();
            Log.e("lk", "lk" + result.getStatus());
            if (result.getStatus().equals("0000")) {
                mInformationAdapter.reset(mResult);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    private class AddCollectionCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                Toast.makeText(InterestDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InterestDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class CancelCollectionCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                Toast.makeText(InterestDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InterestDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
