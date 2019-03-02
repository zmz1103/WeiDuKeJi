package com.wd.tech.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.tech.R;
import com.wd.tech.bean.FindVipBean;
import com.wd.tech.bean.PayBean;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.User;
import com.wd.tech.exception.ApiException;
import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.presenter.BuyVipPresenter;
import com.wd.tech.presenter.FindVipPresenter;
import com.wd.tech.presenter.PayPresenter;
import com.wd.tech.util.MD5Utils;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OpenVipActivity extends WDActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.nianzuan)
    RadioButton nianzuan;
    @BindView(R.id.jizuan)
    RadioButton jizuan;
    @BindView(R.id.yuezuan)
    RadioButton yuezuan;
    @BindView(R.id.zhouzuan)
    RadioButton zhouzuan;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.wxzfbtn)
    RadioButton wxzfbtn;
    @BindView(R.id.zfbbtn)
    RadioButton zfbbtn;
    @BindView(R.id.xiadan)
    Button xiadan;
    private FindVipPresenter mFindVipPresenter;
    private List<FindVipBean> mResult;
    private List<User> mUsers;
    private long mUserId;
    private String mSessionId;
    private BuyVipPresenter mBuyVipPresenter;
    private String mSign;
    private String mMD5;
    private String mOrderId;
    private IWXAPI api;
    private PayPresenter mPayPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_open_vip;
    }

    @Override
    protected void initView() {
        api = WXAPIFactory.createWXAPI(this, "wx4c96b6b8da494224");//第二个参数为APPID
        api.registerApp("wx4c96b6b8da494224");
        mFindVipPresenter = new FindVipPresenter(new FindVipCall());
        mFindVipPresenter.reqeust();


    }

    @Override
    protected void destoryData() {

    }



    @OnClick({R.id.back, R.id.nianzuan, R.id.jizuan, R.id.yuezuan, R.id.zhouzuan, R.id.money, R.id.wxzfbtn, R.id.zfbbtn, R.id.xiadan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.nianzuan:
                nianzuan.isChecked();
                money.setText(mResult.get(3).getPrice()+"");
                jizuan.setChecked(false);
                yuezuan.setChecked(false);
                zhouzuan.setChecked(false);
                break;
            case R.id.jizuan:
                nianzuan.setChecked(false);
                jizuan.isChecked();
                money.setText(mResult.get(2).getPrice()+"");
                yuezuan.setChecked(false);
                zhouzuan.setChecked(false);
                break;
            case R.id.yuezuan:
                nianzuan.setChecked(false);
                jizuan.setChecked(false);
                yuezuan.isChecked();
                money.setText(mResult.get(1).getPrice()+"");
                zhouzuan.setChecked(false);
                break;
            case R.id.zhouzuan:
                nianzuan.setChecked(false);
                jizuan.setChecked(false);
                yuezuan.setChecked(false);
                zhouzuan.isChecked();
                money.setText(mResult.get(0).getPrice()+"");
                break;
            case R.id.money:
                break;
            case R.id.wxzfbtn:
                break;
            case R.id.zfbbtn:
                break;
            case R.id.xiadan:
                xiaDingDan();
                break;
        }
    }

    private void xiaDingDan() {
        if (userDao.loadAll().size()>0){
            mUsers = userDao.loadAll();
            mUserId = mUsers.get(0).getUserId();
            mSessionId = mUsers.get(0).getSessionId();
            mBuyVipPresenter = new BuyVipPresenter(new BuyVipCall());
            if (nianzuan.isChecked()){
                String userid= String.valueOf(mUserId);
                String commodid = String.valueOf(mResult.get(3).getCommodityId());
                mSign = userid + commodid + "tech";
                mMD5 = MD5Utils.MD5(mSign);
                mBuyVipPresenter.reqeust(mUserId,mSessionId,mResult.get(3).getCommodityId(),mMD5);
            }else if (jizuan.isChecked()){
                String userid= String.valueOf(mUserId);
                String commodid = String.valueOf(mResult.get(2).getCommodityId());
                mSign = userid + commodid + "tech";
                mMD5 = MD5Utils.MD5(mSign);
                mBuyVipPresenter.reqeust(mUserId,mSessionId,mResult.get(2).getCommodityId(),mMD5);
            }else if (yuezuan.isChecked()){
                String userid= String.valueOf(mUserId);
                String commodid = String.valueOf(mResult.get(1).getCommodityId());
                mSign = userid + commodid + "tech";
                mMD5 = MD5Utils.MD5(mSign);
                mBuyVipPresenter.reqeust(mUserId,mSessionId,mResult.get(1).getCommodityId(),mMD5);
            }else {
                String userid= String.valueOf(mUserId);
                String commodid = String.valueOf(mResult.get(0).getCommodityId());
                mSign = userid + commodid + "tech";
                mMD5 = MD5Utils.MD5(mSign);
                mBuyVipPresenter.reqeust(mUserId,mSessionId,mResult.get(0).getCommodityId(),mMD5);
            }

        }else {
            Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
        }
    }

    private class FindVipCall implements DataCall<Result<List<FindVipBean>>> {
        @Override
        public void success(Result<List<FindVipBean>> result) {
            if (result.getStatus().equals("0000")){
                mResult = result.getResult();
                nianzuan.isChecked();
                money.setText(mResult.get(3).getPrice()+"");
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class BuyVipCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                mOrderId = result.getOrderId();
                Log.e("lk","orderid"+mOrderId);
                if (wxzfbtn.isChecked()) {
                    mPayPresenter = new PayPresenter(new PayCall());
                    mPayPresenter.reqeust(mUserId, mSessionId , mOrderId, "1");
                }else {
                    Toast.makeText(OpenVipActivity.this, "暂时不支持！！", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(OpenVipActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }



        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class PayCall implements DataCall<PayBean> {
        @Override
        public void success(PayBean result) {

            PayReq req = new PayReq();
            req.appId = result.getAppId();
            req.partnerId = result.getPartnerId();
            req.prepayId = result.getPrepayId();
            req.nonceStr = result.getNonceStr();
            req.timeStamp = result.getTimeStamp();
            req.packageValue = result.getPackageValue();
            req.sign = result.getSign();
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            //3.调用微信支付sdk支付方法
            api.sendReq(req);
        }

        @Override
        public void fail(ApiException e) {


        }
    }
}
