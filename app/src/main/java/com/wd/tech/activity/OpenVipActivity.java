package com.wd.tech.activity;

import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.tech.R;
import com.wd.tech.application.WDApplication;
import com.wd.tech.bean.FindVipBean;
import com.wd.tech.bean.PayBean;
import com.wd.tech.bean.PayResult;
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
import java.util.Map;

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
    private int mSet = 1;

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String,String>)msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OpenVipActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OpenVipActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OpenVipActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(OpenVipActivity.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        };
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_open_vip;
    }

    @Override
    protected void initView() {
        api = WXAPIFactory.createWXAPI(this, "wx4c96b6b8da494224");//第二个参数为APPID
        api.registerApp("wx4c96b6b8da494224");
        mPayPresenter = new PayPresenter(new PayCall());
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
        if (WDApplication.getAppContext().getUserDao().loadAll().size()>0){
            mUsers = WDApplication.getAppContext().getUserDao().loadAll();
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

                if (wxzfbtn.isChecked()) {
                    mSet = 1;
                    Log.e("lk","orderid"+mOrderId);
                    Log.e("lk", "微信支付: " + mSet);

                    mPayPresenter.reqeust(mUserId, mSessionId, mOrderId, 1);
                }else if (zfbbtn.isChecked()){
                    mSet = 2;
                    Log.e("lk", "支付宝支付: "+mSet);
                    mPayPresenter.reqeust(mUserId, mSessionId , mOrderId, mSet);
                }

            }else {
                Toast.makeText(OpenVipActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }



        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class PayCall implements DataCall<Result<String>> {

        private String payInfo;

        @Override
        public void success(Result<String> result) {
            Log.e("lk", result.getMessage());
            Log.e("lk","调微信");
            if (mSet == 1){
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
            }else {

                payInfo = result.getResult();
                //final String payInfo = mOrderId;   // 订单信息
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(OpenVipActivity.this);
                        // 调用支付接口，获取支付结果
                        Map<String, String> result = alipay.payV2(payInfo,true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();


            }

        }

        @Override
        public void fail(ApiException e) {


        }
    }
}
