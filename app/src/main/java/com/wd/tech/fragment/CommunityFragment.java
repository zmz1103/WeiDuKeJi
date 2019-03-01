package com.wd.tech.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.wd.tech.activity.FriendsPostActivity;
import com.wd.tech.activity.PublishActivity;
import com.wd.tech.activity.myactivity.SignActivity;
import com.wd.tech.adapter.CommunityListAdapter;
import com.wd.tech.bean.CommunitylistData;
import com.wd.tech.bean.Result;
import com.wd.tech.dao.UserDao;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.AddCommunityPresenter;
import com.wd.tech.presenter.CancelLikePresenter;
import com.wd.tech.presenter.CommunityListPresenter;
import com.wd.tech.presenter.DoTheTastPresenter;
import com.wd.tech.presenter.LikePresenter;
import com.wd.tech.view.DataCall;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * date:2019/2/19 13:53
 * author:王思敏
 * function:社区列表展示
 */
public class CommunityFragment extends WDFragment implements CustomAdapt{
    @BindView(R.id.community_recy)
    RecyclerView communityRecy;
    @BindView(R.id.btn_publish_the_news)
    LinearLayout btnPublishTheNews;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshLayout;
    private CommunityListAdapter mCommunityListAdapter;
    private CommunityListPresenter mCommunityListPresenter;
    private int page = 1;
    private LikePresenter mLikePresenter;
    private CancelLikePresenter mCancelLikePresenter;
    private TextView mSend;
    private EditText mEtContent;
    private AddCommunityPresenter mAddCommunityPresenter;
    private DoTheTastPresenter mDoTheTastPresenter;
    private List<CommunitylistData> list;

    @Override
    public int getContent() {
        return R.layout.activity_community_fragment;
    }

    @Override
    public void initView(View view) {
        //请求数据
        initData();
    }

    private void initData() {
        mCommunityListAdapter = new CommunityListAdapter(getActivity());
        communityRecy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        communityRecy.setAdapter(mCommunityListAdapter);
        //刷新数据
        initRefresh();
        //点击监听
        initListener();
    }

    private void initRefresh() {
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        //列表
        mCommunityListPresenter = new CommunityListPresenter(new CommunityCall());
        //点赞
        mLikePresenter = new LikePresenter(new LikeCall());
        //取消点赞
        mCancelLikePresenter = new CancelLikePresenter(new CancelLike());
        //评论
        mAddCommunityPresenter = new AddCommunityPresenter(new AddCommunity());
        //做任务首评
        mDoTheTastPresenter = new DoTheTastPresenter(new doTheTask());
        requestt(page);
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadmore(true);//启用加载
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                requestt(page);
                mCommunityListAdapter.clear();
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestt(page);
                mCommunityListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });
    }

    private void requestt(int page) {
        if (user==null){
            mCommunityListPresenter.reqeust(0, "", page, 10);
        }else {
            mCommunityListPresenter.reqeust((int)user.getUserId(), user.getSessionId(), page, 10);
        }
    }

    private void initListener() {
        mCommunityListAdapter.setOnCommunityListClickListener(new CommunityListAdapter.onCommunityListClickListener() {

            @Override
            public void onmHeadPicClick(int userid) {
                if (user==null){
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getActivity(), FriendsPostActivity.class);
                    intent.putExtra("userid", userid);
                    startActivity(intent);
                }
            }

            @Override
            public void onmCommentClick(final int id, String name) {
                if (user==null){
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }else {
                    View inflate = View.inflate(getActivity(), R.layout.pop_comment, null);
                   mEtContent= inflate.findViewById(R.id.et_content);
                    mEtContent.setHint(name);
                    final Dialog builder = new Dialog(getActivity(), R.style.BottomDialog);
                    builder.setContentView(inflate);
                    builder.setCanceledOnTouchOutside(true);
                    ViewGroup.LayoutParams layoutParamsthreefilmreview = inflate.getLayoutParams();
                    layoutParamsthreefilmreview.width = getResources().getDisplayMetrics().widthPixels;
                    inflate.setLayoutParams(layoutParamsthreefilmreview);
                    builder.getWindow().setGravity(Gravity.BOTTOM);
                    builder.show();
                   mSend =  inflate.findViewById(R.id.send);
                    mSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = mEtContent.getText().toString().trim();

                            if (s.isEmpty()) {
                                Toast.makeText(getContext(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                mAddCommunityPresenter.reqeust((int)user.getUserId(),user.getSessionId(),id,s);
                                builder.dismiss();
                            }
                        }
                    });
                }
            }

            @Override
            public void onmPraiseClick(int id,int whetherGreat) {
                if (user ==null){
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }else {
                    if (whetherGreat == 1) {
                        mCancelLikePresenter.reqeust((int)user.getUserId(),user.getSessionId(),id);
                    } else {
                        mLikePresenter.reqeust((int)user.getUserId(),user.getSessionId(),id);
                    }
                }
            }
        });
    }



    @OnClick(R.id.btn_publish_the_news)
    public void onViewClicked() {
        if (user==null){
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(getActivity(), PublishActivity.class);
            startActivity(intent);
        }
    }

    //请求展示列表数据
    class CommunityCall implements DataCall<Result<List<CommunitylistData>>> {

        @Override
        public void success(Result<List<CommunitylistData>> result) {

            if (result.getStatus().equals("0000")) {
                mCommunityListAdapter.addAll(result.getResult());
                mCommunityListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
        }
    }
    //评论点赞
    class LikeCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Log.i("success",data.getMessage());
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }
    //取消点赞
    class CancelLike implements DataCall<Result>{

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                Log.i("success",result.getMessage());
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
                Toast.makeText(getActivity(), ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                mCommunityListAdapter.clear();
                mCommunityListPresenter.reqeust((int)user.getUserId(), user.getSessionId(), 1, 10);
                mDoTheTastPresenter.reqeust(user.getUserId(),user.getSessionId(),1002);
            }
        }

        @Override
        public void fail(ApiException e) {
        }
    }

    //做任务评论
    private class doTheTask implements DataCall<Result> {
        @Override
        public void success(Result result) {
            Log.i("success",result.getMessage());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mCommunityListAdapter.clear();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCommunityListPresenter=null;
        mLikePresenter=null;
        mCancelLikePresenter=null;
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
