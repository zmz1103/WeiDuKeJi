package com.wd.tech.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.transition.Transition;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.drawee.view.SimpleDraweeView;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.tech.R;
import com.wd.tech.activity.InformationDetailsActivity;
import com.wd.tech.activity.IntegralActivity;
import com.wd.tech.activity.InterestActivity;
import com.wd.tech.activity.OpenVipActivity;
import com.wd.tech.activity.SearchActivity;
import com.wd.tech.activity.WebDetailsActivity;
import com.wd.tech.adapter.InformationAdapter;
import com.wd.tech.bean.BannnerBean;
import com.wd.tech.bean.InformationListBean;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.Transfer;
import com.wd.tech.bean.User;
import com.wd.tech.dao.UserDao;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.AddCollectionPresenter;
import com.wd.tech.presenter.AddGreatPresenter;
import com.wd.tech.presenter.BannerPresenter;
import com.wd.tech.presenter.CancelCollectionPresenter;
import com.wd.tech.presenter.InfoShareNum;
import com.wd.tech.presenter.InformationListPresenter;
import com.wd.tech.presenter.WxSharePresenter;
import com.wd.tech.util.MD5Utils;
import com.wd.tech.util.Util;
import com.wd.tech.util.WxShareUtils;
import com.wd.tech.view.DataCall;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * date:2019/2/19 13:49
 * author:李阔(Hypoc7isy语涩)
 * function:
 */
public class HomeFragment extends WDFragment implements CustomAdapt {

