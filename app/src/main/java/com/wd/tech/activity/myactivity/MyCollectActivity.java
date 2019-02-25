package com.wd.tech.activity.myactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
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
import com.wd.tech.presenter.FindAllInfoCillectionPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCollectActivity extends WDActivity {

    private FindAllInfoCillectionPresenter findAllInfoCillectionPresenter;
    private int page = 1;
    private CollectListAdapter collectListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @BindView(R.id.collect_xrec_list)
    SmartRefreshLayout xRecyclerView;

    @BindView(R.id.co_listView)
    RecyclerView mRec;

    @BindView(R.id.cancel_image_delete)
    ImageView cancel_image_delete;
    @BindView(R.id.cancel_text_collect)
    TextView cancel_text_collect;

    Boolean mBoolean = false;

    @Override
    protected void initView() {
        findAllInfoCillectionPresenter = new FindAllInfoCillectionPresenter(new findAll());

        xRecyclerView.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
//设置 Footer 为 球脉冲 样式
        xRecyclerView.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        requestt(page);
        xRecyclerView.setEnableRefresh(true);
        //启用刷新
        xRecyclerView.setEnableLoadmore(true);
        collectListAdapter = new CollectListAdapter(this);
        //启用加载
        xRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                requestt(page);
                collectListAdapter.clear();
                refreshlayout.finishRefresh();
            }
        });
        xRecyclerView.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestt(page);
                collectListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });
        mRec.setLayoutManager(new LinearLayoutManager(this));
        mRec.setAdapter(collectListAdapter);
    }

    private void requestt(int page) {
        if (user == null) {
            findAllInfoCillectionPresenter.reqeust(0, "", page, 5);
        } else {
            findAllInfoCillectionPresenter.reqeust(user.getUserId(), user.getSessionId(), page, 5);
        }
    }

    @Override
    protected void destoryData() {
        findAllInfoCillectionPresenter.unBind();
    }


    @OnClick({R.id.cancel_text_collect, R.id.cancel_image_delete,R.id.my_back_sc})
    void oDian(View view) {
        switch (view.getId()) {
            case R.id.cancel_image_delete:
                if (mBoolean) {
                    cancel_image_delete.setVisibility(View.VISIBLE);
                    cancel_text_collect.setVisibility(View.GONE);
                    mBoolean = false;
                } else {
                    cancel_text_collect.setVisibility(View.VISIBLE);
                    cancel_image_delete.setVisibility(View.GONE);
                    mBoolean = true;
                }
                break;
            case R.id.cancel_text_collect:
                if (mBoolean) {
                    cancel_image_delete.setVisibility(View.VISIBLE);
                    cancel_text_collect.setVisibility(View.GONE);
                    mBoolean = false;
                } else {
                    cancel_text_collect.setVisibility(View.VISIBLE);
                    cancel_image_delete.setVisibility(View.GONE);
                    mBoolean = true;
                }
                break;
            case R.id.my_back_sc:
                finish();
                break;
        }
    }

    private class findAll implements DataCall<Result<List<CollectDataList>>> {
        @Override
        public void success(Result<List<CollectDataList>> result) {
            Toast.makeText(MyCollectActivity.this, "" + result.getMessage() + result.getResult().size(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                collectListAdapter.setLists(result.getResult());
                collectListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
