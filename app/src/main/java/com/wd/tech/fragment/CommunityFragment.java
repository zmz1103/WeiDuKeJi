package com.wd.tech.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.wd.tech.adapter.CommunityListAdapter;
import com.wd.tech.bean.CommunitylistData;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.CommunityListPresenter;
import com.wd.tech.presenter.LikePresenter;
import com.wd.tech.view.DataCall;

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
public class CommunityFragment extends WDFragment implements CustomAdapt {
    @BindView(R.id.community_recy)
    XRecyclerView communityRecy;
    @BindView(R.id.btn_publish_the_news)
    ImageView btnPublishTheNews;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private CommunityListAdapter mCommunityListAdapter;
    private CommunityListPresenter mCommunityListPresenter;
    Unbinder unbinder;
    private int page = 1;
    private LikePresenter mLikePresenter;

    @Override
    public int getContent() {
        return R.layout.activity_community_fragment;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        //刷新加载
        initRefresh();
        //请求数据
        initData();
        //点击监听
        initListener();
    }


    private void initRefresh() {

        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()).setEnableHorizontalDrag(true));
//设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
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
        mCommunityListPresenter = new CommunityListPresenter(new CommunityCall());
        mLikePresenter = new LikePresenter(new LikeCall());
        if (user == null){
            mCommunityListPresenter.reqeust(0, "", page, 5);
        }else {
            mCommunityListPresenter.reqeust((int)user.getUserId(), user.getSessionId(), page, 5);
        }

    }

    private void initData() {
        mCommunityListAdapter = new CommunityListAdapter(getActivity());
        communityRecy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        communityRecy.setAdapter(mCommunityListAdapter);
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
            public void onmCommentClick(int id,CommunitylistData communitylistData) {
                if (user==null){
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onmPraiseClick(int id,CommunitylistData communitylistData) {
                if (user==null){
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }else {
                    if (communitylistData.getWhetherGreat() == 1) {
                    } else {
                        mLikePresenter.reqeust((int)user.getUserId(),user.getSessionId(),id,communitylistData);
                    }
                }
            }
        });
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
            Toast.makeText(getActivity(), "请检查网络连接", Toast.LENGTH_SHORT).show();
        }
    }
    //评论点赞
    class LikeCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                int o = (int) data.getArgs()[1];
                CommunitylistData item = mCommunityListAdapter.getItem(o);
                item.setWhetherGreat(1);
                item.setPraise(item.getPraise()+1);
                mCommunityListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getActivity(), "请检查网络连接", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mCommunityListPresenter.unBind();
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
