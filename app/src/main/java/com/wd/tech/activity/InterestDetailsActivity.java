package com.wd.tech.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.tech.R;
import com.wd.tech.adapter.InformationAdapter;
import com.wd.tech.application.WDApplication;
import com.wd.tech.bean.InformationListBean;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.Transfer;
import com.wd.tech.bean.User;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.AddCollectionPresenter;
import com.wd.tech.presenter.BannerPresenter;
import com.wd.tech.presenter.CancelCollectionPresenter;
import com.wd.tech.presenter.InfoShareNum;
import com.wd.tech.presenter.InformationListPresenter;
import com.wd.tech.presenter.WxSharePresenter;
import com.wd.tech.util.MD5Utils;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InterestDetailsActivity extends WDActivity {


    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerlist)
    RecyclerView recyclerlist;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private InformationAdapter mInformationAdapter;
    private Intent mIntent;
    private InformationListPresenter mInformationListPresenter;
    private int page = 1;
    private String mId;
    private String mName;
    private long mUserId;
    private String mSessionId;
    private AddCollectionPresenter mAddCollectionPresenter;
    private CancelCollectionPresenter mCancelCollectionPresenter;
    private WxSharePresenter mWxSharePresenter;
    private InfoShareNum mInfoShareNum;
    private int mInt;
    private long mLong;
    private String mJIA;
    private String mMD5;
    private List<InformationListBean> mResult;
    private String[] mSplit;
    private Dialog mDialog;
    private IWXAPI mWxApi;
    private int mI;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_interest_details;
    }

    @Override
    protected void initView() {
        mWxApi = WXAPIFactory.createWXAPI(this, "wx4c96b6b8da494224", true);
        mWxApi.registerApp("wx4c96b6b8da494224");
        if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
            List<User> users = WDApplication.getAppContext().getUserDao().loadAll();
            mUserId = users.get(0).getUserId();
            mSessionId = users.get(0).getSessionId();
        }
        mIntent = getIntent();
        mName = mIntent.getStringExtra("title");
        mId = mIntent.getStringExtra("id");
        Log.e("lk","mid"+mId+"mName"+mName);
        title.setText(mName);
        recyclerlist.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        mInformationAdapter = new InformationAdapter(this);
        recyclerlist.setAdapter(mInformationAdapter);
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
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

                mIntent.putExtra("jumpUrl",url);
                startActivity(mIntent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InterestDetailsActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 分享
         */
        mWxSharePresenter = new WxSharePresenter(new WxShareCall());
        mInfoShareNum = new InfoShareNum(new ShareCall());

        mAddCollectionPresenter = new AddCollectionPresenter(new AddCollectionCall());
        mCancelCollectionPresenter = new CancelCollectionPresenter(new CancelCollectionCall());

        mInformationAdapter.setAddcollection(new InformationAdapter.Addcollection() {
            @Override
            public void addsuccess(int id, int whetherCollection,int i) {

                if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
                    mI = i;
                    if (whetherCollection == 2) {
                        mAddCollectionPresenter.reqeust(mUserId, mSessionId, id);
                    } else {
                        String mid = String.valueOf(id);
                        mCancelCollectionPresenter.reqeust(mUserId, mSessionId, mid);
                    }

                } else {
                    Toast.makeText(InterestDetailsActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mInformationAdapter.setDetailstiao(new InformationAdapter.Detailstiao() {
            @Override
            public void detalssuccess(int id, String title, String neirong, String laiyuan, String tupian, long time, int shoucang,int shoucangshu,int shareshu) {
                Log.e("lk","id===id"+id);
                Intent intent = new Intent(InterestDetailsActivity.this,InformationDetailsActivity.class);
                Transfer mTransfer = new Transfer();
                mTransfer.setTitle(title);
                mTransfer.setNeirong(neirong);
                mTransfer.setLaiyuan(laiyuan);
                mTransfer.setTupian(tupian);
                mTransfer.setTime(time);
                mTransfer.setShoucang(shoucang);
                mTransfer.setShoucangshu(shoucangshu);
                mTransfer.setShareshu(shareshu);
                intent.putExtra("mTransfer",mTransfer);
                intent.putExtra("id",id+"");
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
    private void requestt(int page) {
        if (WDApplication.getAppContext().getUserDao().loadAll().size()>0){

            List<User> users = WDApplication.getAppContext().getUserDao().loadAll();
            mSessionId = users.get(0).getSessionId();
            mUserId = users.get(0).getUserId();
            Log.e("lk", "有 interid"+mUserId);
            mInformationListPresenter.reqeust(mUserId, mSessionId, mId, page, 10);

        }else {
            Log.e("lk", "无 ");
            mInformationListPresenter.reqeust(0L, " ", mId, page, 10);

        }

    }

    @Override
    protected void destoryData() {
        mAddCollectionPresenter = null;
        mCancelCollectionPresenter = null;
        mInformationListPresenter = null;
    }



    @OnClick({R.id.search, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                break;
            case R.id.back:
                finish();
                break;
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
                Toast.makeText(InterestDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InterestDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(InterestDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InterestDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
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

        View mView = View.inflate(this, R.layout.share, null);
        mDialog = new Dialog(this, R.style.BottomDialog);
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
        msg.title = mResult.get(mInt).getTitle();
        msg.description = mResult.get(mInt).getSummary();
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
