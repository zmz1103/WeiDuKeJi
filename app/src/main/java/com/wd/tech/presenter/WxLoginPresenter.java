package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;


public class WxLoginPresenter extends WDPresenter{
    public WxLoginPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequests = NetWorkManager.getInstance().create(IRequest.class);
        return iRequests.getWxlogin("1.0",(String) args[0]);
    }
}
