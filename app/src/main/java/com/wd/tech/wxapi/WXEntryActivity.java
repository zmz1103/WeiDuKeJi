package com.wd.tech.wxapi;

import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_wxentry;
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
            default:
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
