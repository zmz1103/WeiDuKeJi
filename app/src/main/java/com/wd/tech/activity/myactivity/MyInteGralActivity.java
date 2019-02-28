package com.wd.tech.activity.myactivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.wd.tech.adapter.CardListAdapter;
import com.wd.tech.adapter.InteGralListAdapter;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.UserInteGralDataList2;
import com.wd.tech.bean.UserInteGralListData;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FindContinuosSignDaysPresenter;
import com.wd.tech.presenter.FindUserIntegralPresenter;
import com.wd.tech.presenter.FindUserIntegralRecordPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView ;

public class MyInteGralActivity extends WDActivity   {


    private FindUserIntegralPresenter findUserIntegralPresenter;
    private FindContinuosSignDaysPresenter findContinuosSignDaysPresenter;
    private int mPage = 1;
    private FindUserIntegralRecordPresenter findUserIntegralRecordPresenter;
    private InteGralListAdapter inteGralListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_inte_gral;
    }

    @BindView(R.id.jf_user_sign_count)
    TextView mUserSignCount;
    @BindView(R.id.jf_count_textview)
    TextView mUserUseCount;

    @BindView(R.id.jf_smart_layout)
    SmartRefreshLayout mLayout;
    @BindView(R.id.jf_rec)
    RecyclerView mRec;

    @Override
    protected void initView() {
        findUserIntegralPresenter = new FindUserIntegralPresenter(new getIntegralCount());
        findContinuosSignDaysPresenter = new FindContinuosSignDaysPresenter(new getSignDays());
        findUserIntegralRecordPresenter = new FindUserIntegralRecordPresenter(new findUserRecord());
        if (user == null) {

        } else {
            findUserIntegralPresenter.reqeust(user.getUserId(), user.getSessionId());
            findContinuosSignDaysPresenter.reqeust(user.getUserId(), user.getSessionId());
        }
        mLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
//设置 Footer 为 球脉冲 样式
        mLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        requestt(mPage);
        mLayout.setEnableRefresh(true);
        //启用刷新
        mLayout.setEnableLoadmore(true);
        //启用加载
        mLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                requestt(mPage);
                inteGralListAdapter.clear();
                refreshlayout.finishRefresh();
            }
        });
        mLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                requestt(mPage);
                inteGralListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });
        inteGralListAdapter = new InteGralListAdapter(this);
        mRec.setLayoutManager(new LinearLayoutManager(this));
        mRec.setAdapter(inteGralListAdapter);
    }

    private void requestt(int page) {
        if (user == null) {
            findUserIntegralRecordPresenter.reqeust(0, "", page, 10);
        } else {
            findUserIntegralRecordPresenter.reqeust(user.getUserId(), user.getSessionId(), page, 10);
        }
    }

    @Override
    protected void destoryData() {
        findUserIntegralPresenter.unBind();
        findUserIntegralRecordPresenter.unBind();
        findContinuosSignDaysPresenter.unBind();
    }



    private class getIntegralCount implements DataCall<Result<UserInteGralListData>> {
        @Override
        public void success(Result<UserInteGralListData> result) {
            if (result.getStatus().equals("0000")) {
                mUserUseCount.setText(String.valueOf(result.getResult().getAmount()));
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class getSignDays implements DataCall<Result<Integer>> {
        @Override
        public void success(Result<Integer> result) {

            if (result.getStatus().equals("0000")) {
                mUserSignCount.setText("您已连续签到" + result.getResult() + "天");
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class findUserRecord implements DataCall<Result<List<UserInteGralDataList2>>> {
        @Override
        public void success(Result<List<UserInteGralDataList2>> result) {

            if (result.getStatus().equals("0000")) {
                inteGralListAdapter.setListData(result.getResult());
                inteGralListAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
