package com.wd.tech.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.FriendsPostActivity;
import com.wd.tech.activity.PublishActivity;
import com.wd.tech.adapter.CommunityListAdapter;
import com.wd.tech.bean.CommunitylistData;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.CommunityListPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * date:2019/2/19 13:53
 * author:王思敏
 * function:社区列表展示
 */
public class CommunityFragment extends WDFragment implements XRecyclerView.LoadingListener {
    @BindView(R.id.community_recy)
    XRecyclerView communityRecy;
    Unbinder unbinder;
    @BindView(R.id.btn_publish_the_news)
    ImageView btnPublishTheNews;
    private CommunityListAdapter mCommunityListAdapter;
    private CommunityListPresenter mCommunityListPresenter;
    private int page = 1;

    @Override
    public int getContent() {
        return R.layout.activity_community_fragment;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        initData();
    }

    private void initData() {
        mCommunityListAdapter = new CommunityListAdapter(getActivity());
        mCommunityListPresenter = new CommunityListPresenter(new CommunityCall());
        communityRecy.setLoadingListener(this);
        communityRecy.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        communityRecy.setAdapter(mCommunityListAdapter);
        communityRecy.refreshComplete();
        initListener();
        mCommunityListPresenter.reqeust(0,"",page,5);
    }


    @Override
    public void onRefresh() {
        if (mCommunityListPresenter.Running()) {
            communityRecy.refreshComplete();
            return;
        }
        page = 1;
        mCommunityListPresenter.reqeust(0,"",page,5);


        communityRecy.loadMoreComplete();
        communityRecy.refreshComplete();
    }

    @Override
    public void onLoadMore() {
        communityRecy.loadMoreComplete();
        communityRecy.refreshComplete();
        if (mCommunityListPresenter.Running()) {
            communityRecy.loadMoreComplete();
            return;
        }
        page++;
        mCommunityListPresenter.reqeust(0,"",page,5);


    }

    class CommunityCall implements DataCall<Result<List<CommunitylistData>>> {

        @Override
        public void success(Result<List<CommunitylistData>> result) {
            communityRecy.refreshComplete();
            communityRecy.loadMoreComplete();
            if (result.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                mCommunityListAdapter.addAll(result.getResult());
                mCommunityListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            communityRecy.refreshComplete();
            communityRecy.loadMoreComplete();
            Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        communityRecy.refreshComplete();
    }

    @Override
    public void onStart() {
        super.onStart();
        communityRecy.refreshComplete();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mCommunityListPresenter.unBind();
    }

    @OnClick(R.id.btn_publish_the_news)
    public void onViewClicked() {
            Intent intent = new Intent(getActivity(),PublishActivity.class);
            startActivity(intent);
    }

    private void initListener() {
        mCommunityListAdapter.setOnCommunityListClickListener(new CommunityListAdapter.onCommunityListClickListener() {
            @Override
            public void onmHeadPicClick(int id, String HeadPic, String NickName, String Signature) {
                Intent intent = new Intent(getActivity(),FriendsPostActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("HeadPic",HeadPic);
                intent.putExtra("NickName",NickName);
                intent.putExtra("Signature",Signature);
                startActivity(intent);
            }

            @Override
            public void onmCommentClick(int id) {
                Toast.makeText(getActivity(), "评论", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onmPraiseClick(int id) {
                Toast.makeText(getActivity(), "点赞", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
