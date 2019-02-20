package com.wd.tech.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wd.tech.R;
import com.wd.tech.adapter.InformationAdapter;
import com.wd.tech.bean.BannnerBean;
import com.wd.tech.bean.InformationListBean;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.BannerPresenter;
import com.wd.tech.presenter.InformationListPresenter;
import com.wd.tech.view.DataCall;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * date:2019/2/19 13:49
 * author:李阔(Hypoc7isy语涩)
 * function:
 */
public class HomeFragment extends WDFragment {

    @BindView(R.id.banner)
    MZBannerView banner;
    @BindView(R.id.recyclerlist)
    RecyclerView recyclerlist;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private BannerPresenter mBannerPresenter;
    private InformationListPresenter mInformationListPresenter;
    private InformationAdapter mInformationAdapter;

    @Override
    public int getContent() {
        return R.layout.activity_information_fragment;
    }

    @Override
    public void initView(View view) {
        mBannerPresenter = new BannerPresenter(new showBannerCall());
        mBannerPresenter.reqeust();
        recyclerlist.setLayoutManager(new LinearLayoutManager(getContext(),OrientationHelper.VERTICAL,false));
        mInformationAdapter = new InformationAdapter(getContext());
        recyclerlist.setAdapter(mInformationAdapter);
        mInformationListPresenter = new InformationListPresenter(new showListCall());
        mInformationListPresenter.reqeust(0,"",1,1,10);


    }


    private class showBannerCall implements DataCall<Result<List<BannnerBean>>> {

        private List<BannnerBean> mResult;

        @Override
        public void success(Result<List<BannnerBean>> result) {

            if (result.getStatus().equals("0000")) {
                mResult = result.getResult();
                banner.setIndicatorVisible(false);
                banner.setPages(mResult, new MZHolderCreator<BannerViewHolder>() {
                    @Override
                    public BannerViewHolder createViewHolder() {
                        return new BannerViewHolder();
                    }
                });
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class BannerViewHolder implements MZViewHolder<BannnerBean> {
        private SimpleDraweeView mImageView;
        private TextView mMessage;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = View.inflate(getContext(), R.layout.banner_item, null);
            mImageView = view.findViewById(R.id.banner_image);
            mMessage = view.findViewById(R.id.message);
            return view;
        }

        @Override
        public void onBind(Context context, int i, BannnerBean bannnerBean) {
            mImageView.setImageURI(Uri.parse(bannnerBean.getImageUrl()));
            mMessage.setText(bannnerBean.getTitle());
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


    }

    private class showListCall implements DataCall<Result<List<InformationListBean>>> {

        private List<InformationListBean> mResult;

        @Override
        public void success(Result<List<InformationListBean>> result) {
            mResult = result.getResult();
            mInformationAdapter.reset(mResult);

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