    @BindView(R.id.banner)
    MZBannerView banner;
    @BindView(R.id.recyclerlist)
    RecyclerView recyclerlist;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.menu)
    ImageView menu;
    private BannerPresenter mBannerPresenter;
    private InformationListPresenter mInformationListPresenter;
    private InformationAdapter mInformationAdapter;

    private long userId;
    private String sessionId;
    private int page = 1;
    private Intent mIntent;
    private AddCollectionPresenter mAddCollectionPresenter;
    private CancelCollectionPresenter mCancelCollectionPresenter;
    private List<InformationListBean> mResult;
    private InfoShareNum mInfoShareNum;
    private int mInt;
    private int mI;
    private WxSharePresenter mWxSharePresenter;
    private long mLong;
    private String mJIA;
    private String mMD5;
    private IWXAPI mWxApi;
    private String[] mSplit;
    private Dialog mDialog;
    private String mId;


    @Override
    public int getContent() {
        return R.layout.activity_information_fragment;
    }

    @Override
    public void initView(View view) {
        mWxApi = WXAPIFactory.createWXAPI(getContext(), "wx4c96b6b8da494224", true);
        mWxApi.registerApp("wx4c96b6b8da494224");
        if (userDao.loadAll().size() > 0) {
            List<User> users = userDao.loadAll();
            userId = users.get(0).getUserId();
            sessionId = users.get(0).getSessionId();
        }


        mIntent = new Intent(getContext(), WebDetailsActivity.class);

        /**
         * 分享
         */
        mWxSharePresenter = new WxSharePresenter(new WxShareCall());
        mInfoShareNum = new InfoShareNum(new ShareCall());


        recyclerlist.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        mInformationAdapter = new InformationAdapter(getContext());
        recyclerlist.setAdapter(mInformationAdapter);
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        mBannerPresenter = new BannerPresenter(new showBannerCall());
        mInformationListPresenter = new InformationListPresenter(new showListCall());
        requestt(page);
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadmore(true);//启用加载


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                requestt(page);
                mInformationAdapter.clear();
                refreshlayout.finishRefresh();

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestt(page);
                mInformationAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });
        mInformationAdapter.setGuangGaoClick(new InformationAdapter.GuangGaoClick() {
            @Override
            public void ggsuccess(String url) {

                mIntent.putExtra("jumpUrl", url);
                startActivity(mIntent);
            }
        });
        mAddCollectionPresenter = new AddCollectionPresenter(new AddCollectionCall());
        mCancelCollectionPresenter = new CancelCollectionPresenter(new CancelCollectionCall());

        mInformationAdapter.setAddgreat(new InformationAdapter.Addcollection() {
            @Override
            public void addsuccess(int id, int whetherCollection,int i) {
                if (userDao.loadAll().size() > 0) {
                    mI = i;
                    if (whetherCollection == 2) {

                        mAddCollectionPresenter.reqeust(userId, sessionId, id);
                    } else {
                        String mid = String.valueOf(id);
                        mCancelCollectionPresenter.reqeust(userId, sessionId, mid);
                    }

                } else {
                    Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mInformationAdapter.setDetailstiao(new InformationAdapter.Detailstiao() {
            @Override
            public void detalssuccess(int id, String title, String neirong, String laiyuan, String tupian, long time, int shoucang,int shoucangshu,int shareshu) {
                Intent intent = new Intent(getContext(),InformationDetailsActivity.class);
                intent.setClass(getContext(),InformationDetailsActivity.class);
                Transfer mTransfer = new Transfer();
                mTransfer.setTitle(title);
                mTransfer.setNeirong(neirong);
                mTransfer.setLaiyuan(laiyuan);
                mTransfer.setTupian(tupian);
                mTransfer.setTime(time);
                mTransfer.setShoucang(shoucang);
                mTransfer.setShoucangshu(shoucangshu);
                mTransfer.setShareshu(shareshu);
                intent.putExtra("id",id+"");
                intent.putExtra("mTransfer",mTransfer);
                startActivity(intent);
            }
        });


        mInformationAdapter.setSharefenxiang(new InformationAdapter.Sharefenxiang() {
            @Override
            public void sharessuccess(int id,int i) {
                mInt = i;
                mId = String.valueOf(id);

                mLong = System.currentTimeMillis();
                mJIA = mLong + "wxShare" + "tech";
                mMD5 = MD5Utils.MD5(mJIA);
                mWxSharePresenter.reqeust(mLong,mMD5);
                mInfoShareNum.reqeust(mId);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBannerPresenter = null;
        mInformationListPresenter = null;
    }

    private void requestt(int page) {
        if (userDao.loadAll().size() > 0) {

            List<User> users = userDao.loadAll();
            long userId = users.get(0).getUserId();
            String sessionId = users.get(0).getSessionId();
            mBannerPresenter.reqeust();
            mInformationListPresenter.reqeust(userId, sessionId, "0", page, 10);


        } else {
            Log.e("lk", "无 ");
            mBannerPresenter.reqeust();
            mInformationListPresenter.reqeust(0L, " ", "0", page, 10);

        }

    }


    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }


    @OnClick({R.id.search, R.id.menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.menu:
                menus();
                break;
        }
    }

    private void menus() {
        startActivity(new Intent(getContext(), InterestActivity.class));
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
                banner.start();
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
        public void onBind(Context context, int i, final BannnerBean bannnerBean) {
            mImageView.setImageURI(Uri.parse(bannnerBean.getImageUrl()));
            mMessage.setText(bannnerBean.getTitle());
            mImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mIntent.putExtra("jumpUrl", bannnerBean.getJumpUrl());
                    startActivity(mIntent);
                }
            });
        }


    }

    private class showListCall implements DataCall<Result<List<InformationListBean>>> {



        @Override
        public void success(Result<List<InformationListBean>> result) {
            mResult = result.getResult();
            Log.e("lk", "lk" + result.getStatus());
            if (result.getStatus().equals("0000")) {
                mInformationAdapter.reset(mResult);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }


    private class AddCollectionCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {

                mInformationAdapter.notifyItemChanged(mI);

                Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class CancelCollectionCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                mInformationAdapter.notifyItemChanged(mI);
                Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class ShareCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                mInformationAdapter.notifyItemChanged(mInt);
                //Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("lk","调用了分享接口");
                String thumbnail = mResult.get(mInt).getThumbnail();
                mSplit = thumbnail.split("\\?");
                if (result.getStatus().equals("0000")){
                    WeChatShare();
                }else {

                }
            }else {
                //Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class WxShareCall implements DataCall<Result> {
        @Override
        public void success(Result result) {




        }

        @Override
        public void fail(ApiException e) {

        }
    }
    //分享链接
    public void WeChatShare() {

        View mView = View.inflate(getContext(), R.layout.share, null);
        mDialog = new Dialog(getContext(), R.style.BottomDialog);
        mDialog.setContentView(mView);
        mDialog.setCanceledOnTouchOutside(true);
        ViewGroup.LayoutParams layoutParamsthreefilmreview = mView.getLayoutParams();
        layoutParamsthreefilmreview.width = getResources().getDisplayMetrics().widthPixels;
        mView.setLayoutParams(layoutParamsthreefilmreview);
        mDialog.getWindow().setGravity(Gravity.BOTTOM);
        mDialog.show();

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.baidu.com";
        WXMediaMessage msg = new WXMediaMessage();
        msg.title = mResult.get(mI).getTitle();
        msg.description = mResult.get(mI).getSummary();
        msg.mediaObject = webpage;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        initSend(req,mView);

    }


    private void initSend(final SendMessageToWX.Req req, View popView) {
        /*Button mHaoYou = popView.findViewById(R.id.shareWXSceneSession);
        Button mCircle = popView.findViewById(R.id.shareWXSceneTimeline);*/

        RelativeLayout mHaoYou = popView.findViewById(R.id.shareWXSceneSessionz);
        RelativeLayout mCircle = popView.findViewById(R.id.shareWXSceneTimelinez);
        TextView mCancel = popView.findViewById(R.id.cancelbbb);
        mHaoYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("lk", "onClick: 点击了微信分享" );
                req.scene = SendMessageToWX.Req.WXSceneSession;
                mWxApi.sendReq(req);
                mDialog.dismiss();
            }
        });
        mCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("lk", "onClick: 点击了朋友圈" );
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                mWxApi.sendReq(req);
                mDialog.dismiss();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("lk", "onClick: 点击了关闭了" );
                mDialog.dismiss();
            }
        });


      //  req.scene = SendMessageToWX.Req.WXSceneSession;    //设置发送到朋友
 //       req.scene = SendMessageToWX.Req.WXSceneTimeline;    //设置发送到朋友圈
        //req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


}
