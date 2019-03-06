package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.adapter.AllCommentAdapter;
import com.wd.tech.bean.AllComment;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.AllCommentPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class AllCommentActivity extends WDActivity implements CustomAdapt {

    @BindView(R.id.image_back)
    ImageView imageBack;
    @BindView(R.id.image_headpic)
    SimpleDraweeView imageHeadpic;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_numcomment)
    TextView txtNumcomment;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recy_comment)
    RecyclerView recyComment;
    private int mCommunityId;
    private AllCommentPresenter mAllCommentPresenter;
    private AllCommentAdapter mAllCommentAdapter;
    private String mComment;
    private String mHeadPic;
    private String mNickName;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_comment;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        Intent intent = getIntent();
        mCommunityId = intent.getExtras().getInt("communityId");
        mComment = intent.getExtras().getString("comment");
        mHeadPic = intent.getExtras().getString("headPic");
        mNickName = intent.getExtras().getString("nickName");
        imageHeadpic.setImageURI(mHeadPic);
        txtName.setText(mNickName);
        txtNumcomment.setText(mComment+"条评论");
        mAllCommentPresenter = new AllCommentPresenter(new AllComment());
        mAllCommentAdapter = new AllCommentAdapter(this);
        recyComment.setAdapter(mAllCommentAdapter);
        recyComment.setLayoutManager(new LinearLayoutManager(this));
        if (user==null){
            mAllCommentPresenter.reqeust(0,"",mCommunityId,page,10);
        }else {
            mAllCommentPresenter.reqeust((int)user.getUserId(),user.getSessionId(),mCommunityId,page,10);

        }
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        requestt(page);
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadmore(true);//启用加载
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                requestt(page);
                mAllCommentAdapter.clear();
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestt(page);
                mAllCommentAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });

    }

    private void requestt(int page) {
        if (user==null){
            mAllCommentPresenter.reqeust(0,"",mCommunityId,page,10);
        }else {
            mAllCommentPresenter.reqeust((int)user.getUserId(),user.getSessionId(),mCommunityId,page,10);

        }
    }



    class AllComment implements DataCall<Result<List<com.wd.tech.bean.AllComment>>>{

        @Override
        public void success(Result<List<com.wd.tech.bean.AllComment>> result) {
            if (result.getStatus().equals("0000")){
                mAllCommentAdapter.setAll(result.getResult());
                mAllCommentAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(AllCommentActivity.this, "失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAllCommentPresenter = null;
    }

    @Override
    protected void destoryData() {

    }


    @OnClick(R.id.image_back)
    public void onViewClicked() {
        finish();
    }
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }
}
