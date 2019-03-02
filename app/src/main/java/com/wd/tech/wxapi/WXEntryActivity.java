package com.wd.tech.wxapi;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.tech.R;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.User;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.WxLoginPresenter;
import com.wd.tech.util.WeiXinUtil;
import com.wd.tech.view.DataCall;

import java.util.List;

public class WXEntryActivity extends WDActivity implements IWXAPIEventHandler {

    private WxLoginPresenter mWxLoginPresenter;
    private String mCode;
    private IWXAPI mWxApi;

    @Override
    protected int getLayoutId() {
        mWxApi = WXAPIFactory.createWXAPI(this, "wx4c96b6b8da494224", true);
        mWxApi.registerApp("wx4c96b6b8da494224");
        mWxApi.handleIntent(getIntent(), this);
        return R.layout.activity_wxentry;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWxApi.handleIntent(intent, this);
    }

    @Override
    protected void initView() {

        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(), this);
    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(final BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                mCode = ((SendAuth.Resp) baseResp).code;
                mWxLoginPresenter = new WxLoginPresenter(new WxCall());
                mWxLoginPresenter.reqeust(mCode);
                break;
            case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                // 只是做了简单的finish操作
                finish();
                break;
            default:
                // 错误返回
                switch (baseResp.getType()) {
                    // 微信分享
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        Log.i("WXEntryActivity" , ">>>errCode = " + baseResp.errCode);
                        finish();
                        break;
                    default:
                        break;
                }
                break;

        }
    }

    class WxCall implements DataCall<Result<User>> {

        @Override
        public void success(Result<User> data) {
            if (data.getStatus().equals("0000")) {
                //登录成功
                Toast.makeText(WXEntryActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                //将登录信息存入数据库

                userDao.deleteAll();
                User user = new User();
                user = data.getResult();
                user.setSole(1);
                userDao.insertOrReplace(user);
                List<User> users = userDao.loadAll();
                Toast.makeText(WXEntryActivity.this, ""+users.size(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }
}
