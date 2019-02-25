package com.wd.tech.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.wd.tech.bean.CommunitylistData;
import com.wd.tech.bean.FriendsPostData;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.AddCommunityPresenter;
import com.wd.tech.presenter.AddFollowPresenter;
import com.wd.tech.presenter.AddFriendPresenter;
import com.wd.tech.presenter.CancelFollowPresenter;
import com.wd.tech.presenter.CancelLikePresenter;
import com.wd.tech.presenter.FriendsPostPresenter;
import com.wd.tech.presenter.LikePresenter;
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
    RecyclerView friendsRecy;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.txt_friend)
    TextView txtFriend;
    @BindView(R.id.txt_attention)
    TextView txtAttention;
    @BindView(R.id.layout_hide)
    LinearLayout layoutHide;
    @BindView(R.id.relat)
    RelativeLayout relat;
    @BindView(R.id.layout)
    LinearLayout layout;
    private String mHeadPic;
    private String mNickName;
    private String mSignature;
    private int mUserid;
    private int mWhetherFollow;
    private int mWhetherMyFriend;
    private FriendsPostPresenter mFriendsPostPresenter;
    private FriendsPostAdapter mFriendsPostAdapter;
    private int page = 1;
    private LikePresenter mLikePresenter;
    private CancelLikePresenter mCancelLikePresenter;
    private AddFollowPresenter mAddFollowPresenter;
    private CancelFollowPresenter mCancelFollowPresenter;
    private TextView mSend;
    private EditText mEtContent;
    private AddCommunityPresenter mAddCommunityPresenter;

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
        mFriendsPostAdapter = new FriendsPostAdapter(this);
        friendsRecy.setLayoutManager(new LinearLayoutManager(this));
        friendsRecy.setAdapter(mFriendsPostAdapter);
        //刷新加载
        initRefresh();
        initListener();

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
        mLikePresenter = new LikePresenter(new LikeCall());
        mCancelLikePresenter = new CancelLikePresenter(new CancelLike());
        mAddCommunityPresenter = new AddCommunityPresenter(new AddCommunity());
        if (user == null){
            mFriendsPostPresenter.reqeust(0L, "", mUserid, page, 10);
        }else {
            mFriendsPostPresenter.reqeust((int)user.getUserId(), user.getSessionId(), mUserid, page, 10);
        }
    }
    private void initListener() {

        mFriendsPostAdapter.setOnFriendsPostClickListener(new FriendsPostAdapter.OnFriendsPostClickListener() {

            @Override
            public void onCommentClick(final int id) {
                if (user==null){
                    Toast.makeText(FriendsPostActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }else {
                    View inflate = View.inflate(FriendsPostActivity.this, R.layout.pop_comment, null);
                    mEtContent = inflate.findViewById(R.id.et_content);
                    mEtContent.setHint("@"+mNickName+"回复：");
                    final Dialog builder = new Dialog(FriendsPostActivity.this, R.style.BottomDialog);
                    builder.setContentView(inflate);
                    builder.setCanceledOnTouchOutside(true);
                    ViewGroup.LayoutParams layoutParamsthreefilmreview = inflate.getLayoutParams();
                    layoutParamsthreefilmreview.width = getResources().getDisplayMetrics().widthPixels;
                    inflate.setLayoutParams(layoutParamsthreefilmreview);
                    builder.getWindow().setGravity(Gravity.BOTTOM);
                    builder.show();
                    mSend = inflate.findViewById(R.id.send);
                    mSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = mEtContent.getText().toString().trim();

                            if (s.isEmpty()) {
                                Toast.makeText(FriendsPostActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                mAddCommunityPresenter.reqeust((int)user.getUserId(),user.getSessionId(),id,s);
                                builder.dismiss();
                            }
                        }
                    });
                }
            }

            @Override
            public void onPraiseClick(int id,CommunityUserPostVoListBean communityUserPostVoListBean) {
                if (user==null){
                    Toast.makeText(FriendsPostActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }else {
                    if (communityUserPostVoListBean.getWhetherGreat() == 1) {
                        mCancelLikePresenter.reqeust((int)user.getUserId(),user.getSessionId(),id,communityUserPostVoListBean);
                    } else {
                        mLikePresenter.reqeust((int)user.getUserId(),user.getSessionId(),id,communityUserPostVoListBean);
                    }
                }
            }
        });

    }


    @OnClick({R.id.friends_more, R.id.friends_image_headPic,R.id.txt_friend,R.id.txt_attention,R.id.relat,R.id.friends_recy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.friends_more:
                if (layoutHide.getVisibility() == View.GONE){
                    layoutHide.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.relat:
                if (layoutHide.getVisibility() == View.VISIBLE){
                    layoutHide.setVisibility(View.GONE);
                }
                break;
            case R.id.friends_recy:
                if (layoutHide.getVisibility() == View.VISIBLE){
                    layoutHide.setVisibility(View.GONE);
                }
                break;
            case R.id.friends_image_headPic:
                //点击头像查看大图
                Intent intent = new Intent(FriendsPostActivity.this,PictureDisplayActivity.class);
                intent.putExtra("mHeadPic",mHeadPic);
                startActivity(intent);
                break;
            case R.id.txt_friend:
                //加好友
                if (user == null){
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                }else {
                   Intent intent1  = new Intent(FriendsPostActivity.this,AddFriendActivity.class);
                   intent1.putExtra("mUserid",mUserid);
                   startActivity(intent1);
                }

                break;
            case R.id.txt_attention:
                //加关注
                mAddFollowPresenter = new AddFollowPresenter(new AddFollow());
                mCancelFollowPresenter = new CancelFollowPresenter(new CancelFollow());
                if (user == null){
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                }else {
                    if (mWhetherFollow ==1){
                        mCancelFollowPresenter.reqeust((int)user.getUserId(),user.getSessionId(),mUserid);
                    }else {
                        mAddFollowPresenter.reqeust((int)user.getUserId(),user.getSessionId(),mUserid);
                    }
                }
                break;
            default:
                break;
        }
    }

    //查询用户帖子数据
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

                friendsTxtNickName.setText(mNickName);
                friendsImageBg.setImageURI(mHeadPic);
                friendsImageHeadPic.setImageURI(mHeadPic);
                friendsTxtSignature.setText(mSignature);
                if (mWhetherFollow ==1){
                    txtAttention.setText("已关注");
                }else {
                    txtAttention.setText("+关注");
                }

                if (mWhetherMyFriend ==1){
                    txtFriend.setText("好友");
                }else {
                    txtFriend.setText("+好友");
                }


                List<CommunityUserPostVoListBean> postVoList = result.getResult().get(0).getCommunityUserPostVoList();
                mFriendsPostAdapter.setAll(postVoList);
                mFriendsPostAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(FriendsPostActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
        }
    }
    //评论点赞
    class LikeCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                int o = (int) data.getArgs()[3];
                CommunityUserPostVoListBean item = mFriendsPostAdapter.getItem(o);
                item.setWhetherGreat(1);
                item.setPraise(item.getPraise()+1);
                mFriendsPostAdapter.notifyDataSetChanged();
                initView();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(FriendsPostActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
        }
    }
    //取消点赞
    class CancelLike implements DataCall<Result>{

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                int o = (int) result.getArgs()[3];
                CommunityUserPostVoListBean item = mFriendsPostAdapter.getItem(o);
                item.setWhetherGreat(2);
                item.setPraise(item.getPraise()-1);
                mFriendsPostAdapter.notifyDataSetChanged();
                initView();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //关注用户
    class AddFollow implements DataCall<Result>{

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                Toast.makeText(FriendsPostActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                initView();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //取消关注用户
    class CancelFollow implements DataCall<Result>{

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                Toast.makeText(FriendsPostActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                initView();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //评论
    class AddCommunity implements DataCall<Result>{

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                Toast.makeText(FriendsPostActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
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
        mFriendsPostPresenter=null;
        mLikePresenter=null;
        mCancelLikePresenter=null;
        mAddFollowPresenter=null;
        mCancelFollowPresenter=null;
        mAddCommunityPresenter=null;

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
