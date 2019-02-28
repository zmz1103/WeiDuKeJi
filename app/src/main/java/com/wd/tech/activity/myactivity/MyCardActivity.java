package com.wd.tech.activity.myactivity;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.wd.tech.bean.CardListData;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.DeletePostPresenter;
import com.wd.tech.presenter.FindMyPostByIdPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCardActivity extends WDActivity implements CardListAdapter.DeletePostInterface {


    private FindMyPostByIdPresenter mFindMyPostByIdPresenter;

    private int mPage = 1;
    @BindView(R.id.my_card_layout)
    SmartRefreshLayout mLayout;
    @BindView(R.id.my_card_rec)
    RecyclerView mRec;

    private CardListAdapter cardListAdapter;
    private DeletePostPresenter mDeletePostPresenter;

    @OnClick(R.id.my_back_gz)
    void daA(){
        finish();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_card;
    }

    @Override
    protected void initView() {
        // 删除帖子
        mFindMyPostByIdPresenter = new FindMyPostByIdPresenter(new findPost());
//
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
                cardListAdapter.clear();
                refreshlayout.finishRefresh();
            }
        });
        mLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                requestt(mPage);
                cardListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });
        cardListAdapter = new CardListAdapter(this);
        cardListAdapter.setmDeletePostInterface(this);
        mRec.setLayoutManager(new LinearLayoutManager(this));
        mRec.setAdapter(cardListAdapter);
    }

    private void requestt(int page) {
        if (user == null) {
            mFindMyPostByIdPresenter.reqeust(0, "", page, 10);
        } else {
            mFindMyPostByIdPresenter.reqeust(user.getUserId(), user.getSessionId(), page, 10);
        }
    }

    @Override
    protected void destoryData() {
        mFindMyPostByIdPresenter = null;
        mDeletePostPresenter = null;
    }

    @Override
    public void onclick(final int i) {

        mDeletePostPresenter = new DeletePostPresenter(new deletePost());

        View popView = View.inflate(MyCardActivity.this, R.layout.my_icon_update, null);
        final PopupWindow popWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popWindow.setTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        final View inflate = LayoutInflater.from(this).inflate(R.layout.activity_my_card, null);

        popWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);

        popView.findViewById(R.id.btn_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mDeletePostPresenter.reqeust(user.getUserId(),user.getSessionId(),String.valueOf(i));
            }
        });
        popView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });


    }

    private class findPost implements DataCall<Result<List<CardListData>>> {
        @Override
        public void success(Result<List<CardListData>> result) {
            Toast.makeText(MyCardActivity.this, "" + result.getMessage() + result.getResult().size(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                cardListAdapter.setmListData(result.getResult());
                cardListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class deletePost implements DataCall<Result> {
        @Override
        public void success(Result result) {
            Toast.makeText(MyCardActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
            cardListAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
