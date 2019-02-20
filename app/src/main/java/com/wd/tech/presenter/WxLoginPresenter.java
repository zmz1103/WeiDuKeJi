package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date: 2019/1/28.
 * Created 王思敏
 * function:微信登录
 */
public class WxLoginPresenter extends WDPresenter{
    public WxLoginPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequests = NetWorkManager.getInstance().create(IRequest.class);
        return iRequests.getWxlogin("0110010010000",(String) args[0]);
    }
}
