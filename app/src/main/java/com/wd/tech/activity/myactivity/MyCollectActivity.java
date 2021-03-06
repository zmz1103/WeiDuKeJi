package com.wd.tech.activity.myactivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.wd.tech.adapter.CollectListAdapter;
import com.wd.tech.bean.CollectDataList;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.CancelCollectionPresenter;
import com.wd.tech.presenter.FindAllInfoCillectionPresenter;
import com.wd.tech.view.DataCall;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCollectActivity extends WDActivity {

    private FindAllInfoCillectionPresenter mFindAllInfoCillectionPresenter;
    private int mPage = 1;
    private CollectListAdapter mCollectListAdapter;
    private CancelCollectionPresenter cancelCollectionPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    private List<CollectDataList> mLists;
    @BindView(R.id.collect_xrec_list)
    SmartRefreshLayout mXRecyclerView;

    @BindView(R.id.co_listView)
    RecyclerView mRec;

    @BindView(R.id.cancel_image_delete)
    ImageView cancel_image_delete;
    @BindView(R.id.cancel_text_collect)
    TextView cancel_text_collect;

    Boolean mBoolean = false;

    @Override
    protected void initView() {
        mLists = new ArrayList<>();
        mFindAllInfoCillectionPresenter = new FindAllInfoCillectionPresenter(new findAll());
        cancelCollectionPresenter = new CancelCollectionPresenter(new cancenCollect());
        mXRecyclerView.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
//设置 Footer 为 球脉冲 样式
        mXRecyclerView.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        requestt(mPage);
        mXRecyclerView.setEnableRefresh(true);
        //启用刷新
        mXRecyclerView.setEnableLoadmore(true);
        mCollectListAdapter = new CollectListAdapter(this, mLists);
        //启用加载
        mXRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                requestt(mPage);
                mLists.clear();
                refreshlayout.finishRefresh();
            }
        });
        mXRecyclerView.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                requestt(mPage);
                mCollectListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });
        mRec.setLayoutManager(new LinearLayoutManager(this));
        mRec.setAdapter(mCollectListAdapter);
    }

    private void requestt(int page) {
        if (user == null) {
            mFindAllInfoCillectionPresenter.reqeust(0L, "", page, 35);
        } else {
            mFindAllInfoCillectionPresenter.reqeust(user.getUserId(), user.getSessionId(), page, 35);
        }
    }

    @Override
    protected void destoryData() {
        mFindAllInfoCillectionPresenter=null;
        cancelCollectionPresenter=null;
    }

    @OnClick({R.id.cancel_text_collect, R.id.cancel_image_delete, R.id.my_back_sc})
    void oDian(View view) {
        switch (view.getId()) {
            case R.id.cancel_image_delete:
                cancel_text_collect.setVisibility(View.VISIBLE);
                cancel_image_delete.setVisibility(View.GONE);

                for (int i = 0; i < mLists.size(); i++) {
                    mLists.get(i).setKan(true);
                }
                mCollectListAdapter.notifyDataSetChanged();
                break;
            case R.id.cancel_text_collect:
                cancel_text_collect.setVisibility(View.GONE);
                cancel_image_delete.setVisibility(View.VISIBLE);

                String id = "";

                for (int i = 0; i < mLists.size(); i++) {
                    boolean ischeck = mLists.get(i).isFlag();
                    if (ischeck) {
                        int infoId1 = mLists.get(i).getInfoId();
                        id += infoId1 + ",";
                    }
                }
                if (id.equals("")) {

                } else {
                    cancelCollectionPresenter.reqeust(user.getUserId(), user.getSessionId(), id);
                }

                //
//                  id = mCollectListAdapter.getId();
//                if (id.length() >= 1) {
//                    cancelCollectionPresenter.reqeust(user.getUserId(), user.getSessionId(), id);
//                }
                for (int i = 0; i < mLists.size(); i++) {
                    mLists.get(i).setKan(false);
                }
                for (int i = 0; i < mLists.size(); i++) {
                    mLists.get(i).setFlag(false);
                }
                mCollectListAdapter.notifyDataSetChanged();
                break;
            case R.id.my_back_sc:
                finish();
                break;
            default:
                break;
        }
    }


    private class findAll implements DataCall<Result<List<CollectDataList>>> {
        @Override
        public void success(Result<List<CollectDataList>> result) {

            if (result.getStatus().equals("0000")) {
                for (int i = 0; i < result.getResult().size(); i++) {
                     result.getResult().get(i).setFlag(false) ;
                }
                mLists.addAll(result.getResult());

                mCollectListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class cancenCollect implements DataCall<Result> {
        @Override
        public void success(Result result) {

            if (result.getStatus().equals("0000")) {
                requestt(mPage);
                mCollectListAdapter.clear();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
