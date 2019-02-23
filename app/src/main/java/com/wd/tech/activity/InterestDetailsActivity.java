package com.wd.tech.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wd.tech.R;
import com.wd.tech.adapter.InformationAdapter;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_interest_details;
    }

    @Override
    protected void initView() {
        recyclerlist.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        mInformationAdapter = new InformationAdapter(this);
        recyclerlist.setAdapter(mInformationAdapter);
    }

    @Override
    protected void destoryData() {

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
}
