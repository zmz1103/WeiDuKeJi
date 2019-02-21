package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.adapter.FriendsPostAdapter;
import com.wd.tech.bean.CommunityUserPostVoListBean;
import com.wd.tech.bean.FriendsPostData;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FriendsPostPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendsPostActivity extends WDActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.friends_image_bg)
    SimpleDraweeView friendsImageBg;
    @BindView(R.id.friends_image_headPic)
    SimpleDraweeView friendsImageHeadPic;
    @BindView(R.id.friends_txt_nickName)
    TextView friendsTxtNickName;
    @BindView(R.id.friends_txt_signature)
    TextView friendsTxtSignature;
    @BindView(R.id.friends_more)
    ImageView friendsMore;
    @BindView(R.id.friends_recy)
    XRecyclerView friendsRecy;
    private int mId;
    private String mHeadPic;
    private String mNickName;
    private String mSignature;
    private FriendsPostPresenter mFriendsPostPresenter;
    private FriendsPostAdapter mFriendsPostAdapter;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friends_post;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        mId = intent.getExtras().getInt("id");
        mHeadPic = intent.getExtras().getString("HeadPic");
        mNickName = intent.getExtras().getString("NickName");
        mSignature = intent.getExtras().getString("Signature");
        friendsImageBg.setImageURI(mHeadPic);
        friendsImageHeadPic.setImageURI(mHeadPic);
        friendsTxtNickName.setText(mNickName);
        friendsTxtSignature.setText(mSignature);

        mFriendsPostPresenter = new FriendsPostPresenter(new FriendsPost());
        mFriendsPostAdapter = new FriendsPostAdapter(this);
        friendsRecy.setAdapter(mFriendsPostAdapter);
        friendsRecy.setLayoutManager(new LinearLayoutManager(this));
        if (user == null){
            mFriendsPostPresenter.reqeust(0,"",mId,page,10);
        }else {
            mFriendsPostPresenter.reqeust(user.getUserId(),user.getSessionId(),mId,page,10);
        }
        friendsRecy.setLoadingListener(this);
    }

    @Override
    public void onRefresh() {
        if (mFriendsPostPresenter.Running()){
            friendsRecy.refreshComplete();
            return;
        }
        page=1;

    }

    @Override
    public void onLoadMore() {
        if (mFriendsPostPresenter.Running()){
            friendsRecy.refreshComplete();
            return;
        }
        page++;
    }

    class FriendsPost implements DataCall<Result<List<FriendsPostData>>>{

        @Override
        public void success(Result<List<FriendsPostData>> result) {
            if (result.getStatus().equals("0000")){
                Toast.makeText(FriendsPostActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                List<CommunityUserPostVoListBean> postVoList = result.getResult().get(0).getCommunityUserPostVoList();
                mFriendsPostAdapter.setAll(postVoList);
                mFriendsPostAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(FriendsPostActivity.this, "失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.friends_more)
    public void onViewClicked() {

    }
}
