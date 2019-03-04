package com.wd.tech.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.application.WDApplication;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.Transfer;
import com.wd.tech.bean.User;
import com.wd.tech.bean.UserInteGralListData;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FindUserIntegralPresenter;
import com.wd.tech.presenter.InfoPayByIntegralPresenter;
import com.wd.tech.util.TimeUtil;
import com.wd.tech.view.DataCall;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntegralActivity extends WDActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.simpleview)
    SimpleDraweeView simpleview;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.details)
    TextView details;
    @BindView(R.id.zuozhe)
    TextView zuozhe;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.shoucang)
    ImageView shoucang;
    @BindView(R.id.fenxiang)
    ImageView fenxiang;
    @BindView(R.id.fenxiangshu)
    TextView fenxiangshu;
    @BindView(R.id.shoucangshu)
    TextView shoucangshu;
    @BindView(R.id.myjifen)
    TextView myjifen;
    @BindView(R.id.duihuan)
    Button duihuan;
    private Intent mIntent;
    private String mId;
    private Transfer mMTransfer;
    private String mLaiyuan;
    private String mNeirong;
    private int mShoucang;
    private long mTime;
    private String mTitle;
    private String mTupian;
    private String[] mTu;
    private int mShoucangshu;
    private int mShareshu;
    private FindUserIntegralPresenter mFindUserIntegralPresenter;
    private List<User> mUsers;
    private long mUserId;
    private String mSessionId;
    private int mAmount;
    private InfoPayByIntegralPresenter mInfoPayByIntegralPresenter;
    private AlertDialog.Builder mBuilder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral;
    }

    @Override
    protected void initView() {
        mBuilder = new AlertDialog.Builder(IntegralActivity.this);
        mIntent = getIntent();
        mId = mIntent.getStringExtra("id");
        mMTransfer = (Transfer) mIntent.getSerializableExtra("mTransfer");
        mLaiyuan = mMTransfer.getLaiyuan();
        mNeirong = mMTransfer.getNeirong();
        mShoucang = mMTransfer.getShoucang();
        mTime = mMTransfer.getTime();
        mTitle = mMTransfer.getTitle();
        mTupian = mMTransfer.getTupian();
        mShoucangshu = mMTransfer.getShoucangshu();
        mShareshu = mMTransfer.getShareshu();
        mTu = mTupian.split("\\?");
        title.setText(mTitle);
        details.setText(mNeirong);
        zuozhe.setText(mLaiyuan);
        Date date = new Date();
        date.setTime(mTime);
        time.setText(TimeUtil.getTimeFormatText(date));
        Log.e("lk", "shoucang" + mShoucang);
        if (mShoucang == 1) {
            shoucang.setImageResource(R.mipmap.common_icon_collect_s);
        } else {
            shoucang.setImageResource(R.mipmap.common_icon_collect_n);
        }
        simpleview.setImageURI(mTu[0]);
        shoucangshu.setText(mShoucangshu + "");
        fenxiangshu.setText(mShareshu + "");

        mFindUserIntegralPresenter = new FindUserIntegralPresenter(new FindUserIntegralCall());

        if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
            mUsers = WDApplication.getAppContext().getUserDao().loadAll();
            mUserId = mUsers.get(0).getUserId();
            mSessionId = mUsers.get(0).getSessionId();
            mFindUserIntegralPresenter.reqeust(mUserId, mSessionId);

        } else {
            Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R.id.back, R.id.duihuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.duihuan:
                dui();
                break;
        }
    }

    private void dui() {
        mInfoPayByIntegralPresenter = new InfoPayByIntegralPresenter(new InfoPayByIntegralCall());
        if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
            mUsers = WDApplication.getAppContext().getUserDao().loadAll();
            mUserId = mUsers.get(0).getUserId();
            mSessionId = mUsers.get(0).getSessionId();
            mInfoPayByIntegralPresenter.reqeust(mUserId, mSessionId,mId,10);

        } else {
            Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
        }
    }


    private class FindUserIntegralCall implements DataCall<Result<UserInteGralListData>> {

        @Override
        public void success(Result<UserInteGralListData> result) {
            if (result.getStatus().equals("0000")) {
                mAmount = result.getResult().getAmount();
                myjifen.setText(mAmount + "分");

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class InfoPayByIntegralCall implements DataCall<Result> {



        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                Toast.makeText(IntegralActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                /*mBuilder.setView(R.layout.dlog_success_layout);
                WindowManager m = getWindowManager();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
                p.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的0.5
                p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
                mBuilder.show().getWindow().setAttributes(p);*/



            }else {
                /*mBuilder.setView(R.layout.dlog_failed_layout);
                WindowManager m = getWindowManager();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
                p.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的0.5
                p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
                mBuilder.show().getWindow().setAttributes(p);*/
                Toast.makeText(IntegralActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
