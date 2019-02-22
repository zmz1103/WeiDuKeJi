package com.wd.tech.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.adapter.FriendsPostAdapter;
import com.wd.tech.bean.CommunityUserPostVoListBean;
import com.wd.tech.bean.CommunityUserVoBean;
import com.wd.tech.bean.FriendsPostData;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FriendsPostPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class FriendsPostActivity extends WDActivity implements CustomAdapt{

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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.txt_friend)
    TextView txtFriend;
    @BindView(R.id.txt_attention)
    TextView txtAttention;
    private String mHeadPic;
    private String mNickName;
    private String mSignature;
    private int mUserid;
    private int mWhetherFollow;
    private int mWhetherMyFriend;
    private FriendsPostPresenter mFriendsPostPresenter;
    private FriendsPostAdapter mFriendsPostAdapter;
    private int page = 1;
    private TextView mTxtFriend;
    private TextView mTxtAttention;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friends_post;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //刷新加载
        initRefresh();
        initData();
    }

    private void initRefresh() {

        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(FriendsPostActivity.this).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(FriendsPostActivity.this).setSpinnerStyle(SpinnerStyle.Scale));
        request(page);
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadmore(true);//启用加载

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                request(page);
                mFriendsPostAdapter.clear();
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                request(page);
                mFriendsPostAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });
    }

    private void request(int page) {
        Intent intent = getIntent();
        mUserid = intent.getExtras().getInt("userid");
        mFriendsPostPresenter = new FriendsPostPresenter(new FriendsPost());
        if (user == null){
            mFriendsPostPresenter.reqeust(0, "", mUserid, page, 10);
        }else {
            mFriendsPostPresenter.reqeust((int)user.getUserId(), user.getSessionId(), mUserid, page, 10);
        }
        friendsImageHeadPic.setImageURI(mHeadPic);
        friendsTxtNickName.setText(mNickName);
        friendsTxtSignature.setText(mSignature);
        friendsImageBg.setImageURI(mHeadPic);
    }
    private void initData() {
        mFriendsPostAdapter = new FriendsPostAdapter(this);
        friendsRecy.setAdapter(mFriendsPostAdapter);
        friendsRecy.setLayoutManager(new LinearLayoutManager(this));

    }


    @OnClick({R.id.friends_more, R.id.friends_image_headPic,R.id.txt_friend,R.id.txt_attention})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.friends_more:

                break;
            case R.id.friends_image_headPic:
                Intent intent = new Intent(FriendsPostActivity.this,PictureDisplayActivity.class);
                intent.putExtra("mHeadPic",mHeadPic);
                startActivity(intent);
                break;
            case R.id.txt_friend:
                Toast.makeText(this, "好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txt_attention:
                Toast.makeText(this, "关注", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }



    class FriendsPost implements DataCall<Result<List<FriendsPostData>>> {



        @Override
        public void success(Result<List<FriendsPostData>> result) {
            if (result.getStatus().equals("0000")) {
                List<FriendsPostData> result1 = result.getResult();
                CommunityUserVoBean userVo = result1.get(0).getCommunityUserVo();
                mHeadPic = userVo.getHeadPic();
                mNickName = userVo.getNickName();
                mSignature = userVo.getSignature();
                mWhetherFollow = userVo.getWhetherFollow();
                mWhetherMyFriend = userVo.getWhetherMyFriend();
                Toast.makeText(FriendsPostActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFriendsPostPresenter.unBind();
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
